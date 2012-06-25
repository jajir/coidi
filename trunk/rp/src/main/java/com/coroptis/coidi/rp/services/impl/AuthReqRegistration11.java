package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthReq;

/**
 * Add registration request 1.1 to authentication request. Process is added based on
 * requirement specified in discovery.
 * 
 * @author jan
 * 
 */
public class AuthReqRegistration11 implements AuthReq {

	@Inject
	private Logger logger;

	@Inject
	@Symbol("common.extension.registration.requiredFields")
	private String requiredFields;

	@Symbol("common.extension.registration.optionalFields")
	private String optionalFields;

	@Symbol("common.extension.registration.policyUrl")
	private String policyUrl;

	@Override
	public boolean applyExtension(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult) {
		if (discoveryResult.getPreferedService().idPresent(
				XrdService.TYPE_SREG_1_1)) {
			logger.debug("Registration extension 1.1 will be applied");
			authenticationRequest.put("openid.ns.sreg",
					XrdService.TYPE_SREG_1_1);
			authenticationRequest.putIgnoreEmpty("openid.sreg.required",
					requiredFields);
			authenticationRequest
					.putIgnoreEmpty("openid.sreg.", optionalFields);
			authenticationRequest.putIgnoreEmpty("openid.sreg.", policyUrl);
		}
		return false;
	}

}
