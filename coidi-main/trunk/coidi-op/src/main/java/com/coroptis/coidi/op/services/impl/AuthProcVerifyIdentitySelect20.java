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

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;

/**
 * In case of 'identity select' authentication request verify that end user
 * select requested identity. If user didn't select application error is
 * returned.
 * 
 * @author jirout
 * 
 */
public class AuthProcVerifyIdentitySelect20 implements AuthenticationProcessor {

    @Inject
    private Logger logger;

    @Inject
    private NegativeResponseGenerator negativeResponseGenerator;

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final UserSessionSkeleton userSession,
	    final Set<String> fieldsToSign) {
	logger.debug("verify parameters: " + authenticationRequest);

	if (AuthenticationRequest.IDENTITY_SELECT.equals(authenticationRequest.getIdentity())) {
	    if (StringUtils.isEmpty(authenticationRequest.getSelectedIdentity())) {
		return negativeResponseGenerator.applicationError("requested identity is '"
			+ AuthenticationRequest.IDENTITY_SELECT
			+ "' but user didn't put selected identity in property '"
			+ AuthenticationRequest.USERS_SELECTED_IDENTITY + "'",
			NegativeResponseGenerator.APPLICATION_ERROR_SELECT_IDENTITY);
	    } else {
		authenticationRequest.setIdentity(authenticationRequest.getSelectedIdentity());
		authenticationRequest.setClaimedId(authenticationRequest.getSelectedIdentity());
	    }
	}
	return null;
    }

}
