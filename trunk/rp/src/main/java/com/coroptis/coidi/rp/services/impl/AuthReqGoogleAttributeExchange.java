package com.coroptis.coidi.rp.services.impl;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthReq;

public class AuthReqGoogleAttributeExchange implements AuthReq {

	@Override
	public boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult) {
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
