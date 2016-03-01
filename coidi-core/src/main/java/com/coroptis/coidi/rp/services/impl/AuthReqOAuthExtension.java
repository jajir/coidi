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

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.services.RpConfigurationService;

public class AuthReqOAuthExtension implements AuthReq {

    private final String realm;

     
    public AuthReqOAuthExtension(final RpConfigurationService configurationService) {
	this.realm = configurationService.getRealm();
    }

    @Override
    public boolean process(AuthenticationRequest authenticationRequest,
	    DiscoveryResult discoveryResult, Map<String, String> parameters) {
	if (discoveryResult.getPreferedService().idPresent(OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0)) {
	    authenticationRequest.put("openid.ns.ext2",
		    "http://spec.openid.net/extensions/oauth/1.0");
	    authenticationRequest.put("openid.ext2.consumer", realm);
	    // TODO add list of requested services as parameter for next val
	    authenticationRequest.put("openid.ext2.scope", "ss");

	}
	return false;
    }

}
