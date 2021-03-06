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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.Session;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
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
	private OpenIdDispatcher openIdRequestDispatcher;
	
	@Inject
	private Session session;

	@SessionState
	private UserSession userSession;

	public StreamResponse onActivate() {
		try {
			HttpServletRequest httpRequest = request.getHTTPServletRequest();
			Map<String, String> map = new HashMap<String, String>();
			for (String key : request.getRequest().getParameterNames()) {
				map.put(key, request.getRequest().getParameter(key));
				logger.debug("adding '" + key + "', '"
						+ request.getRequest().getParameter(key) + "'");
			}
			logger.info("SSO openId request is " + httpRequest.getQueryString());
			AbstractMessage requestResponse = openIdRequestDispatcher.process(
					map, session);
			logger.debug("openId response: " + requestResponse.getMessage());
			if (requestResponse.isUrl()) {
				String redirUrl = requestResponse.getMessage();
				logger.info("SSO openId response is redirect to: '" + redirUrl
						+ "'");
				response.sendRedirect(redirUrl);
				return new TextResponse("");
			} else {
				logger.info("SSO openId response is '"
						+ requestResponse.getMessage() + "'");
				return new TextResponse(requestResponse.getMessage());
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
