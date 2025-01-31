// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package mindspheresinglesignon.actions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.ErrorCodes;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.core.CoreRuntimeException;
import com.mendix.externalinterface.connector.RequestHandler;
import com.mendix.logging.ILogNode;
import com.mendix.m2ee.api.IMxRuntimeRequest;
import com.mendix.m2ee.api.IMxRuntimeResponse;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.ISession;
import com.mendix.systemwideinterfaces.core.IUser;
import com.mendix.webui.CustomJavaAction;
import mindspheresinglesignon.IPublicKeys;
import mindspheresinglesignon.NoJWTException;
import mindspheresinglesignon.VerifyJWT;
import mindspheresinglesignon.ApiReverseProxy;
import mindspheresinglesignon.AppCredentialsPage;
import mindspheresinglesignon.CredentialsHandler;
import mindspheresinglesignon.LogoutHandler;
import mindspheresinglesignon.proxies.MindSphereAccount;
import mindspheresinglesignon.proxies.Tenant;
import mindspheresinglesignon.proxies.constants.Constants;
import mindspheresinglesignon.proxies.microflows.Microflows;
import mindspheresinglesignon.ServiceCredentialsPage;
import system.proxies.UserRole;

public class SingleSignOn extends CustomJavaAction<java.lang.Boolean>
{
	private java.lang.String AppName;

	public SingleSignOn(IContext context, java.lang.String AppName)
	{
		super(context);
		this.AppName = AppName;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		// BEGIN USER CODE
		LOGGER.info("Starting up SingleSignOn Request Handler ... ");
		SingleSignOnRequestHandler ssoHandler = new SingleSignOnRequestHandler(this.AppName);
		Core.addRequestHandler("sso", ssoHandler);
		Core.addRequestHandler("sso/", ssoHandler);
		Boolean local = (System.getenv("VCAP_SERVICES") == null);
		if (local && Constants.getEnableLocalMindSphereApiReverseProxy()) {
			Core.addRequestHandler("api/", new ApiReverseProxy());
		}
		if (local && Constants.getAskForCredentialsOnStartup()) {
			Core.addRequestHandler("credentials", new CredentialsHandler());
			if (Constants.getCredentialsType()) {
				Core.addRequestHandler("credentials.html", new AppCredentialsPage());
			} else {
				Core.addRequestHandler("credentials.html", new ServiceCredentialsPage());
			}
		}
		LogoutHandler logoutHandler = new LogoutHandler();
		Core.addRequestHandler("logout/", logoutHandler);
		Core.addRequestHandler("logout", logoutHandler);		
		LOGGER.info("Starting up SingleSignOn Request Handler ... done");
		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "SingleSignOn";
	}

	// BEGIN EXTRA CODE
	protected static ILogNode LOGGER = Core.getLogger(Constants.getModuleName());
	private static final String XAS_ID = "XASID";

	

	/**
	 * SingleSignOnRequestHandler for login automatically into Mendix application
	 * based on provided MindSphere JWT.
	 * 
	 * In case that the application is running locally a session is created for the
	 * configured Mendix administrator account. In case that the application is
	 * running on MindSphere the JWT is validated. The following claims are checked:
	 * Issuer, Signature, Audience
	 * 
	 * In case of an error during processing the request the application is
	 * redirected to "error_page/403.html"
	 */
	class SingleSignOnRequestHandler extends RequestHandler implements IPublicKeys {

		private String appName;	
		private String queryString;
		private HashMap<String, VerifyJWT> verifyJWTCache = new HashMap<String, VerifyJWT>();

		SingleSignOnRequestHandler(String AppName) {
			super();
			this.appName = AppName;
		}

