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

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;

/**
 * This class in terminator of chain of command. When processing of message end
 * in this class it means that no command could process request.
 * <p>
 * Class works with both OpenID protocol versions.
 * </p>
 * 
 * @author jan
 * 
 */
public class OpenIdDispatcherTerminator implements OpenIdDispatcher {

    private final static Logger logger = LoggerFactory.getLogger(OpenIdDispatcherTerminator.class);

    private final String contact;

    @Inject
    public OpenIdDispatcherTerminator(final OpConfigurationService configurationService) {
	this.contact = configurationService.getErrorContact();
    }

    @Override
    public AbstractMessage process(Map<String, String> requestParams,
	    UserSessionSkeleton userSession) {
	ErrorResponse errorResponse = new ErrorResponse(false);
	StringBuilder buff = new StringBuilder();
	buff.append("Unable to process incoming message, incorrect 'openid.mode'");
	buff.append(" or missing parameters or missing OpenID extension support.");
	String errMsg = buff.toString();
	buff.append("\n");
	for (Entry<String, String> entry : requestParams.entrySet()) {
	    buff.append(entry.getKey());
	    buff.append("=");
	    buff.append(entry.getValue());
	    buff.append("\n");
	}
	logger.info(buff.toString());
	errorResponse.setError(errMsg);
	errorResponse.setContact(contact);
	return errorResponse;
    }

}
