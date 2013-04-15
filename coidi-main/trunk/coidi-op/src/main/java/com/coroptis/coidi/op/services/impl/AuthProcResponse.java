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
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;

public class AuthProcResponse implements AuthenticationProcessor {

    @Inject
    private Logger logger;

    @Inject
    @Symbol("op.server")
    private String opServer;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(AuthenticationRequest authenticationRequest,
	    AuthenticationResponse response, Identity identity, Set<String> fieldsToSign) {
	logger.debug("creating athentication response for: " + authenticationRequest);
	if (authenticationRequest.getIdentity() == null) {
	    if (authenticationRequest.getClaimedId() == null) {
		/**
		 * Both are empty. It could be some OpenID extension request.
		 */
	    } else {
		return negativeResponseGenerator.simpleError("field '"
			+ AuthenticationResponse.CLAIMED_ID + "' is filled and field '"
			+ AuthenticationResponse.IDENTITY + "' is empty, this is forbiden state.");
	    }
	} else {
	    if (authenticationRequest.getClaimedId() == null) {
		return negativeResponseGenerator.simpleError("field '"
			+ AuthenticationResponse.CLAIMED_ID + "' is empty and field '"
			+ AuthenticationResponse.IDENTITY + "' is filled, this is forbiden state.");
	    } else {
		/**
		 * Both are filled
		 */
		response.setIdentity(authenticationRequest.getIdentity());
		response.setClaimedId(authenticationRequest.getClaimedId());
		fieldsToSign.add(AuthenticationResponse.CLAIMED_ID);
		fieldsToSign.add(AuthenticationResponse.IDENTITY);
	    }
	}
	response.setReturnTo(authenticationRequest.getReturnTo());
	response.put("go_to", authenticationRequest.getReturnTo());
	response.setOpEndpoint(opServer + "openid");
	fieldsToSign.add(AuthenticationResponse.RETURN_TO);
	fieldsToSign.add(AuthenticationResponse.NONCE);
	fieldsToSign.add(AuthenticationResponse.OP_ENDPOINT);
	return null;
    }

}
