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
import com.coroptis.coidi.core.services.NonceTool;
import com.coroptis.coidi.op.services.AuthProc;
import com.google.common.base.Preconditions;

/**
 * Just create nonce and add it to signed fields.
 * 
 * @author jirout
 * 
 */
public class AuthProcNonce implements AuthProc {

	private final static Logger logger = LoggerFactory.getLogger(AuthProcNonce.class);

	private final NonceTool nonceTool;

	public AuthProcNonce(final NonceTool nonceTool) {
		this.nonceTool = Preconditions.checkNotNull(nonceTool);
	}

	@Override
	public AbstractMessage process(final AuthenticationRequest authenticationRequest,
			final AuthenticationResponse response, final HttpSession userSession, final Set<String> fieldsToSign) {
		logger.debug("processing nonce: " + authenticationRequest);
		response.setNonce(nonceTool.createNonce());
		fieldsToSign.add(AuthenticationResponse.NONCE);
		return null;
	}

}
