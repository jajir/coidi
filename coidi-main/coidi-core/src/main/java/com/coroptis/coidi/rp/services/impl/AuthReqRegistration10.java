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

import javax.inject.Inject;

import org.slf4j.Logger;

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.services.RpConfigurationService;

/**
 * Add registration request 1.0 to authentication request. Process is added
 * based on requirement specified in discovery.
 * <p>
 * Registration is applied just when discovered OP support it and user set
 * {@value #REG_NEW_IDENTITY} parameter to true.
 * </p>
 * 
 * @author jan
 * 
 */
public class AuthReqRegistration10 implements AuthReq {

    @Inject
    private Logger logger;

    private final String requiredFields;

    private final String optionalFields;

    private final String policyUrl;

    @Inject
    public AuthReqRegistration10(final RpConfigurationService configurationService) {
	this.policyUrl = configurationService.getRegistrationPolicyUrl();
	this.requiredFields = configurationService.getRegistrationRequiredFields();
	this.optionalFields = configurationService.getRegistrationOptionalFields();
    }

    @Override
    public boolean process(AuthenticationRequest authenticationRequest,
	    DiscoveryResult discoveryResult, Map<String, String> parameters) {
	if (discoveryResult.getPreferedService().idPresent(OpenIdNs.TYPE_SREG_1_0)
		&& Boolean.parseBoolean(parameters.get(REG_NEW_IDENTITY))) {
	    logger.debug("Registration extension 1.0 will be applied");
	    authenticationRequest.putIgnoreEmpty("sreg.required", requiredFields);
	    authenticationRequest.putIgnoreEmpty("sreg.optional", optionalFields);
	    authenticationRequest.putIgnoreEmpty("sreg.policy_url", policyUrl);
	}
	return false;
    }

}
