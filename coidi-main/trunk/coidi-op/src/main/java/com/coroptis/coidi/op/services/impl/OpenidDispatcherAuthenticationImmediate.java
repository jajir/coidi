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

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

/**
 * Process openid.more=checkid_immediate.
 * 
 * @author jirout
 * 
 */
public class OpenidDispatcherAuthenticationImmediate implements OpenIdDispatcher {

    @Inject
    private BaseAssociationDao associationDao;

    @Inject
    private AuthenticationService authenticationService;

    @Inject
    private AuthenticationProcessor authenticationProcessor;

    @Inject
    private IdentityService identityService;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(Map<String, String> requestParams,
	    UserSessionSkeleton userSession) {
	if (requestParams.get(OPENID_MODE).equals(AuthenticationRequest.MODE_CHECKID_IMMEDIATE)) {
	    AuthenticationRequest authenticationRequest = new AuthenticationRequest(requestParams);
	    if (!authenticationService.isAuthenticationRequest(authenticationRequest)) {
		return negativeResponseGenerator
			.simpleError("authentication request doesn't contains any idenity field");
	    }

	    Association association = associationDao.getByAssocHandle(authenticationRequest
		    .getAssocHandle());
	    if (association == null) {
		return negativeResponseGenerator.simpleError("Unable to find association handle '"
			+ authenticationRequest.getAssocHandle() + "'");
	    }
	    if (association.getExpiredIn().before(new Date())) {
		return negativeResponseGenerator.simpleError("Assocition handle '"
			+ authenticationRequest.getAssocHandle() + "' expires at "
			+ association.getExpiredIn());
	    }

	    Identity identity = identityService.getIdentityByName(authenticationRequest
		    .getIdentity());
	    if (identity == null) {
		return negativeResponseGenerator.simpleError("There is no such identity '"
			+ authenticationRequest.getIdentity() + "'");
	    }

	    if (!identityService.isIdentityLogged(userSession, identity)) {
		return negativeResponseGenerator.simpleError("Idenity '"
			+ authenticationRequest.getIdentity() + "' is not logged in");
	    }

	    AuthenticationResponse response = new AuthenticationResponse();
	    Set<String> fieldToSign = new HashSet<String>();
	    AbstractMessage out = authenticationProcessor.process(authenticationRequest, response,
		    identity, fieldToSign);
	    return out;
	}
	return null;
    }
}
