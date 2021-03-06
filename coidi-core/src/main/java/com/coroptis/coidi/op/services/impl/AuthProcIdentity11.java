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
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Perform basic setting of authentication response for OpenID 1.1.
 * <p>
 * Also verify that correct identity was entered by user.
 * </p>
 * 
 * @author jirout
 * 
 */
public class AuthProcIdentity11 implements AuthProc {

    private final static Logger logger = LoggerFactory.getLogger(AuthProcIdentity11.class);

    private final NegativeResponseGenerator negativeResponseGenerator;

     
    public AuthProcIdentity11(final NegativeResponseGenerator negativeResponseGenerator) {
	this.negativeResponseGenerator = Preconditions.checkNotNull(negativeResponseGenerator);
    }

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final HttpSession userSession,
	    final Set<String> fieldsToSign) {
	logger.debug("creating athentication response for: " + authenticationRequest);
	response.setNameSpace(AbstractMessage.OPENID_NS_11);
	response.setReturnTo(authenticationRequest.getReturnTo());
	if (Strings.isNullOrEmpty(authenticationRequest.getIdentity())) {
	    return negativeResponseGenerator.buildErrorWithNs(AbstractMessage.OPENID_NS_11,
		    "Mandatory field 'openid.identity' is missing");
	}
	response.setIdentity(authenticationRequest.getIdentity());
	fieldsToSign.add(AuthenticationResponse.MODE);
	fieldsToSign.add(AuthenticationResponse.IDENTITY);
	fieldsToSign.add(AuthenticationResponse.RETURN_TO);
	return null;
    }

}
