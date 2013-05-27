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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.util.OpenId11;

/**
 * Authentication setup for OpenID 1.1.
 * 
 * @author jirout
 * 
 */
public class OpenidDispatcherAuthenticationSetup11 implements OpenIdDispatcher {

    @Inject
    private Logger logger;

    @Inject
    private IdentityService identityService;

    @Inject
    @OpenId11
    private AuthenticationProcessor authenticationProcessor;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    public AbstractMessage process(Map<String, String> requestParams,
	    UserSessionSkeleton userSession) {
	if (requestParams.get(OPENID_MODE).equals(AuthenticationRequest.MODE_CHECKID_SETUP)) {
	    AuthenticationRequest authenticationRequest = new AuthenticationRequest(requestParams);
	    if (StringUtils.isEmpty(authenticationRequest.getIdentity())) {
		return negativeResponseGenerator.simpleError("Field 'openid.identity' is empty.",
			AbstractMessage.OPENID_NS_11);
	    }

	    if (!userSession.isLogged()) {
		logger.debug("User is not logged in.");
		userSession.setAuthenticationRequest(authenticationRequest);
		return negativeResponseGenerator.applicationError("User is not logged in",
			NegativeResponseGenerator.APPLICATION_ERROR_PLEASE_LOGIN,
			AbstractMessage.OPENID_NS_11);
	    }

	    Identity identity = identityService.getByOpLocalIdentifier(authenticationRequest
		    .getIdentity());
	    if (identity == null) {
		logger.debug("Requested identity '" + authenticationRequest.getIdentity()
			+ "' doesn't exists.");
		return identityBelongsToOtherUser(authenticationRequest.getIdentity(),
			userSession.getIdUser());
	    }

	    if (identityService.isUsersOpIdentifier(userSession.getIdUser(),
		    authenticationRequest.getIdentity())) {
		Set<String> fieldToSign = new HashSet<String>();
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setNameSpace(AbstractMessage.OPENID_NS_11);
		return authenticationProcessor.process(authenticationRequest,
			authenticationResponse, identity, userSession, fieldToSign);
	    } else {
		return identityBelongsToOtherUser(authenticationRequest.getIdentity(),
			userSession.getIdUser());
	    }
	}
	return null;
    }

    private ErrorResponse identityBelongsToOtherUser(final String identity, final Integer idUser) {
	return negativeResponseGenerator.simpleError("Identity '" + identity
		+ "' doesn't belongs to user '" + idUser + "'.", AbstractMessage.OPENID_NS_11);
    }
}
