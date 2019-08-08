package mindspheresinglesignon;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.externalinterface.connector.RequestHandler;
import com.mendix.logging.ILogNode;
import com.mendix.m2ee.api.IMxRuntimeRequest;
import com.mendix.m2ee.api.IMxRuntimeResponse;
import com.mendix.systemwideinterfaces.core.IContext;

import mindspheresinglesignon.proxies.Credentials;
import mindspheresinglesignon.proxies.CredentialsResponse;
import mindspheresinglesignon.proxies.constants.Constants;
import mindspheresinglesignon.proxies.microflows.Microflows;

public class CredentialsHandler extends RequestHandler {

	protected static ILogNode LOGGER = Core.getLogger(Constants.getModuleName());
	static private String CREDENTIAL_SESSION_ATTR_NAME = "CredentialsResponse";
	static private String clientId = "";
	static private String clientSecret = "";
	
	public static void storeCredentialsResponsInSession(IMxRuntimeRequest request, CredentialsResponse appCredResp) {
		request.getHttpServletRequest().getSession().setAttribute(CREDENTIAL_SESSION_ATTR_NAME, appCredResp);
	}
	private static void storeClientIdClientSecret(IMxRuntimeRequest request) {		
		clientId = request.getParameter("clientId"); 
 		clientSecret = request.getParameter("clientSecret");		
	}
	public static String getClientId() {
		return clientId;
	}
	public static String getClientSecret() {
		return clientSecret;
	}
	public static CredentialsResponse retrievCredentialsResponsFromSession(IMxRuntimeRequest request) {
		return (CredentialsResponse)request.getHttpServletRequest().getSession().getAttribute(CREDENTIAL_SESSION_ATTR_NAME);		
	}
	
	public static void removeCredentialsFromSession(IMxRuntimeRequest request) {
		request.getHttpServletRequest().getSession().removeAttribute(CREDENTIAL_SESSION_ATTR_NAME);
	}

	@Override
	protected void processRequest(IMxRuntimeRequest request, IMxRuntimeResponse response, String path)
			throws Exception {
		if (!"POST".equals(request.getHttpServletRequest().getMethod())) {
			LOGGER.warn("appcredentials request not of type 'POST'");
			response.setStatus(IMxRuntimeResponse.BAD_REQUEST);
			return;
		}

		// Let's call the application Credentials Microflow do the work
		Credentials appcreds = new Credentials(getContext(request));
		appcreds.setClientID(request.getParameter("clientId"));
		appcreds.setClientSecret(request.getParameter("clientSecret"));
		appcreds.setappName(Constants.getCockpitApplicationName());
		appcreds.setappVersion(Constants.getCockpitApplicationVersion());
		appcreds.sethostTenant(Constants.getHostTenant());
		appcreds.setuserTenant(Constants.getUserTenant());
		appcreds.setCredentialsType(Constants.getCredentialsType());

		CredentialsResponse resp; 
		
		if (Constants.getCredentialsType()) {
			resp = Microflows.aCT_ApplicationCredentials(getContext(request), appcreds);
		} else {
			resp = Microflows.aCT_ServiceCredentials(getContext(request), appcreds);
		}
		
		if (resp == null || (resp != null && resp.getStatusCode() != null && resp.getStatusCode() != 200)) {
			int statusCode = 0;
			String errorMessage = "";
			String errorType = "";

			if (resp != null) {
				statusCode = resp.getStatusCode();
				errorMessage = resp.getErrorMessage();
				errorType = resp.getErrorType();				
			}			
			if (appcreds.getCredentialsType()) {
				AppCredentialsPage page = new AppCredentialsPage();
				page.WriteAppCredPageToResponse(response, true, statusCode, request.getParameter("queryString"), errorMessage, errorType);
			} else {
				ServiceCredentialsPage page = new ServiceCredentialsPage();
				page.WriteServiceCredPageToResponse(response, true, statusCode, request.getParameter("queryString"), errorMessage, errorType);
			}
		} else {
			storeCredentialsResponsInSession(request, resp);
			storeClientIdClientSecret(request);
			// Redirect to home
			response.setStatus(IMxRuntimeResponse.SEE_OTHER);
			String queryString = request.getParameter("queryString");
			if (queryString != null && !queryString.isEmpty()) {
				response.addHeader("Location", "/index.html?" + queryString);
			} else {
				response.addHeader("Location", "/index.html");
			}
		}
	}

	private IContext getContext(IMxRuntimeRequest request) throws CoreException {
		return this.getSessionFromRequest(request).createContext();
	}
}