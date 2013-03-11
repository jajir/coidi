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

import java.util.Map.Entry;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.StatelessModeNonce;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.google.common.base.Preconditions;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger logger;

    @Inject
    private NonceService nonceService;

    @Inject
    private AssociationDao associationDao;

    @Inject
    private SigningService signingService;

    @Inject
    private StatelessModeNonceService statelessModeNonceService;

    @Inject
    @Symbol("op.server")
    private String opServer;

    private final AssociationType statelesModeAssociationType;

    public AuthenticationServiceImpl(
	    // NO_UCD
	    @Inject @Symbol("op.stateless.mode.association.type") final String assocTypeStr,
	    final Logger logger) {
	statelesModeAssociationType = AssociationType.convert(assocTypeStr);
	this.logger = logger;
	logger.debug("stateless mode association type: " + statelesModeAssociationType);
    }

    @Override
    public boolean isAuthenticationRequest(final AuthenticationRequest authenticationRequest) {
	Preconditions.checkNotNull(authenticationRequest, "authenticationRequest");
	if (AuthenticationRequest.MODE_CHECKID_SETUP.equals(authenticationRequest.getMode())
		|| AuthenticationRequest.MODE_CHECKID_IMMEDIATE.equals(authenticationRequest
			.getMode())) {
	    return authenticationRequest.getIdentity() != null
		    && authenticationRequest.getClaimedId() != null;
	}
	return false;
    }

    @Override
    public AuthenticationResponse process(AuthenticationRequest authenticationRequest) {
	AuthenticationResponse response = new AuthenticationResponse();
	response.setIdentity(authenticationRequest.getIdentity());
	response.setNonce(nonceService.createNonce());
	response.setReturnTo(authenticationRequest.getReturnTo());
	response.put("go_to", authenticationRequest.getReturnTo());
	response.setOpEndpoint(opServer + "openid");
	if (authenticationRequest.getAssocHandle() == null) {
	    // state less mode
	    signInStatelessMode(response);
	} else {
	    Association association = associationDao.getByAssocHandle(authenticationRequest
		    .getAssocHandle());
	    if (association == null) {
		logger.info("Invalid assoc handle '" + authenticationRequest.getAssocHandle()
			+ "', let's try to response in stateless mode.");
		signInStatelessMode(response);
		response.setInvalidateHandle(authenticationRequest.getAssocHandle());
	    }
	    response.setAssocHandle(authenticationRequest.getAssocHandle());
	    response.setSigned("assoc_handle,identity,nonce,return_to");
	    response.setSignature(signingService.sign(response, association));
	}
	logger.debug("authentication response: " + response.getMessage());
	return response;
    }

    private void signInStatelessMode(final AuthenticationResponse response) {
	StatelessModeNonce statelessModeNonce = statelessModeNonceService
		.createStatelessModeNonce(response.getNonce());
	response.setSigned("identity,nonce,return_to");
	response.setSignature(signingService.sign(response, statelessModeNonce.getMacKey(),
		statelesModeAssociationType));
    }

    @Override
    public String getNameSpace(AuthenticationRequest authenticationRequest, String nameSpaceUrl) {
	Preconditions.checkNotNull(nameSpaceUrl, "nameSpaceUrl");
	for (Entry<String, String> entry : authenticationRequest.getMap().entrySet()) {
	    if (nameSpaceUrl.equals(entry.getValue())) {
		// key is in format 'openid.ns.name', 'openid.ns.' should be
		// extracted
		return entry.getKey().substring("openid.ns.".length());
	    }
	}
	return null;
    }
}
