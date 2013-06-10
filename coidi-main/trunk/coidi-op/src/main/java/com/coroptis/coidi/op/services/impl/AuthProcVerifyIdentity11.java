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
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;

/**
 * Verify that user is logged in. If is not logged in than return negative
 * authentication response.
 * 
 * @author jirout
 * 
 */
public class AuthProcVerifyIdentity11 implements AuthenticationProcessor {

    @Inject
    private Logger logger;

    @Inject
    private IdentityService identityService;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final UserSessionSkeleton userSession,
	    final Set<String> fieldsToSign) {
	logger.debug("verify identity: " + authenticationRequest);
	if (!userSession.isLogged()) {
	    return negativeResponseGenerator.simpleError("User is not logged at OP",
		    AbstractMessage.OPENID_NS_11);
	}

	Identity identity = identityService.getByOpLocalIdentifier(authenticationRequest
		.getIdentity());
	if (identity == null) {
	    logger.debug("Requested identity '" + authenticationRequest.getIdentity()
		    + "' doesn't exists.");
	    return negativeResponseGenerator.simpleError("Requested identity '"
		    + authenticationRequest.getIdentity() + "' doesn't exists.",
		    AbstractMessage.OPENID_NS_11);
	}
	if (!identityService.isUsersOpIdentifier(userSession.getIdUser(),
		authenticationRequest.getIdentity())) {
	    logger.debug("Identity '" + authenticationRequest.getIdentity()
		    + "' doesn't belongs to user '" + userSession.getIdUser() + "'.");
	    return negativeResponseGenerator.simpleError("Requested identity '"
		    + authenticationRequest.getIdentity() + "' doesn't exists.",
		    AbstractMessage.OPENID_NS_11);
	}
	return null;

    }

}