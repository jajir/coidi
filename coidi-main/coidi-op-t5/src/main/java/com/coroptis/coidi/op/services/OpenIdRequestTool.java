package com.coroptis.coidi.op.services;

import java.util.Map;

import com.coroptis.coidi.op.base.UserSessionSkeleton;

/**
 * Help with OpenID request.
 * 
 * @author jirout
 * 
 */
public interface OpenIdRequestTool {

    /**
     * Allows to identity if OpenID request is protocol version 2.0.
     * 
     * @param requestParams
     *            required OpenID request
     * @return return true when it's OpenID protocol version 2.0 otherwise
     *         return false
     */
    boolean isOpenIdVersion20(Map<String, String> requestParams);

    /**
     * Identify if OpenID request is protocol version 1.0 or 1.1.
     * 
     * @param requestParams
     *            required OpenID request
     * @return return true when it's OpenID protocol version 1.0 or 1.1
     *         otherwise return false
     */
    boolean isOpenIdVersion1x(Map<String, String> requestParams);

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
    boolean verify(String opLocalIdentity, UserSessionSkeleton session);
}
