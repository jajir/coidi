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
package com.coroptis.coidi.op.view.pages;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.iocsupport.OpBinding;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.view.utils.TextResponse;
import com.coroptis.coidi.op.view.utils.UserSession;

/**
 * This end point will accept and process all OpenID (direct and indirect)
 * communication.
 * 
 * @author jan
 * 
 */
public class OpenId { // NO_UCD

    @Inject
    private Logger logger;

    @Inject
    private RequestGlobals request;

    @Inject
    private Response response;

    @Inject
    private OpBinding opBinding;

    @SessionState
    private UserSession userSession;

    public Object onActivate() {
	try {
	    HttpServletRequest httpRequest = request.getHTTPServletRequest();
	    Map<String, String> map = opBinding.getAuthenticationService()
		    .convertHttpRequestParametersToMap(request.getHTTPServletRequest());
	    logger.info("SSO openId request is " + httpRequest.getQueryString());
	    AbstractMessage requestResponse = opBinding.getOpenIdRequestProcessor().process(map,
		    httpRequest.getSession());
	    logger.debug("openId response: " + requestResponse.getMessage());
	    if (requestResponse instanceof ErrorResponse) {
		if (NegativeResponseGenerator.APPLICATION_ERROR_PLEASE_LOGIN.equals(
			requestResponse.get(NegativeResponseGenerator.APPLICATION_ERROR_KEY))) {
		    return Login.class;
		} else {
		    logger.info("SSO openId response is '" + requestResponse.getMessage() + "'");
		    return new TextResponse(requestResponse.getMessage());
		}
	    } else {
		if (requestResponse.isUrl()) {
		    String redirUrl = requestResponse.getUrl(null);
		    logger.info("SSO openId response is redirect to: '" + redirUrl + "'");
		    response.sendRedirect(redirUrl);
		    return new TextResponse("");
		} else {
		    logger.info("SSO openId response is '" + requestResponse.getMessage() + "'");
		    return new TextResponse(requestResponse.getMessage());
		}
	    }
	} catch (Exception e) {
	    /**
	     * It's dirty, but some exceptions can't be caught in different way.
	     */
	    logger.error(e.getMessage(), e);
	    return new TextResponse("occured error: " + e.getMessage());
	}
    }

}
