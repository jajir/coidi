package com.coroptis.coidi.op.services;

import com.coroptis.coidi.op.base.UserSessionSkeleton;

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
    boolean verify(String opLocalIdentity, UserSessionSkeleton session);
}
