package com.coroptis.coidi.rp.services.impl;

import java.util.Map;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthReq;

/**
 * Useful for google federated login. Allows to show icon of login site.
 * 
 * @author jan
 * 
 */
public class AuthReqUiIcon implements AuthReq {

	@Override
	public boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult, Map<String, String> parameters) {
		if (discoveryResult.getPreferedService().idPresent(
				XrdService.TYPE_UI_ICON_1_0)) {
			authenticationRequest.put("openid.ns.ui",
					XrdService.TYPE_UI_ICON_1_0);
			authenticationRequest.put("openid.ui.mode", "x-has-session");
			authenticationRequest.put("openid.ui.icon", "true");
		}
		return false;
	}

}
