package com.coroptis.coidi.core.util;

import java.util.ArrayList;
import java.util.List;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.AuthRespDecoder;

/**
 * Abstract class helps to build chain of commands from
 * {@link AuthenticationRequest} implementations. It could be used at RP sides.
 * 
 * @author jan
 *
 */
public class AbstractAuthRespDecoder implements AuthRespDecoder {

    protected final List<AuthRespDecoder> dispatchers = new ArrayList<AuthRespDecoder>();

    @Override
    public Boolean decode(final AuthenticationResponse authenticationResponse,
	    final Association association, final AuthenticationResult authenticationResult) {
	for (final AuthRespDecoder builder : dispatchers) {
	    final Boolean processed = builder.decode(authenticationResponse, association,
		    authenticationResult);
	    if (processed != null && processed) {
		return true;
	    }
	}
	return false;
    }

}
