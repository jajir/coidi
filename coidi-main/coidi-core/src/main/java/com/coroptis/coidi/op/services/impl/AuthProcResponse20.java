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
import com.coroptis.coidi.op.services.OpConfigurationService;

/**
 * Verify that identity and claimed identities are correctly entered. Initialize
 * authentication response.
 * 
 * @author jirout
 * 
 */
public class AuthProcResponse20 implements AuthenticationProcessor {

    private final static Logger logger = LoggerFactory.getLogger(AuthProcResponse20.class);

    private final String opServer;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Inject
    public AuthProcResponse20(final OpConfigurationService configurationService) {
	this.opServer = configurationService.getOpServerUrl();
    }

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final UserSessionSkeleton userSession,
	    final Set<String> fieldsToSign) {
	logger.debug("creating athentication response for: " + authenticationRequest);
	if (StringUtils.isEmpty(authenticationRequest.getIdentity())) {
	    if (StringUtils.isEmpty(authenticationRequest.getClaimedId())) {
		/**
		 * Both are empty. It could be some OpenID extension request.
		 */
	    } else {
		return negativeResponseGenerator.simpleError("field '"
			+ AuthenticationResponse.CLAIMED_ID + "' is filled and field '"
			+ AuthenticationResponse.IDENTITY + "' is empty, this is forbiden state.");
	    }
	} else {
	    if (StringUtils.isEmpty(authenticationRequest.getClaimedId())) {
		return negativeResponseGenerator.simpleError("field '"
			+ AuthenticationResponse.CLAIMED_ID + "' is empty and field '"
			+ AuthenticationResponse.IDENTITY + "' is filled, this is forbiden state.");
	    } else {
		/**
		 * Both are filled
		 */
		response.setIdentity(authenticationRequest.getIdentity());
		response.setClaimedId(authenticationRequest.getClaimedId());
		fieldsToSign.add(AuthenticationResponse.CLAIMED_ID);
		fieldsToSign.add(AuthenticationResponse.IDENTITY);
	    }
	}
	response.setReturnTo(authenticationRequest.getReturnTo());
	response.setOpEndpoint(opServer + "openid");
	fieldsToSign.add(AuthenticationResponse.RETURN_TO);
	fieldsToSign.add(AuthenticationResponse.OP_ENDPOINT);
	return null;
    }

}