		@Override
		protected void processRequest(IMxRuntimeRequest request, IMxRuntimeResponse response, String path) {

			try {
				// return BAD_REQUEST if we do not have a GET Request
				if (!"GET".equals(request.getHttpServletRequest().getMethod())) {
					LOGGER.warn("SSO request not of type 'GET'");
					response.setStatus(IMxRuntimeResponse.BAD_REQUEST);
					return;
				}
				
				// Get queryString
				this.queryString = request.getHttpServletRequest().getQueryString();

				// Get SudoContext for creating a new user
				IContext context = getSudoContext(request);

				// check if we run locally
				if (System.getenv("VCAP_SERVICES") == null) {
					handleLocalUser(response, context);
					return;
				}

				// Validate JWT and get claims
				JwtClaims claims = this.getClaims(request);

				// Get the user_id from JWT. The user_id is unique across all tenants
				String userId = claims.getStringClaimValue("user_id");
				String eMail = claims.getStringClaimValue("email");
				String tenant = claims.getStringClaimValue("ten");

				// Check if there is already a Session established for the given user
				IUser user = context.getSession().getUser(context);
				if (user != null && user.getName().equals(userId)) {
					updateUserRoles(context, user, claims);
					redirectToIndex(response);
					return;
				}

				// Create user if not exist based on userId
				user = this.getOrCreateUser(context, userId, eMail, tenant);
				updateUserRoles(context, user, claims);
				createSession(response, context, user);
				redirectToIndex(response);
			} catch (InvalidJwtException e) {
				if (e.hasErrorCode(ErrorCodes.AUDIENCE_INVALID)) {
					redirectToErrorPage(response, "/error_page/CockpitApplicationName.html?can=" + this.appName);
				} else {
					redirectToErrorPage(response, "/error_page/403.html");
				}
			} catch (NoJWTException e) {
				redirectToErrorPage(response, "/error_page/NoJWT.html");
			} catch (Throwable e) {
				LOGGER.error(e.getMessage(), e);
				redirectToErrorPage(response, "/error_page/403.html");
			}
		}

		public void redirectToErrorPage(IMxRuntimeResponse response, String location) {
			response.setStatus(IMxRuntimeResponse.SEE_OTHER);
			response.addHeader("Location", location);
		}

		/**
		 * create a new session for given user, redirects to index.html
		 * 
		 * @param response
		 * @param context
		 * @param user
		 * @throws CoreException
		 */
		private void createSession(IMxRuntimeResponse response, IContext context, IUser user) throws CoreException {
			// Logout if currentSession is SystemSession
			ISession currentSession = context.getSession();
			if (currentSession != null && !currentSession.isSystemSession()) {
				Core.logout(currentSession);
			}

			// Create a new Session
			UUID uuid = UUID.randomUUID();
			ISession newSession = Core.initializeSession(user, uuid.toString());
			setCookies(response, newSession);

		}

		/**
		 * get current user roles based on provided scopes in JWT. application scope
		 * name is compared ignoring cases to all available Mendix roles only matching
		 * roles are returned In case of an error some warnings are written to the log.
		 * 
		 * @param context
		 * @param claims
		 * @return
		 * @throws CoreException
		 */
		private List<UserRole> getCurrentUserRoles(IContext context, JwtClaims claims) throws CoreException {
			// initialize return object as empty List
			List<UserRole> userRoles = new ArrayList<UserRole>();
			// Get a list of all application scopes.
			List<String> scopeList;
			try {
				scopeList = claims.getStringListClaimValue("scope");
			} catch (MalformedClaimException e) {
				logger.error(e.getMessage());
				return userRoles;
			}
			// Remove all scopes which are not defined for the application
			scopeList.removeIf(s -> !s.startsWith(appName));
			// Get all roles defined for the application
			List<UserRole> appUserRoles = UserRole.load(context, "");

			// loop through list of scopes and check for each scope if a UserRole has the
			// same name as the scope provided
			scopeList.forEach(scope -> {
				appUserRoles.forEach(userRole -> {
					String scopeWithoutAppName = scope.replaceFirst(appName + ".", "");
					LOGGER.info("MindSphereScope: " + scopeWithoutAppName.toString() + " - MendixUserRole: "
							+ userRole.getName());
					if (scopeWithoutAppName.equalsIgnoreCase(userRole.getName())) {
						LOGGER.info("Assigning UserRole '" + userRole.getName() + "'");
						userRoles.add(userRole);
					}
				});
			});
			if (userRoles.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				appUserRoles.forEach(userRole -> {
					if (sb.length() == 0) {
						sb.append(userRole.getName());
					} else {
						sb.append(", " + userRole.getName());
					}
				});
				LOGGER.warn("No Roles assigned to the user.");
				LOGGER.warn("MindSphere scopes: " + claims.getClaimValue("scope"));
				LOGGER.warn("Mendix Roles: " + sb.toString());
			}
			return userRoles;
		}

