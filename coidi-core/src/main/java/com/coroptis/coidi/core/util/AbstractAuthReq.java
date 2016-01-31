package com.coroptis.coidi.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;

/**
 * Abstract class helps to build chain of commands from
 * {@link AuthenticationRequest} implementations. It could be used at RP sides.
 * 
 * @author jan
 *
 */
public class AbstractAuthReq implements AuthReq {

    protected final List<AuthReq> dispatchers = new ArrayList<AuthReq>();

    @Override
    public boolean process(final AuthenticationRequest authenticationRequest,
	    final DiscoveryResult discoveryResult, final Map<String, String> parameters) {
	for (final AuthReq builder : dispatchers) {
	    final boolean processed = builder.process(authenticationRequest, discoveryResult,
		    parameters);
	    if (processed) {
		return true;
	    }
	}
	return false;
    }

}
