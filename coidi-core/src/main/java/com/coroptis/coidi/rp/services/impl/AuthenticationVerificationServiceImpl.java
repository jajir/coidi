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
package com.coroptis.coidi.rp.services.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.coroptis.coidi.rp.services.AuthenticationVerificationService;

public class AuthenticationVerificationServiceImpl implements AuthenticationVerificationService {

    private final static Logger logger = LoggerFactory.getLogger(AuthenticationVerificationServiceImpl.class);

    @Inject
    private AuthRespDecoder authRespDecoder;

    @Override
    public AuthenticationResult verify(final AuthenticationResponse authenticationResponse,
	    final Association association) {
	AuthenticationResult authenticationResult = new AuthenticationResult();
	if (authRespDecoder.decode(authenticationResponse, association, authenticationResult)) {
	    return authenticationResult;
	} else {
	    logger.debug("message error");
	    throw new CoidiException("There was some error in message");
	}
    }

	public void setAuthRespDecoder(AuthRespDecoder authRespDecoder) {
		this.authRespDecoder = authRespDecoder;
	}
    
}
