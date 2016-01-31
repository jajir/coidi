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

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.google.common.base.Preconditions;

public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private final static Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

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
	    if (map.put(key, httpServletRequest.getParameter(key)) == null) {
		// there wasn't already assigned value.
	    } else {
		throw new CoidiException("There is duplicated key '"
			+ httpServletRequest.getParameter(key) + "' in OpenID message.");
	    }
	    logger.debug("adding '" + key + "', '" + httpServletRequest.getParameter(key) + "'");
	}
	return map;
    }
}
