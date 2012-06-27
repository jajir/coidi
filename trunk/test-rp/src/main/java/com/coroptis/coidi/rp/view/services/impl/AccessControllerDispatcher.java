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
package com.coroptis.coidi.rp.view.services.impl;

import java.io.IOException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;

import com.coroptis.coidi.rp.view.util.AccessOnlyForSigned;
import com.coroptis.coidi.rp.view.util.AccessOnlyForUnsigned;
import com.coroptis.coidi.rp.view.util.UserSession;

/**
 * Dispatcher that handle access rights for site resources.
 * 
 * @author jan
 * 
 */
public class AccessControllerDispatcher implements Dispatcher {

	@Inject
	private Logger logger;

	private final static String LOGIN_PAGE = "/login";

	private ApplicationStateManager asm;
	private final ComponentClassResolver resolver;
	private final ComponentSource componentSource;

	public AccessControllerDispatcher(ApplicationStateManager asm,
			ComponentClassResolver resolver, ComponentSource componentSource) {
		this.asm = asm;
		this.resolver = resolver;
		this.componentSource = componentSource;
	}

	public boolean dispatch(Request request, Response response)
			throws IOException {
		String path = request.getPath();
		if (path.equals("")) {
			return false;
		}

		int nextslashx = path.length();
		String pageName;
		logger.debug("page path: " + request.getPath());

		while (true) {
			pageName = path.substring(1, nextslashx);
			if (!pageName.endsWith("/") && resolver.isPageName(pageName))
				break;
			nextslashx = path.lastIndexOf('/', nextslashx - 1);
			if (nextslashx <= 1)
				return false;
		}
		return checkAccess(pageName, request, response);
	}

	private boolean checkAccess(String pageName, Request request,
			Response response) throws IOException {
		Component page = componentSource.getPage(pageName);
		if (page.getClass().getAnnotation(AccessOnlyForSigned.class) != null) {
			if (asm.exists(UserSession.class)
					&& asm.get(UserSession.class).isLogged()) {
				return false;
			} else {
				response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
				return true;
			}
		}

		if (page.getClass().getAnnotation(AccessOnlyForUnsigned.class) != null) {
			if (asm.exists(UserSession.class)
					&& asm.get(UserSession.class).isLogged()) {
				response.sendRedirect(request.getContextPath());
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

}
