package com.coroptis.coidi.op.services.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.services.SregService;

public class SregServiceImpl implements SregService {

	@Override
	public Set<String> extractRequestedKeys(
			AuthenticationRequest authenticationRequest) {
		Set<String> keys = new HashSet<String>();
		if (authenticationRequest.getMap().get(
				AuthenticationProcessorSreg10.SREG_OPTIONAL) != null) {
			Collections.addAll(
					keys,
					authenticationRequest.getMap()
							.get(AuthenticationProcessorSreg10.SREG_OPTIONAL)
							.split(","));
		}
		if (authenticationRequest.getMap().get(
				AuthenticationProcessorSreg10.SREG_REQUIRED) != null) {
			Collections.addAll(
					keys,
					authenticationRequest.getMap()
							.get(AuthenticationProcessorSreg10.SREG_REQUIRED)
							.split(","));
		}
		return keys;
	}

}
