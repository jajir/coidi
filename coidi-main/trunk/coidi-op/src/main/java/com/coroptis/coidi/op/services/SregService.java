package com.coroptis.coidi.op.services;

import java.util.Set;

import com.coroptis.coidi.core.message.AuthenticationRequest;

/**
 * Service helps process OpenID simple registration extension.
 * 
 * @author jirout
 * 
 */
public interface SregService {

    /**
     * When SREG extension 1.1 is detected than keys are extracted and returned.
     * 
     * @param authenticationRequest
     *            required {@link AuthenticationRequest}
     * @return
     */
    Set<String> extractRequestedKeys(AuthenticationRequest authenticationRequest);

}
