package com.coroptis.coidi.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.services.AuthenticationProcessor;

/**
 * Abstract class helps to build chain of commands from
 * {@link AuthenticationProcessor} implementations. It could be used at RP
 * sides.
 * 
 * @author jan
 *
 */
public class AbstractAuthProc implements AuthenticationProcessor {

    protected final List<AuthenticationProcessor> dispatchers = new ArrayList<AuthenticationProcessor>();

    /**
     * Implementations just choose first {@link AuthenticationProcessor} which
     * process inputs.
     */
    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final HttpSession userSession,
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
