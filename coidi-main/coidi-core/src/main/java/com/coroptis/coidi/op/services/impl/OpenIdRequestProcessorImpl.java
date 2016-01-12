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

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.services.OpenIdRequestTool;
import com.coroptis.coidi.op.util.OpenId11;
import com.coroptis.coidi.op.util.OpenId20;

/**
 * Simple implementation of {@link OpenIdRequestProcessor}.
 * 
 * @author jirout
 * 
 */
public class OpenIdRequestProcessorImpl implements OpenIdRequestProcessor {

    @Inject
    @OpenId11
    private OpenIdDispatcher openIdDispatcher11;

    @Inject
    @OpenId20
    private OpenIdDispatcher openIdDispatcher20;

    private final boolean openidVersion11Enabled;

    @Inject
    private OpenIdRequestTool openIdRequestTool;

    @Inject
    public OpenIdRequestProcessorImpl(final OpConfigurationService configurationService) {
	this.openidVersion11Enabled = configurationService.isOpenId11Enabled();
    }

    @Override
    public AbstractMessage process(final Map<String, String> requestParams,
	    final UserSessionSkeleton userSession) {
	if (openidVersion11Enabled && openIdRequestTool.isOpenIdVersion1x(requestParams)) {
	    return openIdDispatcher11.process(requestParams, userSession);
	} else {
	    return openIdDispatcher20.process(requestParams, userSession);
	}
    }

}
