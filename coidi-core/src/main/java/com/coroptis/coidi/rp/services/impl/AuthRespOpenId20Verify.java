package com.coroptis.coidi.rp.services.impl;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.google.common.base.Strings;

/**
 * When OpenID response missing name space than it's OpendID 1.1 or 1.0 or it's
 * not OpenID message at all.
 * 
 * @author jirout
 * 
 */
public class AuthRespOpenId20Verify implements AuthRespDecoder {

    @Override
    public Boolean decode(final AuthenticationResponse authenticationResponse,
	    final Association association, final AuthenticationResult authenticationResult) {
	if (Strings.isNullOrEmpty(authenticationResponse.getNameSpace())) {
	    throw new CoidiException("OpenID namespace was not filled.");
	}
	if (!AbstractMessage.OPENID_NS.equals(authenticationResponse.getNameSpace())) {
	    throw new CoidiException("OpenID namespace contains invalid value '"
		    + authenticationResponse.getNameSpace() + "'.");
	}
	if (authenticationResponse.getMap().size() < 3) {
	    throw new CoidiException(
		    "Probably it's not OpenID message there is only one key value pair.");
	}
	return null;
    }

}
