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
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.entities.StatelessModeNonce;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.google.common.base.Joiner;

/**
 * Class just return back already processes authentication response. If
 * processing comes here it means that there wasn't any errors and message could
 * be signed.
 * 
 * @author jirout
 * 
 */
public class AuthenticationProcessorTerminator implements AuthenticationProcessor {

    @Inject
    private Logger logger;

    @Inject
    private AssociationDao associationDao;

    @Inject
    private SigningService signingService;

    @Inject
    private StatelessModeNonceService statelessModeNonceService;

    private final AssociationType statelesModeAssociationType;

    private final Joiner joiner = Joiner.on(",").skipNulls();

    public AuthenticationProcessorTerminator(
	    // NO_UCD
	    @Inject @Symbol("op.stateless.mode.association.type") final String assocTypeStr,
	    final Logger logger) {
	statelesModeAssociationType = AssociationType.convert(assocTypeStr);
	this.logger = logger;
	logger.debug("stateless mode association type: " + statelesModeAssociationType);
    }

    @Override
    public AbstractMessage process(AuthenticationRequest authenticationRequest,
	    AuthenticationResponse response, Identity identity, Set<String> fieldsToSign) {
	response.setSigned(joiner.join(fieldsToSign));
	if (authenticationRequest.getAssocHandle() == null) {
	    /**
	     * State-less mode.
	     */
	    signInStatelessMode(response);
	} else {
	    Association association = associationDao.getByAssocHandle(authenticationRequest
		    .getAssocHandle());
	    if (association == null) {
		logger.info("Invalid assoc handle '" + authenticationRequest.getAssocHandle()
			+ "', let's try to response in stateless mode.");
		signInStatelessMode(response);
		response.setInvalidateHandle(authenticationRequest.getAssocHandle());
	    } else {
		response.setAssocHandle(authenticationRequest.getAssocHandle());
		response.setSignature(signingService.sign(response, association));
	    }
	}
	return response;
    }

    private void signInStatelessMode(final AuthenticationResponse response) {
	StatelessModeNonce statelessModeNonce = statelessModeNonceService
		.createStatelessModeNonce(response.getNonce());
	response.setSignature(signingService.sign(response, statelessModeNonce.getMacKey(),
		statelesModeAssociationType));
    }
}
