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

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestTool;

/**
 * Implementation
 * 
 * @author jirout
 * 
 */
public class OpenIdRequestToolImpl implements OpenIdRequestTool {

    @Override
    public boolean isOpenIdVersion20(final Map<String, String> requestParams) {
	return requestParams.get(OpenIdDispatcher.OPENID_NS) != null && AbstractMessage.OPENID_NS_20
		.equals(requestParams.get(OpenIdDispatcher.OPENID_NS));
    }

    @Override
    public boolean isOpenIdVersion1x(final Map<String, String> requestParams) {
	return requestParams.get(OpenIdDispatcher.OPENID_NS) == null
		|| AbstractMessage.OPENID_NS_10
			.equals(requestParams.get(OpenIdDispatcher.OPENID_NS))
		|| AbstractMessage.OPENID_NS_11
			.equals(requestParams.get(OpenIdDispatcher.OPENID_NS));
    }

}
