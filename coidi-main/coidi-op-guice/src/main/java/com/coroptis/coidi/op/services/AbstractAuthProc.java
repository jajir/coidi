package com.coroptis.coidi.op.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;

@Singleton
public class AbstractAuthProc implements AuthenticationProcessor {

    protected final List<AuthenticationProcessor> dispatchers = new ArrayList<AuthenticationProcessor>();

    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final UserSessionSkeleton userSession,
	    final Set<String> fieldsToSign) {
	for (final AuthenticationProcessor builder : dispatchers) {
	    final AbstractMessage row = builder.process(authenticationRequest, response,
		    userSession, fieldsToSign);
	    if (row != null) {
		return row;
	    }
	}
	return null;
    }

}
