package com.coroptis.coidi.rp.iocsupport;

import com.coroptis.coidi.core.util.AbstractAuthReq;
import com.coroptis.coidi.rp.services.AuthReq;
import com.google.common.base.Preconditions;

public class AuthReqChain extends AbstractAuthReq {

	public void add(final AuthReq authReq) {
		dispatchers.add(Preconditions.checkNotNull(authReq));
	}

}
