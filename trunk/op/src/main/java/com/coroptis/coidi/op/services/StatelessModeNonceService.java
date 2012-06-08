package com.coroptis.coidi.op.services;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.op.entities.StatelessModeNonce;

public interface StatelessModeNonceService {

	StatelessModeNonce createStatelessModeNonce(String nonce);

	Boolean isValidCheckAuthenticationRequest(
			final CheckAuthenticationRequest request);
}
