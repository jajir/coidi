package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationParameters;
import com.coroptis.coidi.rp.base.DiscoveryResult;

public interface RpService {

	String authentication(DiscoveryResult discoveryResult,
			Association association,
			AuthenticationParameters authenticationParameters);
}
