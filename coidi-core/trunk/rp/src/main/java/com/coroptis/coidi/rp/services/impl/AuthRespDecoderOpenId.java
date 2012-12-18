package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.base.AuthenticationResult.Status;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.coroptis.coidi.rp.services.AuthenticationService;
import com.coroptis.coidi.rp.services.NonceDao;

/**
 * authentication response decoder verify that openid.mode is correct and
 * message signature.
 * 
 * @author jirout
 * 
 */
public class AuthRespDecoderOpenId implements AuthRespDecoder {

	@Inject
	private Logger logger;

	@Inject
	private NonceService nonceService;

	@Inject
	private SigningService signingService;

	@Inject
	private NonceDao nonceDao;

	@Override
	public Boolean decode(final AuthenticationResponse authenticationResponse,
			final Association association,
			final AuthenticationResult authenticationResult) {
		if (authenticationResponse.getMode() == null) {
			logger.warn("openid.mode is not in authentication response: "
					+ authenticationResponse.getMessage());
			return false;
		}
		if (authenticationResponse.getMode().equals("cancel")) {
			authenticationResult.setStatus(Status.cancel);
		} else if (authenticationResponse.getMode().equals("id_res")) {
			authenticationResult.setStatus(Status.res);
			if (nonceService.verifyNonce(authenticationResponse.getNonce(),
					AuthenticationService.NONCE_EXPIRATION_TIME_IN_MINUTES)) {
				nonceDao.storeNonce(authenticationResponse.getNonce());
			} else {
				logger.warn("nonce is expired in authentication response: "
						+ authenticationResponse.getMessage());
				return false;
			}
			String signature = signingService.sign(authenticationResponse,
					association);
			if (!signature.equals(authenticationResponse.getSignature())) {
				logger.warn("Signature in authentication response '"
						+ authenticationResponse.getSignature()
						+ "' is not same as computed '" + signature
						+ "', used association: " + association
						+ ", from authentication response "
						+ authenticationResponse.getMessage());
				return false;
			}
		} else {
			logger.warn("openid.mode value is not valid in authentication response: "
					+ authenticationResponse.getMessage());
			return false;
		}
		authenticationResult.setIdentity(authenticationResponse.getIdentity());
		return null;
	}

}