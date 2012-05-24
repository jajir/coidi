package com.coroptis.coidi.op.view.services;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.op.view.entities.StatelessModeNonce;

public interface StatelessModeNonceService {

	StatelessModeNonce createStatelessModeNonce(String nonce);

	StatelessModeNonce getByNonce(String noce);

	Boolean verifyCheckAuthenticationRequest(
			final CheckAuthenticationRequest request);
}
