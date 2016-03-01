package com.coroptis.coidi.rp.iocsupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.AuthReq;
import com.google.common.base.Preconditions;

public class AuthReqChain implements AuthReq {

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

    public void add(final AuthReq authReq) {
	dispatchers.add(Preconditions.checkNotNull(authReq));
    }

}
