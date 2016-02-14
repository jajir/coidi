package com.coroptis.coidi.op.services;

import java.util.Map;

/**
 * Help with OpenID request.
 * 
 * @author jirout
 * 
 */
public interface OpenIdRequestTool {

    /**
     * Identify if OpenID request is protocol version 1.0 or 1.1.
     * 
     * @param requestParams
     *            required OpenID request
     * @return return true when it's OpenID protocol version 1.0 or 1.1
     *         otherwise return false
     */
    boolean isOpenIdVersion1x(Map<String, String> requestParams);

}
