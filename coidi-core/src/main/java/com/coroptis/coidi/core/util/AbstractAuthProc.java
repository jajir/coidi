package com.coroptis.coidi.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.services.AuthProc;

/**
 * Abstract class helps to build chain of commands from
 * {@link AuthProc} implementations. It could be used at RP
 * sides.
 * 
 * @author jan
 *
 */
public class AbstractAuthProc implements AuthProc {

    protected final List<AuthProc> dispatchers = new ArrayList<AuthProc>();

    /**
     * Implementations just choose first {@link AuthProc} which
     * process inputs.
     */
    @Override
    public AbstractMessage process(final AuthenticationRequest authenticationRequest,
	    final AuthenticationResponse response, final HttpSession userSession,
	    final Set<String> fieldsToSign) {
	for (final AuthProc builder : dispatchers) {
	    final AbstractMessage row = builder.process(authenticationRequest, response,
		    userSession, fieldsToSign);
	    if (row != null) {
		return row;
	    }
	}
	return null;
    }

}
