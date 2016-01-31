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

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;

/**
 * Perform basic setting of authentication response for OpenID 1.1.
 * <p>
 * Also verify that correct identity was entered by user.
 * </p>
 * 
 * @author jirout
 * 
 */
public class AuthProcResponse11 implements AuthenticationProcessor {

    private final static Logger logger = LoggerFactory.getLogger(AuthProcResponse11.class);

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final UserSessionSkeleton userSession,
	    final Set<String> fieldsToSign) {
	logger.debug("creating athentication response for: " + authenticationRequest);
	response.setNameSpace(AbstractMessage.OPENID_NS_11);
	response.setReturnTo(authenticationRequest.getReturnTo());
	if (StringUtils.isEmpty(authenticationRequest.getIdentity())) {
	    return negativeResponseGenerator.simpleError(
		    "Mandatory field 'openid.identity' is missing", AbstractMessage.OPENID_NS_11);
	}
	response.setIdentity(authenticationRequest.getIdentity());
	fieldsToSign.add(AuthenticationResponse.MODE);
	fieldsToSign.add(AuthenticationResponse.IDENTITY);
	fieldsToSign.add(AuthenticationResponse.RETURN_TO);
	return null;
    }

}
