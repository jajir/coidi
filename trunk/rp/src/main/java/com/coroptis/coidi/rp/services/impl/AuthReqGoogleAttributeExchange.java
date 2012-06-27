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

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthReq;

public class AuthReqGoogleAttributeExchange implements AuthReq {

	@Override
	public boolean process(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult, Map<String, String> parameters) {
		if (discoveryResult.getPreferedService().idPresent(
				XrdService.TYPE_ATTRIBUTE_EXCHANGE_2_0)) {
			authenticationRequest.put("openid.ns.ax",
					XrdService.TYPE_ATTRIBUTE_EXCHANGE_2_0);
			authenticationRequest.put("openid.ax.mode", "fetch_request");
			/**
			 * possible required values are: country, email, firstname,
			 * language, lastname
			 */
			authenticationRequest.put("openid.ax.required", "email,lastname");
			authenticationRequest.put("openid.ax.country",
					"http://axschema.org/contact/country/home");
			authenticationRequest.put("openid.ax.email",
					"http://axschema.org/contact/email");
			authenticationRequest.put("openid.ax.firstname",
					"http://axschema.org/namePerson/first");
			authenticationRequest.put("openid.ax.language",
					"http://axschema.org/pref/language");
			authenticationRequest.put("openid.ax.lastname",
					"http://axschema.org/namePerson/last");
		}
		return false;
	}

}
