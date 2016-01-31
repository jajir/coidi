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

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationParameters;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.services.RpConfigurationService;
import com.coroptis.coidi.rp.services.RpService;

public class RpServiceImpl implements RpService {

    private final static Logger logger = LoggerFactory.getLogger(RpServiceImpl.class);

    @Inject
    private AuthReq authReq;

    private final String realm;

    @Inject
    public RpServiceImpl(final RpConfigurationService configurationService) {
	this.realm = configurationService.getRealm();
    }

    @Override
    public String authentication(final DiscoveryResult discoveryResult,
	    final Association association,
	    final AuthenticationParameters authenticationParameters) {
	AuthenticationRequest authenticationRequest = new AuthenticationRequest();
	if (association == null) {
	    // no association handle --> stateless mode
	} else {
	    authenticationRequest.setAssocHandle(association.getAssocHandle());
	}

	// TODO refactor it
	authenticationRequest.setIdentity(discoveryResult.getClaimedId());
	authenticationRequest.setClaimedId(discoveryResult.getClaimedId());
	authenticationRequest.setMode(authenticationParameters.getMode());
	authenticationRequest.setRealm(realm);
	authenticationRequest.setReturnTo(authenticationParameters.getReturnTo());
	authReq.process(authenticationRequest, discoveryResult,
		authenticationParameters.getParameters());
	logger.debug("authentication: " + authenticationRequest.getMessage());
	return authenticationRequest.getUrl(discoveryResult.getEndPoint());
    }

}
