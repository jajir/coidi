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

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.services.AuthProc;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.UserVerifier;
import com.google.common.base.Preconditions;

/**
 * Verify that identity and claimed identities are correctly entered. Initialize
 * authentication response.
 * 
 * @author jirout
 * 
 */
public class AuthProcIdentity20 implements AuthProc {

    private final static Logger logger = LoggerFactory.getLogger(AuthProcIdentity20.class);

    private final String opServer;

    private final UserVerifier userVerifier;

    private final NegativeResponseGenerator negativeResponseGenerator;

    @Inject
    public AuthProcIdentity20(final OpConfigurationService configurationService,
	    final UserVerifier userVerifier,
	    final NegativeResponseGenerator negativeResponseGenerator) {
	this.opServer = configurationService.getOpServerUrl();
	this.userVerifier = Preconditions.checkNotNull(userVerifier);
	this.negativeResponseGenerator = Preconditions.checkNotNull(negativeResponseGenerator);
    }

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final HttpSession session,
	    final Set<String> fieldsToSign) {
	logger.debug("creating authentication response for: " + authenticationRequest);
	if (StringUtils.isEmpty(authenticationRequest.getIdentity())) {
	    if (StringUtils.isEmpty(authenticationRequest.getClaimedId())) {
		/**
		 * Both are empty. It could be some OpenID extension request.
		 */
	    } else {
		return negativeResponseGenerator.buildError("field '",
			AuthenticationResponse.CLAIMED_ID, "' is filled and field '",
			AuthenticationResponse.IDENTITY, "' is empty, this is forbiden state.");
	    }
	} else {
	    if (StringUtils.isEmpty(authenticationRequest.getClaimedId())) {
		return negativeResponseGenerator.buildError("field '",
			AuthenticationResponse.CLAIMED_ID, "' is empty and field '",
			AuthenticationResponse.IDENTITY, "' is filled, this is forbiden state.");
	    } else {
		/**
		 * Both are filled
		 */
		if (AuthenticationRequest.IDENTITY_SELECT
			.equals(authenticationRequest.getIdentity())) {
		    final String identity = userVerifier.getSelectedIdenity(session);
		    if (StringUtils.isEmpty(identity)) {
			return negativeResponseGenerator.applicationError(
				"requested identity is '" + AuthenticationRequest.IDENTITY_SELECT
					+ "' but user didn't selected identity",
				NegativeResponseGenerator.APPLICATION_ERROR_SELECT_IDENTITY);
		    }
		    response.setIdentity(identity);
		    response.setClaimedId(identity);
		} else {
		    response.setIdentity(authenticationRequest.getIdentity());
		    response.setClaimedId(authenticationRequest.getClaimedId());
		}
		fieldsToSign.add(AuthenticationResponse.CLAIMED_ID);
		fieldsToSign.add(AuthenticationResponse.IDENTITY);
	    }
	}
	response.setReturnTo(authenticationRequest.getReturnTo());
	response.setOpEndpoint(opServer + "openid");
	fieldsToSign.add(AuthenticationResponse.RETURN_TO);
	fieldsToSign.add(AuthenticationResponse.OP_ENDPOINT);
	return null;
    }

}
