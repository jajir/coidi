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

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.message.AssociationResponse;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.CryptoService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

/**
 * Process openid.mode=associate. OP create valid association and share it with
 * RP.
 * 
 * @author jirout
 * 
 */
public class OpenIdDispatcherAssociation implements OpenIdDispatcher {

    @Inject
    private CryptoService cryptoService;

    @Inject
    private Logger logger;

    @Inject
    private AssociationTool associationTool;

    @Inject
    private BaseAssociationDao associationDao;

    @Inject
    private CryptoSessionService cryptoSessionService;

    @Inject
    private CryptographyService cryptographyService;

    @Inject
    private ConvertorService convertorService;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(Map<String, String> requestParams,
	    UserSessionSkeleton userSession) {
	if (requestParams.get(OPENID_MODE).equals(AbstractMessage.MODE_ASSOCIATE)) {
	    AssociationRequest request = new AssociationRequest(requestParams);
	    if (request.getAssociationType() == null) {
		return negativeResponseGenerator.simpleError("Parameter '"
			+ AssociationRequest.ASSOCIATION_TYPE + "' is required");
	    }
	    if (request.getSessionType() == null) {
		return negativeResponseGenerator.simpleError("Parameter '"
			+ AssociationRequest.SESSION_TYPE + "' is required");
	    }
	    if (request.getDhConsumerPublic() == null) {
		return negativeResponseGenerator.simpleError("Parameter '"
			+ AssociationRequest.DH_CONSUMER_PUBLIC + "' is required");
	    }
	    // TODO it's duplicated code, see association service.
	    Association association = associationDao.createNewInstance();
	    association.setAssocHandle(cryptoService.generateUUID());
	    association.setSessionType(request.getSessionType());
	    association.setExpiredIn(associationTool.getTimeToLive());
	    association.setAssociationType(request.getAssociationType());

	    AssociationResponse out = new AssociationResponse();
	    if (request.getSessionType().equals(SessionType.no_encription)) {
		logger.info("No encryption was setup during association request/response.");
		byte macKey[] = cryptoService.generateAssociationRandom(association
			.getAssociationType());
		association.setMacKey(convertorService.convertToString(macKey));
		out.setMacKey(macKey);
	    } else {
		association.setMacKey(convertorService.convertToString(cryptoService
			.generateSessionRandom(association.getSessionType())));
		KeyPair cryptoSession = cryptoSessionService.generateCryptoSession(request);
		out.setEncMacKey(cryptographyService.encryptSecret(cryptoSession,
			request.getDhConsumerPublic(),
			convertorService.convertToBytes(association.getMacKey()),
			request.getSessionType()));
		out.setDhServerPublic(cryptoSession.getPublicKey());
	    }

	    associationDao.create(association);

	    out.setSessionType(association.getSessionType());
	    out.setAssociationType(association.getAssociationType());
	    out.setAssocHandle(association.getAssocHandle());
	    out.setExpiresIn(String.valueOf((association.getExpiredIn().getTime() - System
		    .currentTimeMillis()) / 1000L));
	    return out;
	} else {
	    return null;
	}
    }

}
