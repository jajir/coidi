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
     *            required authentication response
     * @param association
     *            required association
     * @param authenticationResult
     *            required result authenticationResult
     * @return <code>true</code> when message is processed otherwise
     *         <code>false</code>
     */
    Boolean decode(AuthenticationResponse authenticationResponse, Association association,
	    AuthenticationResult authenticationResult);

}
