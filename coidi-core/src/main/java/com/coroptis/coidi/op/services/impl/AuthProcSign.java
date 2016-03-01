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

import javax.servlet.http.HttpSession;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.services.AuthProc;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

/**
 * Class just sign already processes authentication response. If processing
 * comes here it means that there wasn't any errors and message could be signed.
 * 
 * @author jirout
 * 
 */
public class AuthProcSign implements AuthProc {

    private final BaseAssociationDao associationDao;

    private final SigningService signingService;

    private final Joiner joiner = Joiner.on(",").skipNulls();

     
    public AuthProcSign(final BaseAssociationDao associationDao,
	    final SigningService signingService) {
	this.associationDao = Preconditions.checkNotNull(associationDao);
	this.signingService = Preconditions.checkNotNull(signingService);
    }

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final HttpSession userSession,
	    final Set<String> fieldsToSign) {
	response.setSigned(joiner.join(fieldsToSign));
	response.setAssocHandle(response.getAssocHandle());
	Association association = associationDao.getByAssocHandle(response.getAssocHandle());
	if (association == null) {
	    throw new CoidiException("Invalid assoc handle '" + response.getAssocHandle() + "'.");
	} else {
	    response.setSignature(signingService.sign(response, association));
	}
	return response;
    }

}
