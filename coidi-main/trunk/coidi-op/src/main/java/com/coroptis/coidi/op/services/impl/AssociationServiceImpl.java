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

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.CryptoService;
import com.google.common.base.Preconditions;

public class AssociationServiceImpl implements AssociationService {

    @Inject
    private BaseAssociationDao baseAssociationDao;

    @Inject
    private CryptoService cryptoService;

    @Inject
    private AssociationTool associationTool;

    @Inject
    private ConvertorService convertorService;

    @Override
    public void delete(final String associationHandle) {
	Association association = baseAssociationDao.getByAssocHandle(associationHandle);
	if (association != null) {
	    baseAssociationDao.delete(association);
	}
    }

    @Override
    public boolean isValid(final String assoc_handle) {
	if (assoc_handle == null) {
	    return false;
	}
	Association association = baseAssociationDao.getByAssocHandle(assoc_handle);
	if (association == null) {
	    return false;
	}
	return new Date().before(association.getExpiredIn());
    }

    @Override
    public Association createAssociation(final AssociationType associationType,
	    final SessionType sessionType) {
	Preconditions.checkNotNull(associationType, "associationType is null");
	Preconditions.checkNotNull(sessionType, "sessionType is null");
	Association association = buildAssociation();
	association.setAssociationType(associationType);
	association.setSessionType(sessionType);
	return association;
    }

    @Override
    public Association createStateLessAssociation() {
	Association association = buildAssociation();
	association.setAssociationType(associationTool.getDefaultAssociationType());
	association.setMacKey(convertorService.convertToString(cryptoService
		.generateAssociationRandom(association.getAssociationType())));
	return association;
    }

    private Association buildAssociation() {
	Association association = baseAssociationDao.createNewInstance();
	association.setAssocHandle(cryptoService.generateUUID());
	association.setExpiredIn(associationTool.getTimeToLive());
	return association;
    }

}
