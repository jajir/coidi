package com.coroptis.coidi.op.view.services.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentEventLinkEncoder;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import com.coroptis.coidi.op.view.utils.SignedUserRequired;
import com.coroptis.coidi.op.view.utils.UnSignedUserRequired;
import com.coroptis.coidi.op.view.utils.UserSession;

public class AccessController implements Dispatcher {

	private final static Logger logger = Logger
			.getLogger(AccessController.class);

	@Inject
	private ComponentEventLinkEncoder linkEncoder;

	private final ApplicationStateManager asm;

	private final ComponentSource componentSource;

	public AccessController(final ApplicationStateManager asm,
			final ComponentSource componentSource) {
		this.asm = asm;
		this.componentSource = componentSource;
	}

	@Override
	public boolean dispatch(Request request, Response response)
			throws IOException {
		boolean result = checkAccess(getPageName(request), request, response);
		return result;
	}

	public boolean checkAccess(String pageName, Request request,
			Response response) throws IOException {

		Component page = componentSource.getPage(pageName);

		if (page.getClass().getAnnotation(SignedUserRequired.class) != null) {
			logger.debug("page is for 'SignedUserRequired' " + pageName);
			if (asm.exists(UserSession.class)) {
				UserSession userSession = asm.get(UserSession.class);
				if (!userSession.isUserLogged()) {
					response.sendRedirect("/index");
					return true;
				}
			} else {
				response.sendRedirect("/index");
				return true;
			}
		}

		if (page.getClass().getAnnotation(UnSignedUserRequired.class) != null) {
			logger.debug("page is for 'UnSignedUserRequired' " + pageName);
			if (asm.exists(UserSession.class)) {
				UserSession userSession = asm.get(UserSession.class);
				if (userSession.isUserLogged()) {
					response.sendRedirect("/user/+"
							+ userSession.getUser().getName());
					return true;
				}
			}
		}

		return false;
	}

	private String getPageName(Request request) {
		PageRenderRequestParameters pageParams = linkEncoder
				.decodePageRenderRequest(request);

		String pageName = "index";

		if (pageParams != null) {
			pageName = pageParams.getLogicalPageName();
			logger.debug("Resolved page name: " + pageName);
		} else {
			ComponentEventRequestParameters componentParams = linkEncoder
					.decodeComponentEventRequest(request);

			if (componentParams != null) {
				pageName = componentParams.getContainingPageName();
				logger.debug("Resolved page name: " + pageName);
			}
		}

		return pageName;
	}

}
