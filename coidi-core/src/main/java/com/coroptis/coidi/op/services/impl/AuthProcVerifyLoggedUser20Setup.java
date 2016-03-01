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
import com.coroptis.coidi.op.services.AuthProc;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.UserVerifier;
import com.google.common.base.Preconditions;

/**
 * Verify that use is logged in. When is not logged than application error
 * returned. This error should be handled by application, this error should not
 * be passed to RP.
 * 
 * @author jirout
 * 
 */
public class AuthProcVerifyLoggedUser20Setup implements AuthProc {

    private final static Logger logger = LoggerFactory
	    .getLogger(AuthProcVerifyLoggedUser20Setup.class);

    private final NegativeResponseGenerator negativeResponseGenerator;

    private final UserVerifier userVerifier;

     
    public AuthProcVerifyLoggedUser20Setup(
	    final NegativeResponseGenerator negativeResponseGenerator,
	    final UserVerifier userVerifier) {
	this.negativeResponseGenerator = Preconditions.checkNotNull(negativeResponseGenerator);
	this.userVerifier = Preconditions.checkNotNull(userVerifier);
    }

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final HttpSession session,
	    final Set<String> fieldsToSign) {
	logger.debug("verify identity: " + authenticationRequest);
	Preconditions.checkNotNull(session, "UserSession is null");
	if (!userVerifier.isUserLogged(session)) {
	    logger.debug("User is not logged in.");
	    userVerifier.storeAuthenticatonRequest(session, authenticationRequest);
	    return negativeResponseGenerator.applicationError("User is not logged in",
		    NegativeResponseGenerator.APPLICATION_ERROR_PLEASE_LOGIN);
	}
	return null;
    }

}
