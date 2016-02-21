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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.services.AuthProc;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.UserVerifier;

/**
 * Put correct identity id and claimed identity to response. In case of
 * 'identity select' authentication request verify that end user select
 * requested identity. If user didn't select application error is returned.
 * 
 * @author jirout
 * 
 */
public class AuthProcVerifyIdentitySelect20 implements AuthProc {

	private final static Logger logger = LoggerFactory.getLogger(AuthProcVerifyIdentitySelect20.class);

	@Inject
	private NegativeResponseGenerator negativeResponseGenerator;

	@Inject
	private UserVerifier userVerifier;

	@Override
	public AbstractMessage process(final AuthenticationRequest authenticationRequest,
			final AuthenticationResponse response, final HttpSession session, final Set<String> fieldsToSign) {
		logger.debug("verify parameters: " + authenticationRequest);

		if (AuthenticationRequest.IDENTITY_SELECT.equals(authenticationRequest.getIdentity())) {
			if (StringUtils.isEmpty(userVerifier.getSelectedIdenity(session))) {
				return negativeResponseGenerator.applicationError(
						"requested identity is '" + AuthenticationRequest.IDENTITY_SELECT
								+ "' but user didn't selected identity",
						NegativeResponseGenerator.APPLICATION_ERROR_SELECT_IDENTITY);
			} else {
				response.setIdentity(userVerifier.getSelectedIdenity(session));
				response.setClaimedId(userVerifier.getSelectedIdenity(session));
			}
		} else {
			response.setIdentity(authenticationRequest.getIdentity());
			response.setClaimedId(authenticationRequest.getClaimedId());
		}
		return null;
	}

}
