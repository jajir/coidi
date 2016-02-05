package com.coroptis.coidi.rp.services.impl;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.google.common.base.Strings;

/**
 * VErify that incoming authentication response is valid OpenID 2.0 message.
 * 
 * @author jirout
 * 
 */
public class AuthRespOpenId20Verify implements AuthRespDecoder {

	@Override
	public Boolean decode(final AuthenticationResponse authenticationResponse, final Association association,
			final AuthenticationResult authenticationResult) {
		if (Strings.isNullOrEmpty(authenticationResponse.getNameSpace())) {
			throw new CoidiException("OpenID namespace was not filled.");
		}
		if (!AbstractMessage.OPENID_NS_20.equals(authenticationResponse.getNameSpace())) {
			throw new CoidiException(
					"OpenID namespace contains invalid value '" + authenticationResponse.getNameSpace() + "'.");
		}
		if (authenticationResponse.getMap().size() < 3) {
			throw new CoidiException("AuthenticationResponse probably it's not OpenID message becauses there is just '"
					+ authenticationResponse.getMap().size() + "' key value pairs.");
		}
		return false;
	}

}
