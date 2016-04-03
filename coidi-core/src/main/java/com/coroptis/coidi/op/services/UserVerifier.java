package com.coroptis.coidi.op.services;

import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AuthenticationRequest;

public interface UserVerifier {

    /**
     * Verify that OP local identity exists, that identity exists and that user
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

    /**
     * When in identity comes value AuthenticationRequest.IDENTITY_SELECT than
     * user at OP should select his identity. In many cases there is simple
     * mapping between identity and user.
     * <p>
     * This method allows OP provider to ask user to selected identity.
     * </p>
     * <p>
     * Method should not ask end user. Because could be called in
     * mode=checkid_immediate.
     * </p>
     * 
     * @param session
     *            required HTTP session
     * @return selected user's OP local identifier
     */
    String getSelectedOpLocalIdentifier(HttpSession session);

}
