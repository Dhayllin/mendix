// This file was generated by Mendix Modeler.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package mindspheresinglesignon.proxies;

public class MindSphereAccount extends system.proxies.User
{
	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "MindSphereSingleSignOn.MindSphereAccount";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		Email("Email"),
		IsSubTenantUser("IsSubTenantUser"),
		SubTenantId("SubTenantId"),
		Name("Name"),
		Password("Password"),
		LastLogin("LastLogin"),
		Blocked("Blocked"),
		Active("Active"),
		FailedLogins("FailedLogins"),
		WebServiceUser("WebServiceUser"),
		IsAnonymous("IsAnonymous"),
		MindSphereAccount_Tenant("MindSphereSingleSignOn.MindSphereAccount_Tenant"),
		UserRoles("System.UserRoles"),
		User_Language("System.User_Language"),
		User_TimeZone("System.User_TimeZone");

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

	public MindSphereAccount(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, "MindSphereSingleSignOn.MindSphereAccount"));
	}

	protected MindSphereAccount(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mindSphereAccountMendixObject)
	{
		super(context, mindSphereAccountMendixObject);
		if (!com.mendix.core.Core.isSubClassOf("MindSphereSingleSignOn.MindSphereAccount", mindSphereAccountMendixObject.getType()))
			throw new java.lang.IllegalArgumentException("The given object is not a MindSphereSingleSignOn.MindSphereAccount");
	}

	/**
	 * @deprecated Use 'MindSphereAccount.load(IContext, IMendixIdentifier)' instead.
	 */
	@java.lang.Deprecated
	public static mindspheresinglesignon.proxies.MindSphereAccount initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return mindspheresinglesignon.proxies.MindSphereAccount.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 */
	public static mindspheresinglesignon.proxies.MindSphereAccount initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new mindspheresinglesignon.proxies.MindSphereAccount(context, mendixObject);
	}

	public static mindspheresinglesignon.proxies.MindSphereAccount load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return mindspheresinglesignon.proxies.MindSphereAccount.initialize(context, mendixObject);
	}

	public static java.util.List<mindspheresinglesignon.proxies.MindSphereAccount> load(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String xpathConstraint) throws com.mendix.core.CoreException
	{
		java.util.List<mindspheresinglesignon.proxies.MindSphereAccount> result = new java.util.ArrayList<mindspheresinglesignon.proxies.MindSphereAccount>();
		for (com.mendix.systemwideinterfaces.core.IMendixObject obj : com.mendix.core.Core.retrieveXPathQuery(context, "//MindSphereSingleSignOn.MindSphereAccount" + xpathConstraint))
			result.add(mindspheresinglesignon.proxies.MindSphereAccount.initialize(context, obj));
		return result;
	}

	/**
	 * @return value of Email
	 */
	public final java.lang.String getEmail()
	{
		return getEmail(getContext());
	}

	/**
	 * @param context
	 * @return value of Email
	 */
	public final java.lang.String getEmail(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.Email.toString());
	}

	/**
	 * Set value of Email
	 * @param email
	 */
	public final void setEmail(java.lang.String email)
	{
		setEmail(getContext(), email);
	}

	/**
	 * Set value of Email
	 * @param context
	 * @param email
	 */
	public final void setEmail(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String email)
	{
		getMendixObject().setValue(context, MemberNames.Email.toString(), email);
	}

	/**
	 * @return value of IsSubTenantUser
	 */
	public final java.lang.Boolean getIsSubTenantUser()
	{
		return getIsSubTenantUser(getContext());
	}

	/**
	 * @param context
	 * @return value of IsSubTenantUser
	 */
	public final java.lang.Boolean getIsSubTenantUser(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Boolean) getMendixObject().getValue(context, MemberNames.IsSubTenantUser.toString());
	}

	/**
	 * Set value of IsSubTenantUser
	 * @param issubtenantuser
	 */
	public final void setIsSubTenantUser(java.lang.Boolean issubtenantuser)
	{
		setIsSubTenantUser(getContext(), issubtenantuser);
	}

	/**
	 * Set value of IsSubTenantUser
	 * @param context
	 * @param issubtenantuser
	 */
	public final void setIsSubTenantUser(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Boolean issubtenantuser)
	{
		getMendixObject().setValue(context, MemberNames.IsSubTenantUser.toString(), issubtenantuser);
	}

	/**
	 * @return value of SubTenantId
	 */
	public final java.lang.String getSubTenantId()
	{
		return getSubTenantId(getContext());
	}

	/**
	 * @param context
	 * @return value of SubTenantId
	 */
	public final java.lang.String getSubTenantId(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.SubTenantId.toString());
	}

	/**
	 * Set value of SubTenantId
	 * @param subtenantid
	 */
	public final void setSubTenantId(java.lang.String subtenantid)
	{
		setSubTenantId(getContext(), subtenantid);
	}

	/**
	 * Set value of SubTenantId
	 * @param context
	 * @param subtenantid
	 */
	public final void setSubTenantId(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String subtenantid)
	{
		getMendixObject().setValue(context, MemberNames.SubTenantId.toString(), subtenantid);
	}

	/**
	 * @return value of MindSphereAccount_Tenant
	 */
	public final mindspheresinglesignon.proxies.Tenant getMindSphereAccount_Tenant() throws com.mendix.core.CoreException
	{
		return getMindSphereAccount_Tenant(getContext());
	}

	/**
	 * @param context
	 * @return value of MindSphereAccount_Tenant
	 */
	public final mindspheresinglesignon.proxies.Tenant getMindSphereAccount_Tenant(com.mendix.systemwideinterfaces.core.IContext context) throws com.mendix.core.CoreException
	{
		mindspheresinglesignon.proxies.Tenant result = null;
		com.mendix.systemwideinterfaces.core.IMendixIdentifier identifier = getMendixObject().getValue(context, MemberNames.MindSphereAccount_Tenant.toString());
		if (identifier != null)
			result = mindspheresinglesignon.proxies.Tenant.load(context, identifier);
		return result;
	}

	/**
	 * Set value of MindSphereAccount_Tenant
	 * @param mindsphereaccount_tenant
	 */
	public final void setMindSphereAccount_Tenant(mindspheresinglesignon.proxies.Tenant mindsphereaccount_tenant)
	{
		setMindSphereAccount_Tenant(getContext(), mindsphereaccount_tenant);
	}

	/**
	 * Set value of MindSphereAccount_Tenant
	 * @param context
	 * @param mindsphereaccount_tenant
	 */
	public final void setMindSphereAccount_Tenant(com.mendix.systemwideinterfaces.core.IContext context, mindspheresinglesignon.proxies.Tenant mindsphereaccount_tenant)
	{
		if (mindsphereaccount_tenant == null)
			getMendixObject().setValue(context, MemberNames.MindSphereAccount_Tenant.toString(), null);
		else
			getMendixObject().setValue(context, MemberNames.MindSphereAccount_Tenant.toString(), mindsphereaccount_tenant.getMendixObject().getId());
	}

	@java.lang.Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (obj != null && getClass().equals(obj.getClass()))
		{
			final mindspheresinglesignon.proxies.MindSphereAccount that = (mindspheresinglesignon.proxies.MindSphereAccount) obj;
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
		return "MindSphereSingleSignOn.MindSphereAccount";
	}

	/**
	 * @return String GUID from this object, format: ID_0000000000
	 * @deprecated Use getMendixObject().getId().toLong() to get a unique identifier for this object.
	 */
	@java.lang.Override
	@java.lang.Deprecated
	public java.lang.String getGUID()
	{
		return "ID_" + getMendixObject().getId().toLong();
	}
}
