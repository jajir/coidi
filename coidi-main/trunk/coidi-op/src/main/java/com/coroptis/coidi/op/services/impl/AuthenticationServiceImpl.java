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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.google.common.base.Preconditions;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger logger;

    private final AssociationType statelesModeAssociationType;

    public AuthenticationServiceImpl(
	    // NO_UCD
	    @Inject @Symbol("op.stateless.mode.association.type") final String assocTypeStr,
	    final Logger logger) {
	statelesModeAssociationType = AssociationType.convert(assocTypeStr);
	this.logger = logger;
	logger.debug("stateless mode association type: " + statelesModeAssociationType);
    }

    @Override
    public boolean isAuthenticationRequest(final AuthenticationRequest authenticationRequest) {
	Preconditions.checkNotNull(authenticationRequest, "authenticationRequest");
	if (AuthenticationRequest.MODE_CHECKID_SETUP.equals(authenticationRequest.getMode())
		|| AuthenticationRequest.MODE_CHECKID_IMMEDIATE.equals(authenticationRequest
			.getMode())) {
	    return authenticationRequest.getIdentity() != null
		    && authenticationRequest.getClaimedId() != null;
	}
	return false;
    }

    @Override
    public String getNameSpace(AuthenticationRequest authenticationRequest, String nameSpaceUrl) {
	Preconditions.checkNotNull(nameSpaceUrl, "nameSpaceUrl");
	for (Entry<String, String> entry : authenticationRequest.getMap().entrySet()) {
	    if (nameSpaceUrl.equals(entry.getValue())) {
		// key is in format 'openid.ns.name', 'openid.ns.' should be
		// extracted
		return entry.getKey().substring("openid.ns.".length());
	    }
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> convertHttpRequestParametersToMap(
	    HttpServletRequest httpServletRequest) {
	Map<String, String> map = new HashMap<String, String>();
	Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
	while (parameterNames.hasMoreElements()) {
	    String key = parameterNames.nextElement();
	    map.put(key, httpServletRequest.getParameter(key));
	    logger.debug("adding '" + key + "', '" + httpServletRequest.getParameter(key) + "'");
	}
	return map;
    }
}
