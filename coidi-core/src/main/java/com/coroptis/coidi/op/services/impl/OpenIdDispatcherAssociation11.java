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
import com.google.common.base.Preconditions;

/**
 * Process openid.mode=associate. OP create valid association and share it with
 * RP.
 * 
 * @author jirout
 * 
 */
public class OpenIdDispatcherAssociation11 implements OpenIdDispatcher {

    private final NegativeResponseGenerator negativeResponseGenerator;

    private final AssociationProcessor associationProcessor;

    @Inject
    public OpenIdDispatcherAssociation11(final NegativeResponseGenerator negativeResponseGenerator,
	    final AssociationProcessor associationProcessor) {
	this.negativeResponseGenerator = Preconditions.checkNotNull(negativeResponseGenerator);
	this.associationProcessor = Preconditions.checkNotNull(associationProcessor);
    }

    @Override
    public AbstractMessage process(Map<String, String> requestParams, HttpSession userSession) {
	if (requestParams.get(OPENID_MODE).equals(AbstractMessage.MODE_ASSOCIATE)) {
	    AssociationRequest request = new AssociationRequest(requestParams);
	    if (request.getDhConsumerPublic() == null) {
		return negativeResponseGenerator.buildErrorWithNs(AbstractMessage.OPENID_NS_11,
			"Parameter '", AssociationRequest.DH_CONSUMER_PUBLIC, "' is required");
	    }
	    AbstractMessage out = associationProcessor.processAssociation(request,
		    extractSessionType(request), extractAssociationType(request));
	    /**
	     * Prevent returning no-encryption value
	     */
	    if (SessionType.NO_ENCRYPTION.getName()
		    .equals(out.get(AssociationRequest.SESSION_TYPE))) {
		out.put(AssociationRequest.SESSION_TYPE, null);
	    }
	    return out;
	} else {
	    return null;
	}
    }

    private SessionType extractSessionType(final AssociationRequest request) {
	if (request.getSessionType() == null) {
	    return SessionType.NO_ENCRYPTION;
	}
	return request.getSessionType();
    }

    private AssociationType extractAssociationType(final AssociationRequest request) {
	if (request.getAssociationType() == null) {
	    return AssociationType.HMAC_SHA1;
	}
	/**
	 * It allows to use not just HMAC_SHA1. Specification allows just
	 * HMAC_SHA1.
	 */
	return request.getAssociationType();
    }

}
