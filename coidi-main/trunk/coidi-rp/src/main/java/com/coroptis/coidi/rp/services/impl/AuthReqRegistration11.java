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

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;

/**
 * Add registration request 1.1 to authentication request. Process is added
 * based on requirement specified in discovery.
 * <p>
 * Simple registration extension is applied just when is supported by OP and in
 * parameters under key 'sreg.ns' is value OpenIdNs.TYPE_SREG_1_1. Required
 * fields are in comma separated list under the key 'sreg.required'. Optional
 * fields are in comma separated list under the key 'sreg.optional'.
 * </p>
 * 
 * @author jan
 * 
 */
public class AuthReqRegistration11 implements AuthReq {

    public final static String SREG_REQUIRED = "sreg.required";

    public final static String SREG_OPTIONAL = "sreg.optional";

    public final static String SREG_POLICY_URL = "sreg.policy_url";

    @Inject
    private Logger logger;

    @Inject
    @Symbol("common.extension.registration.policyUrl")
    private String policyUrl;

    @Override
    public boolean process(AuthenticationRequest authenticationRequest,
	    DiscoveryResult discoveryResult, Map<String, String> parameters) {
	if (discoveryResult.getPreferedService().idPresent(OpenIdNs.TYPE_SREG_1_1)
		&& OpenIdNs.TYPE_SREG_1_1.equals(parameters.get("sreg.ns"))) {
	    logger.debug("Registration extension 1.1 will be applied");
	    authenticationRequest.put("ns.sreg", OpenIdNs.TYPE_SREG_1_1);
	    authenticationRequest.putIgnoreEmpty(SREG_REQUIRED, parameters.get(SREG_REQUIRED));
	    authenticationRequest.putIgnoreEmpty(SREG_OPTIONAL, parameters.get(SREG_OPTIONAL));
	    authenticationRequest.putIgnoreEmpty(SREG_POLICY_URL, policyUrl);
	}
	return false;
    }

}
