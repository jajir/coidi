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

public class AuthReqGoogleAttributeExchange implements AuthReq {

	@Override
	public boolean process(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult, Map<String, String> parameters) {
		if (discoveryResult.getPreferedService().idPresent(
				OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0)) {
			authenticationRequest.put("ns.ax",
					OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0);
			authenticationRequest.put("ax.mode", "fetch_request");
			/**
			 * possible required values are: country, email, firstname,
			 * language, lastname
			 */
			if (Boolean.parseBoolean(parameters.get(REG_NEW_IDENTITY))) {
				/**
				 * just email will be asked for, it allows to match identities
				 * by email
				 */
				authenticationRequest.put("ax.required", "email,firstname");
				authenticationRequest.put("ax.type.email",
						"http://axschema.org/contact/email");
				authenticationRequest.put("ax.type.firstname",
						"http://axschema.org/namePerson/first");
			}
			/**
			 * authenticationRequest.put("ax.required", "email,lastname");
			 * authenticationRequest.put("ax.type.country",
			 * "http://axschema.org/contact/country/home");
			 * authenticationRequest.put("ax.type.email",
			 * "http://axschema.org/contact/email");
			 * authenticationRequest.put("ax.type.firstname",
			 * "http://axschema.org/namePerson/first");
			 * authenticationRequest.put("ax.type.language",
			 * "http://axschema.org/pref/language");
			 * authenticationRequest.put("ax.type.lastname",
			 * "http://axschema.org/namePerson/last");
			 */
			/**
			 * Following values are qequired by google.
			 */
			authenticationRequest
					.setIdentity("http://specs.openid.net/auth/2.0/identifier_select");
			authenticationRequest
					.setClaimedId("http://specs.openid.net/auth/2.0/identifier_select");
		}
		return false;
	}

}
