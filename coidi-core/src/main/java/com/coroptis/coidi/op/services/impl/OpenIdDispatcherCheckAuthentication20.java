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

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.message.CheckAuthenticationResponse;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.google.common.base.Preconditions;

/**
 * When no previous dispatcher process message then this report that message is
 * invalid.
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherCheckAuthentication20 implements OpenIdDispatcher {

    private final static Logger logger = LoggerFactory
	    .getLogger(OpenIdDispatcherCheckAuthentication20.class);

    private final StatelessModeNonceService statelessModeNonceService;

    private final SigningService signingService;

    private final AssociationService associationService;

     
    public OpenIdDispatcherCheckAuthentication20(
	    final StatelessModeNonceService statelessModeNonceService,
	    final SigningService signingService, final AssociationService associationService) {
	this.statelessModeNonceService = Preconditions.checkNotNull(statelessModeNonceService);
	this.signingService = Preconditions.checkNotNull(signingService);
	this.associationService = Preconditions.checkNotNull(associationService);
    }

    @Override
    public AbstractMessage process(Map<String, String> requestParams, HttpSession userSession) {
	if (requestParams.get(OPENID_MODE)
		.equals(CheckAuthenticationRequest.MODE_CHECK_AUTHENTICATION)) {
	    CheckAuthenticationRequest request = new CheckAuthenticationRequest(requestParams);
	    logger.debug("processing: " + request);
	    CheckAuthenticationResponse response = new CheckAuthenticationResponse();
	    Nonce nonce = statelessModeNonceService.getVerifiedNonce(request.getNonce());
	    if (nonce == null) {
		response.setIsValid(false);
		return response;
	    } else {
		if (!statelessModeNonceService.isAssociationValid(nonce, request)) {
		    response.setIsValid(false);
		    return response;
		}
		if (!request.getSignature()
			.equals(signingService.sign(request, nonce.getAssociation()))) {
		    response.setIsValid(false);
		    logger.info("Signature is not valid " + request);
		    return response;
		}
	    }
	    associationService.delete(request.getAssocHandle());
	    response.setIsValid(true);
	    response.setInvalidateHandle(request.getAssocHandle());
	    return response;
	}
	return null;
    }

}
