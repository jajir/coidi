package com.coroptis.coidi.op.services;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.base.UserSessionSkeleton;

public interface AuthenticationSetupProcessor {

    AbstractMessage process(AuthenticationRequest authenticationRequest,
	    UserSessionSkeleton userSession);

}
