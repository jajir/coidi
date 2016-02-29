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

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.AuthProc;
import com.google.common.base.Preconditions;

/**
 * When response doesn't contains value in association handle than it's
 * state-less mode. In that case new state-less association have to be created.
 * 
 * @author jirout
 * 
 */
public class AuthProcStateLessAssociation implements AuthProc {

    private final static Logger logger = LoggerFactory
	    .getLogger(AuthProcStateLessAssociation.class);

    private final AssociationService associationService;

    private final BaseAssociationDao baseAssociationDao;

    private final BaseNonceDao baseNonceDao;

    @Inject
    public AuthProcStateLessAssociation(final AssociationService associationService,
	    final BaseAssociationDao baseAssociationDao, final BaseNonceDao baseNonceDao) {
	this.associationService = Preconditions.checkNotNull(associationService);
	this.baseAssociationDao = Preconditions.checkNotNull(baseAssociationDao);
	this.baseNonceDao = Preconditions.checkNotNull(baseNonceDao);

    }

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final HttpSession userSession,
	    final Set<String> fieldsToSign) {
	logger.debug("processing nonce: " + authenticationRequest);
	if (StringUtils.isEmpty(response.getAssocHandle())) {
	    logger.debug("Entering into state-less mode.");
	    /**
	     * State-less mode, association handle will be generated and stored
	     * on OP side, and will be send to RP.
	     */
	    Association association = associationService.createStateLessAssociation();

	    /**
	     * If nonce was created in response than will be persisted. In
	     * OpenID 1.1 could be missing.
	     */
	    if (StringUtils.isNotEmpty(response.getNonce())) {
		Nonce nonce = baseNonceDao.createNewInstance();
		nonce.setNonce(response.getNonce());
		nonce.setAssociation(association);
		association.setNonces(new HashSet<Nonce>());
		association.getNonces().add(nonce);
	    }
	    baseAssociationDao.create(association);
	    response.setAssocHandle(association.getAssocHandle());
	}
	return null;
    }

}
