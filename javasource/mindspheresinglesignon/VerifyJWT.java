package mindspheresinglesignon;

import java.util.Date;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.resolvers.JwksVerificationKeyResolver;
import org.jose4j.keys.resolvers.VerificationKeyResolver;
import org.jose4j.lang.JoseException;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.MendixRuntimeException;

import mindspheresinglesignon.proxies.constants.Constants;

public class VerifyJWT {
	private String audience = "";
	private String trustedIssuer = "";
	private long lastPublicKeyUpdateTime = 0;
	private String PublicKeys = "";
	private JwtConsumer jwtConsumer = null;
	private IPublicKeys iPublicKeys;
	
	protected static ILogNode LOGGER = Core.getLogger(Constants.getModuleName());

	public VerifyJWT(String audience, String trustedIssuer, IPublicKeys iPublicKeys) {
		this.audience = audience;
		this.trustedIssuer = trustedIssuer;
		this.iPublicKeys = iPublicKeys;	
	}

	private String getPublicKeys() {
		LOGGER.info("fetching publicKeys");		
		try {
			// It could be, that publicKeys could not be loaded... 
			String newPublicKeys = iPublicKeys.getPublicKeys();
			lastPublicKeyUpdateTime = new Date().getTime();
			this.PublicKeys = newPublicKeys;
		} catch (MendixRuntimeException e) {
			LOGGER.error(e.getMessage());
		}		
		return this.PublicKeys;
	}

	private VerificationKeyResolver getVerificationKeyResolver() {
		try {
			JsonWebKeySet jwks = new JsonWebKeySet(getPublicKeys());
			JwksVerificationKeyResolver vkr = new JwksVerificationKeyResolver(jwks.getJsonWebKeys());
			return vkr;
		} catch (JoseException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	private JwtConsumer getJwtConsumer() {
		long currentTime = new Date().getTime();
		if (currentTime - lastPublicKeyUpdateTime < 24 * 60 * 60 * 1000 && jwtConsumer != null) {
			return jwtConsumer;
		}

		jwtConsumer = new JwtConsumerBuilder()
				.setRequireExpirationTime()
				.setRequireIssuedAt()
				.setAllowedClockSkewInSeconds(300)
				.setVerificationKeyResolver(getVerificationKeyResolver())
				.setJwsAlgorithmConstraints(
						new AlgorithmConstraints(ConstraintType.WHITELIST, AlgorithmIdentifiers.RSA_USING_SHA256))
				.setExpectedIssuer(trustedIssuer)
				.setExpectedAudience(audience)
				.build();

		return jwtConsumer;
	}
	
	static public JwtClaims getClaimsNoSecurityCheck(String jwt) throws InvalidJwtException {
		JwtConsumer consumer = new JwtConsumerBuilder()
				.setSkipVerificationKeyResolutionOnNone()
				.setSkipAllValidators()
				.setSkipSignatureVerification()
				.build();
		jwt = jwt.replaceFirst("(?i)bearer ", "");
		return consumer.processToClaims(jwt);
	}

	public JwtClaims getJwtClaims(String jwt) throws InvalidJwtException {
		if (jwt == null)
			jwt = "";
		jwt = jwt.replaceFirst("(?i)bearer ", "");
		return getJwtConsumer().processToClaims(jwt);
	}
}