package com.coroptis.coidi.rp.iocsupport;

import com.coroptis.coidi.core.util.AbstractAuthRespDecoder;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.google.common.base.Preconditions;

public class AuthResponseDecoderChain extends AbstractAuthRespDecoder {

	public void add(final AuthRespDecoder decoder) {
		dispatchers.add(Preconditions.checkNotNull(decoder));
	}

}
