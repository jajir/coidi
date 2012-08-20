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
package com.coroptis.coidi.op.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

public class OpenidDispatcherAuthenticationImmediate implements
		OpenIdDispatcher {

	@Inject
	private Logger logger;

	@Inject
	private NonceService nonceService;

	@Inject
	private AssociationDao associationDao;

	@Inject
	private AuthenticationService authenticationService;

	@Inject
	private SigningService signingService;

	@Inject
	private AuthenticationProcessor authenticationProcessor;

	@Inject
	private IdentityService identityService;

	@Override
	public AbstractMessage process(Map<String, String> requestParams) {
		if (requestParams.get(OPENID_MODE).equals(
				AuthenticationRequest.MODE_CHECKID_IMMEDIATE)) {
			AuthenticationRequest authenticationRequest = new AuthenticationRequest(
					requestParams);
			if (!authenticationService
					.isAuthenticationRequest(authenticationRequest)) {
				logger.debug("authentication request doesn't contains any idenity field");
				return null;
			}

			Association association = associationDao
					.getByAssocHandle(authenticationRequest.getAssocHandle());
			if (association == null) {
				ErrorResponse errorResponse = new ErrorResponse(false);
				String msg = "Unable to find association handle '"
						+ authenticationRequest.getAssocHandle() + "'";
				logger.warn(msg);
				errorResponse.setError(msg);
				return errorResponse;
			}

			AuthenticationResponse response = new AuthenticationResponse();
			response.setAssocHandle(authenticationRequest.getAssocHandle());
			response.setIdentity(authenticationRequest.getIdentity());
			response.setNonce(nonceService.createNonce());
			response.setReturnTo(authenticationRequest.getReturnTo());
			response.setSigned("assoc_handle,identity,nonce,return_to");
			response.setSignature(signingService.sign(response, association));
			response.put("go_to", authenticationRequest.getReturnTo());

			Identity identity = identityService.getById(authenticationRequest
					.getIdentity());
			if (identity == null) {
				// negative response
			}

			authenticationProcessor.process(authenticationRequest, response,
					identity);
			return response;
		}
		return null;
	}
}
