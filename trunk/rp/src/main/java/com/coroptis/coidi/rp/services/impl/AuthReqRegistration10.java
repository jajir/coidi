package com.coroptis.coidi.rp.services.impl;

import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthReq;

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

	public final static String REG_NEW_IDENTITY = "registration.itIsNewIdentity";

	@Inject
	private Logger logger;

	@Inject
	@Symbol("common.extension.registration.requiredFields")
	private String requiredFields;

	@Inject
	@Symbol("common.extension.registration.optionalFields")
	private String optionalFields;

	@Inject
	@Symbol("common.extension.registration.policyUrl")
	private String policyUrl;

	@Override
	public boolean process(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult, Map<String, String> parameters) {
		if (discoveryResult.getPreferedService().idPresent(
				XrdService.TYPE_SREG_1_0)
				&& Boolean.parseBoolean(parameters.get(REG_NEW_IDENTITY))) {
			logger.debug("Registration extension 1.0 will be applied");
			authenticationRequest.putIgnoreEmpty("openid.sreg.required",
					requiredFields);
			authenticationRequest.putIgnoreEmpty("openid.sreg.optional",
					optionalFields);
			authenticationRequest.putIgnoreEmpty("openid.sreg.policy_url",
					policyUrl);
		}
		return false;
	}

}
