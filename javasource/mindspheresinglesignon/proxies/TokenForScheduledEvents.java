// This file was generated by Mendix Modeler.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package mindspheresinglesignon.proxies;

public class TokenForScheduledEvents extends mindspheresinglesignon.proxies.TenantObject
{
	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "MindSphereSingleSignOn.TokenForScheduledEvents";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		Access_Token("Access_Token"),
		ExpiresAt("ExpiresAt"),
		TenantObject_Tenant("MindSphereSingleSignOn.TenantObject_Tenant");

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

	public TokenForScheduledEvents(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, "MindSphereSingleSignOn.TokenForScheduledEvents"));
	}

	protected TokenForScheduledEvents(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject tokenForScheduledEventsMendixObject)
	{
		super(context, tokenForScheduledEventsMendixObject);
		if (!com.mendix.core.Core.isSubClassOf("MindSphereSingleSignOn.TokenForScheduledEvents", tokenForScheduledEventsMendixObject.getType()))
			throw new java.lang.IllegalArgumentException("The given object is not a MindSphereSingleSignOn.TokenForScheduledEvents");
	}

	/**
	 * @deprecated Use 'TokenForScheduledEvents.load(IContext, IMendixIdentifier)' instead.
	 */
	@java.lang.Deprecated
	public static mindspheresinglesignon.proxies.TokenForScheduledEvents initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return mindspheresinglesignon.proxies.TokenForScheduledEvents.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 */
	public static mindspheresinglesignon.proxies.TokenForScheduledEvents initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new mindspheresinglesignon.proxies.TokenForScheduledEvents(context, mendixObject);
	}

	public static mindspheresinglesignon.proxies.TokenForScheduledEvents load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return mindspheresinglesignon.proxies.TokenForScheduledEvents.initialize(context, mendixObject);
	}

	public static java.util.List<mindspheresinglesignon.proxies.TokenForScheduledEvents> load(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String xpathConstraint) throws com.mendix.core.CoreException
	{
		java.util.List<mindspheresinglesignon.proxies.TokenForScheduledEvents> result = new java.util.ArrayList<mindspheresinglesignon.proxies.TokenForScheduledEvents>();
		for (com.mendix.systemwideinterfaces.core.IMendixObject obj : com.mendix.core.Core.retrieveXPathQuery(context, "//MindSphereSingleSignOn.TokenForScheduledEvents" + xpathConstraint))
			result.add(mindspheresinglesignon.proxies.TokenForScheduledEvents.initialize(context, obj));
		return result;
	}

	/**
	 * @return value of Access_Token
	 */
	public final java.lang.String getAccess_Token()
	{
		return getAccess_Token(getContext());
	}

	/**
	 * @param context
	 * @return value of Access_Token
	 */
	public final java.lang.String getAccess_Token(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.Access_Token.toString());
	}

	/**
	 * Set value of Access_Token
	 * @param access_token
	 */
	public final void setAccess_Token(java.lang.String access_token)
	{
		setAccess_Token(getContext(), access_token);
	}

	/**
	 * Set value of Access_Token
	 * @param context
	 * @param access_token
	 */
	public final void setAccess_Token(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String access_token)
	{
		getMendixObject().setValue(context, MemberNames.Access_Token.toString(), access_token);
	}

	/**
	 * @return value of ExpiresAt
	 */
	public final java.util.Date getExpiresAt()
	{
		return getExpiresAt(getContext());
	}

	/**
	 * @param context
	 * @return value of ExpiresAt
	 */
	public final java.util.Date getExpiresAt(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.util.Date) getMendixObject().getValue(context, MemberNames.ExpiresAt.toString());
	}

	/**
	 * Set value of ExpiresAt
	 * @param expiresat
	 */
	public final void setExpiresAt(java.util.Date expiresat)
	{
		setExpiresAt(getContext(), expiresat);
	}

	/**
	 * Set value of ExpiresAt
	 * @param context
	 * @param expiresat
	 */
	public final void setExpiresAt(com.mendix.systemwideinterfaces.core.IContext context, java.util.Date expiresat)
	{
		getMendixObject().setValue(context, MemberNames.ExpiresAt.toString(), expiresat);
	}

	@java.lang.Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (obj != null && getClass().equals(obj.getClass()))
		{
			final mindspheresinglesignon.proxies.TokenForScheduledEvents that = (mindspheresinglesignon.proxies.TokenForScheduledEvents) obj;
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
		return "MindSphereSingleSignOn.TokenForScheduledEvents";
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
