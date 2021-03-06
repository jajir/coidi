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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.entities.IdentitySreg;
import com.coroptis.coidi.op.services.AuthProc;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.SregService;
import com.google.common.base.Preconditions;

/**
 * Simple Registration Extension 1.0.
 * 
 * @author jirout
 * 
 */
public class AuthProcSreg10 implements AuthProc {

    private final static Logger logger = LoggerFactory.getLogger(AuthProcSreg10.class);

    private final SregService sregService;

    private final IdentityService identityService;

    private final NegativeResponseGenerator negativeResponseGenerator;

     
    public AuthProcSreg10(final SregService sregService, final IdentityService identityService,
	    final NegativeResponseGenerator negativeResponseGenerator) {
	this.sregService = Preconditions.checkNotNull(sregService);
	this.identityService = Preconditions.checkNotNull(identityService);
	this.negativeResponseGenerator = Preconditions.checkNotNull(negativeResponseGenerator);
    }

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final HttpSession userSession,
	    final Set<String> fieldsToSign) {
	if (sregService.isSreg10(authenticationRequest)) {
	    logger.debug("simple registration extension 1.0 was detected");
	    Identity identity = identityService
		    .getByOpLocalIdentifier(authenticationRequest.getIdentity());
	    if (identity == null) {
		return negativeResponseGenerator
			.buildError("For sreg extension is identity required.");
	    }
	    if (!(identity instanceof IdentitySreg)) {
		return negativeResponseGenerator
			.buildError("For sreg is required extended identity.");
	    }
	    Set<String> keys = sregService.extractRequestedKeys(authenticationRequest);
	    sregService.fillSregResponse(keys, response, (IdentitySreg) identity, fieldsToSign);
	}
	return null;
    }

}
