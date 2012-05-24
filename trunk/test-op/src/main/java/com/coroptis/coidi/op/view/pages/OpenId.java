package com.coroptis.coidi.op.view.pages;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.op.view.services.OpenIdDispatcher;
import com.coroptis.coidi.op.view.utils.TextResponse;

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

	public StreamResponse onActivate() {
		try {
			HttpServletRequest httpRequest = request.getHTTPServletRequest();
			Map<String, String> map = new HashMap<String, String>();
			for (String key : request.getRequest().getParameterNames()) {
				map.put(key, request.getRequest().getParameter(key));
				logger.debug("adding " + key + ", "
						+ request.getRequest().getParameter(key));
			}
			logger.info("SSO openId request is " + httpRequest.getQueryString());
			AbstractOpenIdResponse requestResponse = openIdRequestDispatcher
					.process(map);
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
