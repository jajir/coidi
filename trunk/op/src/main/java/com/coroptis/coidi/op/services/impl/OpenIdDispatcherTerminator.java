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
import java.util.Map.Entry;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

/**
 * When no previous dispatcher process message then this report that message is
 * invalid.
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherTerminator implements OpenIdDispatcher {

	@Inject
	private Logger logger;

	@Override
	public AbstractMessage process(Map<String, String> requestParams) {
		ErrorResponse errorResponse = new ErrorResponse(false);
		StringBuilder buff = new StringBuilder();
		buff.append("Unable to process incoming message, incorrect openid.mode '");
		buff.append(requestParams.get(OPENID_MODE));
		buff.append("'\n");
		for (Entry<String, String> entry : requestParams.entrySet()) {
			buff.append(entry.getKey());
			buff.append("=");
			buff.append(entry.getValue());
			buff.append("\n");
		}
		logger.warn(buff.toString());
		errorResponse
				.setError("Unable to process incoming message, incorrect openid.mode '"
						+ requestParams.get(OPENID_MODE) + "'\n");
		return errorResponse;
	}

}
