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

import java.util.Set;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;

public class AuthenticationProcessorResponse implements AuthenticationProcessor {

	@Inject
	private Logger logger;

	@Inject
	private NonceService nonceService;

	@Inject
	@Symbol("op.server")
	private String opServer;

	@Override
	public AbstractMessage process(AuthenticationRequest authenticationRequest,
			AuthenticationResponse response, Identity identity,
			Set<String> fieldsToSign) {
		logger.debug("creating athentication response for: "
				+ authenticationRequest);
		response.setIdentity(authenticationRequest.getIdentity());
		response.setNonce(nonceService.createNonce());
		response.setReturnTo(authenticationRequest.getReturnTo());
		response.put("go_to", authenticationRequest.getReturnTo());
		response.setOpEndpoint(opServer + "openid");
		fieldsToSign.add("identity");
		fieldsToSign.add("nonce");
		fieldsToSign.add("return_to");
		if (authenticationRequest.getAssocHandle() == null) {
			/**
			 * state-less mode, association handle will generate and stored on
			 * OP side, not will be send to RP
			 */
		} else {
			fieldsToSign.add("assoc_handle");
		}
		return null;
	}

}
