package com.coroptis.coidi.rp.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.base.AuthenticationResult.Status;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.coroptis.coidi.rp.services.AuthenticationVerificationService;
import com.coroptis.coidi.rp.services.NonceService;
import com.google.common.base.Preconditions;

/**
 * authentication response decoder verify that openid.mode is correct and
 * message signature.
 * 
 * @author jirout
 * 
 */
public class AuthRespDecoderOpenId implements AuthRespDecoder {

	private final static Logger logger = LoggerFactory.getLogger(AuthRespDecoderOpenId.class);

	private final NonceService nonceService;

	private final SigningService signingService;

	public AuthRespDecoderOpenId(final NonceService nonceService, final SigningService signingService) {
		this.nonceService = Preconditions.checkNotNull(nonceService);
		this.signingService = Preconditions.checkNotNull(signingService);
	}

	@Override
	public boolean decode(final AuthenticationResponse authenticationResponse, final Association association,
			final AuthenticationResult authenticationResult) {
		if (authenticationResponse.getMode() == null) {
			throw new CoidiException("openid.mode is empty");
		}
		if (authenticationResponse.getMode().equals("cancel")) {
			authenticationResult.setStatus(Status.cancel);
			return true;
		} else if (authenticationResponse.getMode().equals("id_res")) {
			authenticationResult.setStatus(Status.res);
			if (!nonceService.isNonceValid(authenticationResponse.getNonce(),
					AuthenticationVerificationService.NONCE_EXPIRATION_TIME_IN_MINUTES)) {
				authenticationResult.setStatus(Status.cancel);
				return true;
			}
			final String signature = signingService.sign(authenticationResponse, association);
			if (!signature.equals(authenticationResponse.getSignature())) {
				logger.warn("Signature in authentication response '" + authenticationResponse.getSignature()
						+ "' is not same as computed '" + signature + "', used association: " + association
						+ ", from authentication response " + authenticationResponse.getMessage());
				authenticationResult.setStatus(Status.cancel);
				return true;
			}
		} else {
			throw new CoidiException("openid.mode value '" + authenticationResponse.getMode()
					+ "' is not valid in authentication response");
		}
		authenticationResult.setIdentity(authenticationResponse.getIdentity());
		return false;
	}

}