		/**
		 * updating user roles for given user, also setting a new random password for
		 * the user
		 * 
		 * @param context
		 * @param iuser
		 * @param claims
		 * @throws CoreException
		 * @throws MalformedClaimException
		 */
		private void updateUserRoles(IContext context, IUser iuser, JwtClaims claims)
				throws CoreException, MalformedClaimException {
			context.startTransaction();
			MindSphereAccount account = MindSphereAccount.initialize(context, iuser.getMendixObject());
			account.setUserRoles(context, getCurrentUserRoles(context, claims));
			account.setPassword(getRandomPWD());
			if (claims.hasClaim("subtenant")) {
				account.setIsSubTenantUser(true);
				account.setSubTenantId(claims.getStringClaimValue("subtenant"));
			} else {
				account.setIsSubTenantUser(false);
				account.setSubTenantId(null);
			}
			Core.commit(context, account.getMendixObject());
			context.endTransaction();
		}

		/**
		 * add session cookies to response
		 * 
		 * @param response
		 * @param session
		 */
		private void setCookies(IMxRuntimeResponse response, ISession session) {
			response.addCookie(Core.getConfiguration().getSessionIdCookieName(), session.getId().toString(), "/", "",
					-1, true);
			response.addCookie(XAS_ID, "0." + Core.getXASId(), "/", "", -1, true);
		}

		private IUser getOrCreateUser(IContext context, String Name, String eMail, String ten) throws Exception {
			IUser user = Core.getUser(context, Name);
			if (user != null) {
				return user;
			}
			// Get the Tenant
			Tenant tenant = getOrCreateTenant(context, ten);
			// We have to create a new user
			context.startTransaction();
			MindSphereAccount account = new MindSphereAccount(context);
			account.setName(Name);
			account.setPassword(getRandomPWD());
			account.setEmail(eMail);
			account.setActive(true);
			account.setMindSphereAccount_Tenant(tenant);
			account.setWebServiceUser(false);
			Core.commit(context, account.getMendixObject());
			context.endTransaction();
			return Core.getUser(context, Name);
		}

		private String getRandomPWD() {
			String pwd = RandomStringUtils.randomAscii(32);
			String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^\\[\\]()@#$%^&+=])(?=\\S+$).{32,}$";
			if (pwd.matches(pattern)) {
				return pwd;
			} else {
				return getRandomPWD();
			}
		}

		private Tenant getOrCreateTenant(IContext context, String TenantName) throws Exception {
			List<Tenant> TenantList = Tenant.load(context, "[Name='" + TenantName + "']");
			if (TenantList.size() == 0) {
				try {
					context.startTransaction();
					Tenant tenant = new Tenant(context);
					tenant.setName(TenantName);
					Core.commit(context, tenant.getMendixObject());
					context.endTransaction();
				} catch (CoreRuntimeException e) {
					LOGGER.info(e.getMessage());
					context.rollbackTransAction();
				}
				return getOrCreateTenant(context, TenantName);
			}
			if (TenantList.size() == 1) {
				return TenantList.get(0);
			}
			throw new Exception("This should never happen!!! Your data is corrupted");
		}
		private String getQueryString() {
			return this.queryString != null ? "?" + this.queryString : "";
		}

		private void redirectToIndex(IMxRuntimeResponse response) {
			response.setStatus(IMxRuntimeResponse.SEE_OTHER);
			if (System.getenv("VCAP_SERVICES") == null && Constants.getAskForCredentialsOnStartup()) {
				response.addHeader("Location", "/credentials.html" + this.getQueryString());
			} else {
				response.addHeader("Location", "/index.html" + this.getQueryString());
			}
		}

		private IContext getSudoContext(IMxRuntimeRequest request) throws CoreException {
			// Create Context from Session if available
			ISession oldSession = this.getSessionFromRequest(request);
			IContext context = null;
			if (oldSession != null) {
				context = oldSession.createContext();
			} else {
				context = Core.createSystemContext();
			}

			if (!context.isSudo()) {
				context = context.createSudoClone();
			}
			return context;
		}

