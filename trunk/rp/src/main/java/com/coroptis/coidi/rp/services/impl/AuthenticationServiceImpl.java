/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
		if (authenticationResponse.getMode() != null
				&& authenticationResponse.getMode().equals("id_res")) {
			if (nonceService.verifyNonce(authenticationResponse.getNonce(),
					NONCE_EXPIRATION_TIME_IN_MINUTES)) {
				nonceDao.storeNonce(authenticationResponse.getNonce());
			} else {
				logger.info("nonce is expired. '"
						+ authenticationResponse.getNonce() + "'");
				return false;
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
		} else {
			logger.debug("authentication response doesn't contains mode");
		}
		return false;
	}
}
