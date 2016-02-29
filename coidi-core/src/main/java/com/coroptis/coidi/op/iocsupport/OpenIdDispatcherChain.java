package com.coroptis.coidi.op.iocsupport;

import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.google.common.base.Preconditions;

public class OpenIdDispatcherChain extends AbstractOpenIdDispatcher {

    public void add(final OpenIdDispatcher dispatcher) {
	dispatchers.add(Preconditions.checkNotNull(dispatcher));
    }

}
