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

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationService;

/**
 * Attribute exchange extension version 1.0.
 * @author jirout
 *
 */
public class AuthenticationProcessorAx10 implements AuthenticationProcessor {

	@Inject
	private Logger logger;

	@Inject
	private AuthenticationService authenticationService;

	@Override
	public AbstractMessage process(AuthenticationRequest authenticationRequest,
			AuthenticationResponse response, Identity identity,
			Set<String> fieldsToSign) {
		String nameSpaceName = authenticationService.getNameSpace(
				authenticationRequest, OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0);
		if (nameSpaceName != null) {
			logger.debug("name space '" + nameSpaceName + "' for "
					+ OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0);
		
		}
		return null;
	}

}
