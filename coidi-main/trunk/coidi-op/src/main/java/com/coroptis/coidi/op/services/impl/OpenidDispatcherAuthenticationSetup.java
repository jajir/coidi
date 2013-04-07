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
import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

public class OpenidDispatcherAuthenticationSetup implements OpenIdDispatcher {

    @Inject
    private Logger logger;

    @Inject
    private AuthenticationService authenticationService;

    @Inject
    private IdentityService identityService;

    @Inject
    private AuthenticationProcessor authenticationProcessor;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(Map<String, String> requestParams,
	    UserSessionSkeleton userSession) {
	if (requestParams.get(OPENID_MODE).equals(AuthenticationRequest.MODE_CHECKID_SETUP)) {
	    AuthenticationRequest authenticationRequest = new AuthenticationRequest(requestParams);
	    if (!authenticationService.isAuthenticationRequest(authenticationRequest)) {
		logger.debug("authentication request doesn't contains any idenity field");
		return null;
	    }

	    if (StringUtils.isEmpty(authenticationRequest.getClaimedId())) {
		if (StringUtils.isEmpty(authenticationRequest.getIdentity())) {
		    logger.debug("it's not authentication request,"
			    + " probably it's authentication extension");
		    return null;
		} else {
		    return negativeResponseGenerator
			    .simpleError("In authentication request is not filled openid.claimed_id"
				    + " but openid.identity is empty" + " It's invalid combination");
		}
	    } else {
		if (StringUtils.isEmpty(authenticationRequest.getIdentity())) {
		    /**
		     * Use openid.claimed_id as openid.identity.
		     */
		    authenticationRequest.setIdentity(authenticationRequest.getClaimedId());
		}
	    }

	    if (!userSession.isLogged()) {
		logger.debug("User is not logged in.");
		userSession.setAuthenticationRequest(authenticationRequest);
		return new RedirectResponse();
	    }

	    if (AuthenticationRequest.IDENTITY_SELECT.equals(authenticationRequest.getIdentity())) {
		if (StringUtils.isEmpty(authenticationRequest.getSelectedIdentity())) {
		    return negativeResponseGenerator.applicationError("requested identity is '"
			    + AuthenticationRequest.IDENTITY_SELECT
			    + "' but user didin't put selected identity in property '"
			    + AuthenticationRequest.USERS_SELECTED_IDENTITY + "'",
			    NegativeResponseGenerator.APPLICATION_ERROR_SELECT_IDENTITY);
		} else {
		    authenticationRequest.setIdentity(authenticationRequest.getSelectedIdentity());
		}
	    }

	    Identity identity = identityService.getByOpIdentifier(authenticationRequest
		    .getIdentity());

	    if (identity == null) {
		logger.debug("Unable to find idenity by '" + authenticationRequest.getIdentity()
			+ "'.");
		return new RedirectResponse();
	    }

	    if (identityService.isUsersOpIdentifier(userSession.getIdUser(),
		    authenticationRequest.getIdentity())) {
		Set<String> fieldToSign = new HashSet<String>();
		return authenticationProcessor.process(authenticationRequest,
			new AuthenticationResponse(), identity, fieldToSign);
	    } else {
		return negativeResponseGenerator.simpleError("Identity '"
			+ authenticationRequest.getIdentity() + "' doesn't belongs to user '"
			+ userSession.getIdUser() + "'.");
	    }

	}
	return null;
    }

    class RedirectResponse extends AbstractOpenIdResponse {

	@Override
	public boolean isUrl() {
	    return true;
	}

	@Override
	public String getMessage() {
	    return "./login";
	}
    };
}
