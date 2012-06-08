package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.services.AuthenticationService;
import com.coroptis.coidi.rp.services.NonceDao;

public class AuthenticationServiceImpl implements AuthenticationService {

	@Inject
	private Logger logger;

	@Inject
	private NonceService nonceService;

	@Inject
	private SigningService signingService;

	@Inject
	private NonceDao nonceDao;

	@Override
	public Boolean verify(final AuthenticationResponse authenticationResponse,
			final Association association) {
		if (authenticationResponse.getMode().equals("id_res")) {
			if (!nonceService.verifyNonce(authenticationResponse.getNonce(),
					NONCE_EXPIRATION_TIME_IN_MINUTES)) {
				logger.info("nonce is expired. '"
						+ authenticationResponse.getNonce() + "'");
				return false;
			} else {
				nonceDao.storeNonce(authenticationResponse.getNonce());
			}
			String signature = signingService.sign(authenticationResponse,
					association);
			if (!signature.equals(authenticationResponse.getSignature())) {
				logger.info("Signature in authentication response '"
						+ authenticationResponse.getSignature()
						+ "' is not same as computed '" + signature
						+ "', used association: " + association);
				return false;
			}
			return true;
		}
		return false;
	}
}