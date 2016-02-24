package com.coroptis.coidi.rp.iocsupport;

import javax.inject.Inject;
import javax.inject.Named;

import com.coroptis.coidi.core.util.AbstractAuthRespDecoder;
import com.coroptis.coidi.rp.services.AuthRespDecoder;

public class SimpleAuthResponseDecoder extends AbstractAuthRespDecoder {

	@Inject
	public SimpleAuthResponseDecoder(

			final @Named("authRespOpenId20Verify") AuthRespDecoder authRespOpenId20Verify,
			final @Named("authRespDecoderOpenId") AuthRespDecoder authRespDecoderOpenId,
			final @Named("authRespDecoderTerminator") AuthRespDecoder authRespDecoderTerminator

	) {
		dispatchers.add(authRespOpenId20Verify);
		dispatchers.add(authRespDecoderOpenId);
		dispatchers.add(authRespDecoderTerminator);
	}

}
