package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthReq;

public class AuthReqOAuthExtension implements AuthReq {

	@Inject
	@Symbol("common.realm")
	private String realm;

	@Override
	public boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult) {
		if (discoveryResult.getPreferedService().idPresent(
				XrdService.TYPE_ATTRIBUTE_EXCHANGE_2_0)) {
			authenticationRequest.put("openid.ns.ext2",
					"http://spec.openid.net/extensions/oauth/1.0");
			authenticationRequest.put("openid.ext2.consumer", realm);
			// TODO add list of requested services as parameter for next val
			authenticationRequest.put("openid.ext2.scope", "ss");

		}
		return false;
	}

}
