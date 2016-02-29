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

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.services.OpenIdRequestTool;
import com.google.common.base.Preconditions;

/**
 * Simple implementation of {@link OpenIdRequestProcessor}.
 * 
 * @author jirout
 * 
 */
@Singleton
public class OpenIdRequestProcessorImpl implements OpenIdRequestProcessor {

	private final OpenIdDispatcher openIdDispatcher11;

	private final OpenIdDispatcher openIdDispatcher20;

	private final boolean openidVersion11Enabled;

	private final OpenIdRequestTool openIdRequestTool;

	@Inject
	public OpenIdRequestProcessorImpl(final OpConfigurationService configurationService,
			final OpenIdDispatcher openIdDispatcher11, final OpenIdDispatcher openIdDispatcher20,
			final OpenIdRequestTool openIdRequestTool) {
		this.openidVersion11Enabled = configurationService.isOpenId11Enabled();
		this.openIdDispatcher11 = Preconditions.checkNotNull(openIdDispatcher11);
		this.openIdDispatcher20 = Preconditions.checkNotNull(openIdDispatcher20);
		this.openIdRequestTool = Preconditions.checkNotNull(openIdRequestTool);
	}

	@Override
	public AbstractMessage process(final Map<String, String> requestParams, final HttpSession userSession) {
		if (openidVersion11Enabled && openIdRequestTool.isOpenIdVersion1x(requestParams)) {
			return openIdDispatcher11.process(requestParams, userSession);
		} else {
			return openIdDispatcher20.process(requestParams, userSession);
		}
	}

}
