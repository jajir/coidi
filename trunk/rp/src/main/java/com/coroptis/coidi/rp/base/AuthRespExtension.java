package com.coroptis.coidi.rp.base;

import com.coroptis.coidi.core.message.AuthenticationResponse;

public class AuthRespExtension {

	private final String extensionAlias;

	private final AuthenticationResponse authenticationResponse;

	public AuthRespExtension(final String extensionAlias,
			final AuthenticationResponse authenticationResponse) {
		this.extensionAlias = extensionAlias;
		this.authenticationResponse = authenticationResponse;
	}

	public String getValueForName(String name) {
		return authenticationResponse.get(extensionAlias + ".value." + name);
	}

}
