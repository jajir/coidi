package com.coroptis.coidi.op.services;

import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AuthenticationRequest;

public interface UserVerifier {

    /**
     * verify that OP local identity exists, that identity exists and that user
     * in user session own given identity.
     * 
     * @param opLocalIdentity
     *            optional OP local identifier
     * @param session
     *            required user's session
     * @return <code>true</code> when user own given OP local identity otherwise
     *         return false <code>true</code>
     */
    boolean verify(String opLocalIdentity, HttpSession session);

    /**
     * Check if users is logged into HTTP session.
     * 
     * @param session
     *            required HTTP session
     * @return return <code>true</code> when user is logged in otherwise return
     *         <code>false</code>
     */
    boolean isUserLogged(HttpSession session);

    /**
     * Store authentication request into HTTP session for further use.
     * 
     * @param session
     *            required HTTP session
     * @param authenticationRequest
     *            required authentication request to store
     */
    void storeAuthenticatonRequest(HttpSession session,
	    AuthenticationRequest authenticationRequest);

}
