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

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.google.common.base.Preconditions;

public class StatelessModeNonceServiceImpl implements StatelessModeNonceService {

    @Inject
    private Logger logger;

    @Inject
    private BaseNonceDao baseNonceDao;

    @Inject
    private AssociationTool associationTool;

    @Inject
    private BaseAssociationDao baseAssociationDao;

    @Inject
    private NonceService nonceService;

    @Override
    public Nonce getVerifiedNonce(final String nonce) {
	if (nonce == null) {
	    logger.info("Nonce is null");
	    return null;
	}
	if (!nonceService.verifyNonceExpiration(nonce, 30)) {
	    logger.info("Invalid nonce '" + nonce + "'");
	    return null;
	}
	final Nonce nonceObject = baseNonceDao.getByNonce(nonce);
	if (nonceObject == null) {
	    logger.info("There is no stored such nonce '" + nonce + "'");
	    return null;
	} else {
	    if (nonceObject.getAssociation() == null) {
		logger.info("Nonce '" + nonce + "' is not attached to any association");
		return null;
	    } else {
		if (associationTool.isPrivateAssociation(nonceObject.getAssociation())) {
		    return nonceObject;
		} else {
		    logger.info("Nonce '" + nonce + "' belongs to shared association,"
			    + " should belongs to private association");
		    return null;
		}
	    }
	}
    }

    @Override
    public Boolean isAssociationValid(Nonce nonce, CheckAuthenticationRequest request) {
	Preconditions.checkNotNull(request, "request is null");
	Preconditions.checkNotNull(nonce, "nonce is null");
	String associationHandle = request.getAssocHandle();
	if (associationHandle == null) {
	    logger.info("Association handle is null in " + request.toString());
	    return false;
	}
	Association association = baseAssociationDao.getByAssocHandle(associationHandle);
	if (association == null) {
	    logger.info("Association fo association handle '" + associationHandle + "' is null");
	    return false;
	}
	if (!associationTool.isPrivateAssociation(association)) {
	    logger.info("Association is not private " + association);
	    return false;
	}
	return association.getAssocHandle().equals(nonce.getAssociation().getAssocHandle());
    }

}