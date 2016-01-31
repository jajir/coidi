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
package com.coroptis.coidi.rp.services.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.services.AuthenticationProcessException;

public class AuthReqPreconditions implements AuthReq {

    private final static Logger logger = LoggerFactory.getLogger(AuthReqPreconditions.class);

    @Override
    public boolean process(AuthenticationRequest authenticationRequest,
	    DiscoveryResult discoveryResult, Map<String, String> parameters) {
	if (discoveryResult.getPreferedService() == null) {
	    logger.info("Discovery process failed, found XRDS document is not valid.");
	    throw new AuthenticationProcessException(
		    "Discovery process failed, found XRDS document is not valid.");
	}
	/**
	 * Look for OP identifier element
	 */
	if (discoveryResult.getPreferedService().idPresent(OpenIdNs.TYPE_OPENID_2_0)) {
	    return false;
	} else {
	    /**
	     * Look for Claimed identifier element
	     */
	    if (discoveryResult.getPreferedService().idPresent(
		    OpenIdNs.TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0)) {
		return false;
	    } else {
		logger.info("Discovery process failed, found XRDS document doens't contains"
			+ " neither OP identifier element nor Claimend identifier element.");
		throw new AuthenticationProcessException(
			"Discovery process failed, found XRDS document is not valid.");
	    }
	}
    }
}
