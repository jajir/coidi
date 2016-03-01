package com.coroptis.coidi.rp.iocsupport;

import java.util.ArrayList;
import java.util.List;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.google.common.base.Preconditions;

public class AuthResponseDecoderChain implements AuthRespDecoder {

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

    public void add(final AuthRespDecoder decoder) {
	dispatchers.add(Preconditions.checkNotNull(decoder));
    }

}
