package com.coroptis.coidi.rp.services.impl;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.AuthRespDecoder;

/**
 * Just return true to finish message processing, when processing reached this
 * point message was processed correctly.
 * 
 * @author jirout
 * 
 */
public class AuthRespDecoderTerminator implements AuthRespDecoder {

    @Override
    public Boolean decode(AuthenticationResponse authenticationResponse, Association association,
	    AuthenticationResult authenticationResult) {
	return true;
    }

}
