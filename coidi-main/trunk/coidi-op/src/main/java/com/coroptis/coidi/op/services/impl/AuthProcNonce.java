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
import java.util.Set;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.AuthenticationProcessor;

/**
 * Verify that association handle is valid. If association handle is not valid
 * created state less association. Also create nonce.
 * 
 * @author jirout
 * 
 */
public class AuthProcNonce implements AuthenticationProcessor {

    @Inject
    private Logger logger;

    @Inject
    private AssociationService associationService;

    @Inject
    private NonceService nonceService;

    @Inject
    private BaseAssociationDao baseAssociationDao;

    @Inject
    private BaseNonceDao baseNonceDao;

    @Override
    public AbstractMessage process(AuthenticationRequest authenticationRequest,
	    AuthenticationResponse response, Identity identity,
	    final UserSessionSkeleton userSession, Set<String> fieldsToSign) {
	logger.debug("processing nonce: " + authenticationRequest);
	response.setNonce(nonceService.createNonce());
	if (associationService.isValid(authenticationRequest.getAssocHandle())) {
	    response.setAssocHandle(authenticationRequest.getAssocHandle());
	} else {
	    logger.debug("Entering into state-less mode, assoc_handle is invalid.");
	    /**
	     * State-less mode, association handle will generate and stored on
	     * OP side, not will be send to RP.
	     */
	    Association association = associationService.createStateLessAssociation();
	    Nonce nonce = baseNonceDao.createNewInstance();
	    nonce.setNonce(response.getNonce());
	    nonce.setAssociation(association);
	    association.setNonces(new HashSet<Nonce>());
	    association.getNonces().add(nonce);
	    baseAssociationDao.create(association);
	    response.setAssocHandle(association.getAssocHandle());
	}
	fieldsToSign.add(AuthenticationResponse.ASSOC_HANDLE);
	return null;
    }

}
