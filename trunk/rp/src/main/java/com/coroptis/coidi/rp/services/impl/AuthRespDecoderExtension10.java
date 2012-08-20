package com.coroptis.coidi.rp.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.rp.base.AuthRespExtension;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.coroptis.coidi.rp.services.AuthRespSupport;

/**
 * Decoder understand attribute exchange OpenID extension.
 * 
 * @author jan
 * 
 */
public class AuthRespDecoderExtension10 implements AuthRespDecoder {

	@Inject
	private AuthRespSupport authRespSupport;

	@Override
	public AuthRespExtension decode(
			final AuthenticationResponse authenticationResponse,
			final String key, final String nameSpace) {
		if (nameSpace.equals(OpenIdNs.TYPE_ATTRIBUTE_EXCHANGE_1_0)) {
			String prefix = authRespSupport.getNameSpacePrefix(key);
			return new AuthRespExtension(prefix, authenticationResponse);
		}
		return null;
	}

}