		/**
		 * returns claims of the request after token validation
		 * 
		 * @param request
		 * @return JwtClaims
		 * @throws InvalidJwtException
		 * @throws NoJWTException
		 */
		private JwtClaims getClaims(IMxRuntimeRequest request) throws InvalidJwtException, NoJWTException {
			VerifyJWT verifyJWT = getVerifyJWT(getTrustedIssuer(request));
			String jwt = request.getHeader("authorization");
			if (jwt == null || jwt == "") {
				throw new NoJWTException();
			}
			return verifyJWT.getJwtClaims(jwt);
		}

		/**
		 * Simple Cache for the Token Validation Class. As the creation of this object
		 * is expensive we put them in a HashMap.
		 * 
		 * @param trustedIssuer
		 * @return
		 */
		private VerifyJWT getVerifyJWT(String trustedIssuer) {
			String Key = appName + trustedIssuer;
			if (!verifyJWTCache.containsKey(Key)) {
				verifyJWTCache.put(Key, new VerifyJWT(appName, trustedIssuer, (IPublicKeys) this));
			}
			return verifyJWTCache.get(Key);
		}

		/**
		 * Method generates the TrustedIssuer String based on requestURL In case of an
		 * error method returns an empty string.
		 * 
		 * @param request
		 * @return The TrustedIssuer which must be checked in the JWT.
		 */
		private String getTrustedIssuer(IMxRuntimeRequest request) {
			// Trusted Issuer URL has the following format
			// https://<Tenant>.piam.<Env>.mindsphere.io/oauth/token
			// With <Tenant> = Provider Name
			// With <Env> on PROD = eu1 or on PROD-B = eu1-b
			try {
				// Referer header looks like:
				// https://<Tenant>-<AppName>-<Provider>.<Env>.mindsphere.io
				URL refererURL = new URL(request.getHeader("referer"));
				LOGGER.info(refererURL);
				String Host = refererURL.getHost();
				String Part = Host.substring(0, Host.indexOf("."));
				String TrustedIssuer = "https://" + Part.substring(Part.lastIndexOf("-") + 1) + ".piam"
						+ Host.substring(Host.indexOf(".")) + "/oauth/token";
				LOGGER.info("TrustedIssuer = " + TrustedIssuer);
				return TrustedIssuer;
			} catch (MalformedURLException e) {
				LOGGER.error(e.getMessage());
				return "";
			}
		}

		/**
		 * Method handles the LocalUser case. We are just creating a session for the
		 * Configured administrator. We do not change any roles for this user.
		 * 
		 * @param response
		 * @throws Exception
		 */
		private void handleLocalUser(IMxRuntimeResponse response, IContext context) throws Exception {
			LOGGER.info("Running localy, autologin as MendixAdmin");
			String AdminUserName = Core.getConfiguration().getAdminUserName();

			IUser user = Core.getUser(context, AdminUserName);
			if (user == null) {
				user = getOrCreateUser(context, AdminUserName, "max.mustermann@email.com", Constants.getUserTenant());
			}
			List<UserRole> appUserRoles = getUserRoles(context, user);
			if (!Core.isSubClassOf(MindSphereAccount.getType(), user.getMendixObject().getType())) {
				context.startTransaction();
				Core.delete(context, user.getMendixObject());
				context.endTransaction();
				user = getOrCreateUser(context, AdminUserName, "max.mustermann@email.com", Constants.getUserTenant());
			}
			// Make sure local Admin has association to configured Tenant
			// Make sure local Admin has the right Roles
			context.startTransaction();
			MindSphereAccount account = MindSphereAccount.initialize(context, user.getMendixObject());
			account.setMindSphereAccount_Tenant(getOrCreateTenant(context, Constants.getUserTenant()));
			account.setUserRoles(appUserRoles);
			Core.commit(context, account.getMendixObject());
			context.endTransaction();
			createSession(response, context, Core.getUser(context, AdminUserName));
			redirectToIndex(response);
		}

		private List<UserRole> getUserRoles(IContext context, IUser user) throws CoreException {
			Set<String> userRoles = user.getUserRoleNames();
			List<UserRole> appUserRoles = UserRole.load(context, "");
			appUserRoles.removeIf(e -> !userRoles.contains(e.getName()));
			return appUserRoles;
		}

		@Override
		public String getPublicKeys() {
			return Microflows.gET_PublicKeys(context());
		}
	}
	// END EXTRA CODE
}
