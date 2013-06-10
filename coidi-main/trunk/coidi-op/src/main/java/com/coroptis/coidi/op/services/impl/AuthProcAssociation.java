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
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.AuthenticationProcessor;

/**
 * Perform association verification. It validates that association exists and is
 * valid. When association is valid than copy it value to response message
 * otherwise copy it to invalidate handle and switch to state-less mode.
 * 
 * @author jirout
 * 
 */
public class AuthProcAssociation implements AuthenticationProcessor {

    @Inject
    private Logger logger;

    @Inject
    private AssociationService associationService;

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final UserSessionSkeleton userSession,
	    final Set<String> fieldsToSign) {
	logger.debug("verifying association: " + authenticationRequest);

	/**
	 * There could be problem null value in field association handle and
	 * empty value are threat in a same way as invalid association.
	 */
	if (associationService.isValid(authenticationRequest.getAssocHandle())) {
	    response.setAssocHandle(authenticationRequest.getAssocHandle());
	} else {
	    response.setInvalidateHandle(authenticationRequest.getAssocHandle());
	    response.setAssocHandle(null);
	    logger.debug("Invalid association handle '" + authenticationRequest.getAssocHandle()
		    + "'");
	}
	return null;
    }

}
