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

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.services.AssociationProcessor;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

/**
 * Process openid.mode=associate. OP create valid association and share it with
 * RP.
 * 
 * @author jirout
 * 
 */
public class OpenIdDispatcherAssociation20 implements OpenIdDispatcher {

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Inject
    private AssociationProcessor associationProcessor;

    @Override
    public AbstractMessage process(Map<String, String> requestParams, HttpSession userSession) {
	if (requestParams.get(OPENID_MODE).equals(AbstractMessage.MODE_ASSOCIATE)) {

	    final String assocTypeName = requestParams.get(OPENID_ASSOC_TYPE);
	    if (assocTypeName == null) {
		return negativeResponseGenerator.missingParameter(OPENID_ASSOC_TYPE);
	    } else if (AssociationType.convert(assocTypeName) == null) {
		return negativeResponseGenerator.buildError("Invalid value '", assocTypeName,
			"' of property '", OPENID_ASSOC_TYPE, "'");
	    }

	    final String sessionTypeName = requestParams.get(OPENID_SESSION_TYPE);
	    if (sessionTypeName == null) {
		return negativeResponseGenerator.missingParameter(OPENID_SESSION_TYPE);
	    } else if (SessionType.convert(sessionTypeName) == null) {
		return negativeResponseGenerator.buildError("Invalid value '", sessionTypeName,
			"' of property '", OPENID_SESSION_TYPE, "'");
	    }

	    final String dhConsumerPublic = requestParams.get(OPENID_DH_CONSUMER_PUBLIC);
	    if (dhConsumerPublic == null) {
		return negativeResponseGenerator.missingParameter(OPENID_DH_CONSUMER_PUBLIC);
	    }
	    //TODO values are computed twice here and in AssociationRequest getters.
	    
	    AssociationRequest request = new AssociationRequest(requestParams);
	    return associationProcessor.processAssociation(request, request.getSessionType(),
		    request.getAssociationType());
	} else {
	    return null;
	}
    }

}
