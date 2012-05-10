package com.coroptis.coidi.op.view.pages;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.Response;

import com.coroptis.coidi.op.view.services.AbstractOpenIdResponse;
import com.coroptis.coidi.op.view.services.MessageService;
import com.coroptis.coidi.op.view.services.OpenIdDispatcher;
import com.coroptis.coidi.op.view.utils.TextResponse;
import com.google.common.io.CharStreams;

/**
 * This end point will accept and process all OpenID (direct and indirect)
 * communication.
 * 
 * @author jan
 * 
 */
public class OpenId {

	private final static Logger logger = Logger.getLogger(OpenId.class);

	@Inject
	private RequestGlobals request;

	@Inject
	private Response response;

	@Inject
	private OpenIdDispatcher openIdRequestDispatcher;

	@Inject
	private MessageService messageService;

	public StreamResponse onActivate() {
		try {
			HttpServletRequest httpRequest = request.getHTTPServletRequest();
			Map<String, String> map = new HashMap<String, String>();
			for (String key : request.getRequest().getParameterNames()) {
				map.put(key, request.getRequest().getParameter(key));
			}
			logger.info("SSO openId request is " + httpRequest.getQueryString());
			// Map<String, String> map = messageService
			// .convertUrlToMap(httpRequest.getQueryString());
			AbstractOpenIdResponse requestResponse = openIdRequestDispatcher
					.process(map);
			if (requestResponse.getRedirectToUrl() == null) {
				logger.info("SSO openId response is '"
						+ requestResponse.getMessage() + "'");
				return new TextResponse(requestResponse.getMessage());
			} else {
				String redirUrl = null;
				if (requestResponse.getRedirectToUrl().contains("?")) {
					redirUrl = requestResponse.getRedirectToUrl() + "&"
							+ requestResponse.getMessage();
				} else {
					redirUrl = requestResponse.getRedirectToUrl() + "?"
							+ requestResponse.getMessage();
				}
				logger.info("SSO openId response is redirect to: '" + redirUrl
						+ "'");
				response.sendRedirect(redirUrl);
				return new TextResponse("");
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
