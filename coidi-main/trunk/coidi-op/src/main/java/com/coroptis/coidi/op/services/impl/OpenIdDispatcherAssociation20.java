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

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
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

	    return associationProcessor.processAssociation(request, request.getSessionType(),
		    request.getAssociationType());
	} else {
	    return null;
	}
    }

}
