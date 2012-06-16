package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.rp.base.DiscoveryResult;

public interface XrdsService {

	DiscoveryResult extractDiscoveryResult(String xrdsDocument);

}
