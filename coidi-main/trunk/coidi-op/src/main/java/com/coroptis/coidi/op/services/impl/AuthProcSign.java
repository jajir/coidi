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

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.google.common.base.Joiner;

/**
 * Class just return back already processes authentication response. If
 * processing comes here it means that there wasn't any errors and message could
 * be signed.
 * 
 * @author jirout
 * 
 */
public class AuthProcSign implements AuthenticationProcessor {

    @Inject
    private BaseAssociationDao associationDao;

    @Inject
    private SigningService signingService;

    private final Joiner joiner = Joiner.on(",").skipNulls();

    @Override
    public AbstractMessage process(AuthenticationRequest authenticationRequest,
	    AuthenticationResponse response, Identity identity, Set<String> fieldsToSign) {
	response.setSigned(joiner.join(fieldsToSign));
	Association association = associationDao.getByAssocHandle(authenticationRequest
		.getAssocHandle());
	if (association == null) {
	    throw new CoidiException("Invalid assoc handle '"
		    + authenticationRequest.getAssocHandle()
		    + "', let's try to response in stateless mode.");
	} else {
	    response.setAssocHandle(authenticationRequest.getAssocHandle());
	    response.setSignature(signingService.sign(response, association));
	}
	return response;
    }

}
