package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;

/**
 * Object hold information about one extension. Each OpenID extension have one
 * implementation. When implementation detect and decode concrete extension than
 * resolved object is returned.
 * 
 * @author jan
 * 
 */
public interface AuthRespDecoder {

    /**
     * 
     * @param authenticationResponse
     * @return
     */
    Boolean decode(AuthenticationResponse authenticationResponse, Association association,
	    AuthenticationResult authenticationResult);

}
