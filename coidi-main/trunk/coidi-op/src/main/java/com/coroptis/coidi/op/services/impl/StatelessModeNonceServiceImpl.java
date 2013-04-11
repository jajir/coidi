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
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.google.common.base.Preconditions;

public class StatelessModeNonceServiceImpl implements StatelessModeNonceService {

    private final Logger logger;

    @Inject
    private BaseNonceDao statelessModeNonceDao;

    @Inject
    private SigningService signingService;

    private final AssociationType statelesModeAssociationType;

    public StatelessModeNonceServiceImpl(
	    @Inject @Symbol("op.stateless.mode.association.type") final String assocTypeStr,
	    final Logger logger) {
	// FIXME it's moved in associationTool
	this.logger = logger;
	statelesModeAssociationType = AssociationType.convert(assocTypeStr);
	logger.debug("Association type for stateless mode: " + statelesModeAssociationType);
    }

    @Override
    public Boolean isValidCheckAuthenticationRequest(final CheckAuthenticationRequest request) {
	Nonce nonce = statelessModeNonceDao.getByNonce(request.getNonce());
	Preconditions.checkNotNull(nonce, "nonce '" + request.getNonce()
		+ "' wasn't found during sateless authentication");
	Preconditions.checkNotNull(nonce.getAssociation(), "nonce '" + nonce.getNonce()
		+ "' doesn't have any association");
	String signature = signingService.sign(request, nonce.getAssociation().getMacKey(),
		statelesModeAssociationType);
	if (signature.equals(request.getSignature())) {
	    return true;
	} else {
	    logger.info("Signature from check_authentication message '" + request.getSignature()
		    + "' is not same as computed one '" + signature + "'");
	    return false;
	}
    }
}
