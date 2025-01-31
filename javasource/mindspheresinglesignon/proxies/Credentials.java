// This file was generated by Mendix Modeler.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package mindspheresinglesignon.proxies;

public class Credentials
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject credentialsMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "MindSphereSingleSignOn.Credentials";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		appName("appName"),
		appVersion("appVersion"),
		hostTenant("hostTenant"),
		userTenant("userTenant"),
		ClientID("ClientID"),
		ClientSecret("ClientSecret"),
		CredentialsType("CredentialsType"),
		Base64Credentials("Base64Credentials");

		private java.lang.String metaName;

		MemberNames(java.lang.String s)
		{
			metaName = s;
		}

		@java.lang.Override
		public java.lang.String toString()
		{
			return metaName;
		}
	}

	public Credentials(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, "MindSphereSingleSignOn.Credentials"));
	}

	protected Credentials(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject credentialsMendixObject)
	{
		if (credentialsMendixObject == null)
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		if (!com.mendix.core.Core.isSubClassOf("MindSphereSingleSignOn.Credentials", credentialsMendixObject.getType()))
			throw new java.lang.IllegalArgumentException("The given object is not a MindSphereSingleSignOn.Credentials");

		this.credentialsMendixObject = credentialsMendixObject;
		this.context = context;
	}

	/**
	 * @deprecated Use 'Credentials.load(IContext, IMendixIdentifier)' instead.
	 */
	@java.lang.Deprecated
	public static mindspheresinglesignon.proxies.Credentials initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return mindspheresinglesignon.proxies.Credentials.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 */
	public static mindspheresinglesignon.proxies.Credentials initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new mindspheresinglesignon.proxies.Credentials(context, mendixObject);
	}

	public static mindspheresinglesignon.proxies.Credentials load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return mindspheresinglesignon.proxies.Credentials.initialize(context, mendixObject);
	}

	/**
	 * Commit the changes made on this proxy object.
	 */
	public final void commit() throws com.mendix.core.CoreException
	{
		com.mendix.core.Core.commit(context, getMendixObject());
	}

	/**
	 * Commit the changes made on this proxy object using the specified context.
	 */
	public final void commit(com.mendix.systemwideinterfaces.core.IContext context) throws com.mendix.core.CoreException
	{
		com.mendix.core.Core.commit(context, getMendixObject());
	}

	/**
	 * Delete the object.
	 */
	public final void delete()
	{
		com.mendix.core.Core.delete(context, getMendixObject());
	}

	/**
	 * Delete the object using the specified context.
	 */
	public final void delete(com.mendix.systemwideinterfaces.core.IContext context)
	{
		com.mendix.core.Core.delete(context, getMendixObject());
	}
	/**
	 * @return value of appName
	 */
	public final java.lang.String getappName()
	{
		return getappName(getContext());
	}

	/**
	 * @param context
	 * @return value of appName
	 */
	public final java.lang.String getappName(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.appName.toString());
	}

	/**
	 * Set value of appName
	 * @param appname
	 */
	public final void setappName(java.lang.String appname)
	{
		setappName(getContext(), appname);
	}

	/**
	 * Set value of appName
	 * @param context
	 * @param appname
	 */
	public final void setappName(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String appname)
	{
		getMendixObject().setValue(context, MemberNames.appName.toString(), appname);
	}

	/**
	 * @return value of appVersion
	 */
	public final java.lang.String getappVersion()
	{
		return getappVersion(getContext());
	}

	/**
	 * @param context
	 * @return value of appVersion
	 */
	public final java.lang.String getappVersion(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.appVersion.toString());
	}

	/**
	 * Set value of appVersion
	 * @param appversion
	 */
	public final void setappVersion(java.lang.String appversion)
	{
		setappVersion(getContext(), appversion);
	}

	/**
	 * Set value of appVersion
	 * @param context
	 * @param appversion
	 */
	public final void setappVersion(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String appversion)
	{
		getMendixObject().setValue(context, MemberNames.appVersion.toString(), appversion);
	}

	/**
	 * @return value of hostTenant
	 */
	public final java.lang.String gethostTenant()
	{
		return gethostTenant(getContext());
	}

	/**
	 * @param context
	 * @return value of hostTenant
	 */
	public final java.lang.String gethostTenant(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.hostTenant.toString());
	}

	/**
	 * Set value of hostTenant
	 * @param hosttenant
	 */
	public final void sethostTenant(java.lang.String hosttenant)
	{
		sethostTenant(getContext(), hosttenant);
	}

	/**
	 * Set value of hostTenant
	 * @param context
	 * @param hosttenant
	 */
	public final void sethostTenant(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String hosttenant)
	{
		getMendixObject().setValue(context, MemberNames.hostTenant.toString(), hosttenant);
	}

	/**
	 * @return value of userTenant
	 */
	public final java.lang.String getuserTenant()
	{
		return getuserTenant(getContext());
	}

	/**
	 * @param context
	 * @return value of userTenant
	 */
	public final java.lang.String getuserTenant(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.userTenant.toString());
	}

	/**
	 * Set value of userTenant
	 * @param usertenant
	 */
	public final void setuserTenant(java.lang.String usertenant)
	{
		setuserTenant(getContext(), usertenant);
	}

	/**
	 * Set value of userTenant
	 * @param context
	 * @param usertenant
	 */
	public final void setuserTenant(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String usertenant)
	{
		getMendixObject().setValue(context, MemberNames.userTenant.toString(), usertenant);
	}

	/**
	 * @return value of ClientID
	 */
	public final java.lang.String getClientID()
	{
		return getClientID(getContext());
	}

	/**
	 * @param context
	 * @return value of ClientID
	 */
	public final java.lang.String getClientID(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.ClientID.toString());
	}

	/**
	 * Set value of ClientID
	 * @param clientid
	 */
	public final void setClientID(java.lang.String clientid)
	{
		setClientID(getContext(), clientid);
	}

	/**
	 * Set value of ClientID
	 * @param context
	 * @param clientid
	 */
	public final void setClientID(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String clientid)
	{
		getMendixObject().setValue(context, MemberNames.ClientID.toString(), clientid);
	}

	/**
	 * @return value of ClientSecret
	 */
	public final java.lang.String getClientSecret()
	{
		return getClientSecret(getContext());
	}

	/**
	 * @param context
	 * @return value of ClientSecret
	 */
	public final java.lang.String getClientSecret(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.ClientSecret.toString());
	}

	/**
	 * Set value of ClientSecret
	 * @param clientsecret
	 */
	public final void setClientSecret(java.lang.String clientsecret)
	{
		setClientSecret(getContext(), clientsecret);
	}

	/**
	 * Set value of ClientSecret
	 * @param context
	 * @param clientsecret
	 */
	public final void setClientSecret(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String clientsecret)
	{
		getMendixObject().setValue(context, MemberNames.ClientSecret.toString(), clientsecret);
	}

	/**
	 * @return value of CredentialsType
	 */
	public final java.lang.Boolean getCredentialsType()
	{
		return getCredentialsType(getContext());
	}

	/**
	 * @param context
	 * @return value of CredentialsType
	 */
	public final java.lang.Boolean getCredentialsType(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Boolean) getMendixObject().getValue(context, MemberNames.CredentialsType.toString());
	}

	/**
	 * Set value of CredentialsType
	 * @param credentialstype
	 */
	public final void setCredentialsType(java.lang.Boolean credentialstype)
	{
		setCredentialsType(getContext(), credentialstype);
	}

	/**
	 * Set value of CredentialsType
	 * @param context
	 * @param credentialstype
	 */
	public final void setCredentialsType(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Boolean credentialstype)
	{
		getMendixObject().setValue(context, MemberNames.CredentialsType.toString(), credentialstype);
	}

	/**
	 * @return value of Base64Credentials
	 */
	public final java.lang.String getBase64Credentials()
	{
		return getBase64Credentials(getContext());
	}

	/**
	 * @param context
	 * @return value of Base64Credentials
	 */
	public final java.lang.String getBase64Credentials(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.Base64Credentials.toString());
	}

	/**
	 * Set value of Base64Credentials
	 * @param base64credentials
	 */
	public final void setBase64Credentials(java.lang.String base64credentials)
	{
		setBase64Credentials(getContext(), base64credentials);
	}

	/**
	 * Set value of Base64Credentials
	 * @param context
	 * @param base64credentials
	 */
	public final void setBase64Credentials(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String base64credentials)
	{
		getMendixObject().setValue(context, MemberNames.Base64Credentials.toString(), base64credentials);
	}

	/**
	 * @return the IMendixObject instance of this proxy for use in the Core interface.
	 */
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return credentialsMendixObject;
	}

	/**
	 * @return the IContext instance of this proxy, or null if no IContext instance was specified at initialization.
	 */
	public final com.mendix.systemwideinterfaces.core.IContext getContext()
	{
		return context;
	}

	@java.lang.Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (obj != null && getClass().equals(obj.getClass()))
		{
			final mindspheresinglesignon.proxies.Credentials that = (mindspheresinglesignon.proxies.Credentials) obj;
			return getMendixObject().equals(that.getMendixObject());
		}
		return false;
	}

	@java.lang.Override
	public int hashCode()
	{
		return getMendixObject().hashCode();
	}

	/**
	 * @return String name of this class
	 */
	public static java.lang.String getType()
	{
		return "MindSphereSingleSignOn.Credentials";
	}

	/**
	 * @return String GUID from this object, format: ID_0000000000
	 * @deprecated Use getMendixObject().getId().toLong() to get a unique identifier for this object.
	 */
	@java.lang.Deprecated
	public java.lang.String getGUID()
	{
		return "ID_" + getMendixObject().getId().toLong();
	}
}
