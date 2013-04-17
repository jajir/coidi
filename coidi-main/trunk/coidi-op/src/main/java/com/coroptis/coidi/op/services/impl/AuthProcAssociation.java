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
import java.util.Set;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;

/**
 * Perform association verification. It validates that association exists and is
 * valid otherwise switch to stateless mode.
 * 
 * @author jirout
 * 
 */
public class AuthProcAssociation implements AuthenticationProcessor {

    @Inject
    private Logger logger;

    @Inject
    private BaseAssociationDao associationDao;

    @Override
    public AbstractMessage process(AuthenticationRequest authenticationRequest,
	    AuthenticationResponse response, Identity identity, Set<String> fieldsToSign) {
	logger.debug("verifying association: " + authenticationRequest);

	if (authenticationRequest.getAssocHandle() == null) {
	    response.setAssocHandle(null);
	} else {
	    Association association = associationDao.getByAssocHandle(authenticationRequest
		    .getAssocHandle());
	    if (association == null) {
		response.setInvalidateHandle(authenticationRequest.getAssocHandle());
		response.setAssocHandle(null);
		logger.debug("Unable to find association handle '"
			+ authenticationRequest.getAssocHandle() + "'");
	    } else {
		if (association.getExpiredIn().before(new Date())) {
		    response.setInvalidateHandle(authenticationRequest.getAssocHandle());
		    response.setAssocHandle(null);
		    logger.debug("Assocition handle '" + authenticationRequest.getAssocHandle()
			    + "' expires at " + association.getExpiredIn());
		}
	    }
	}
	return null;
    }

}
