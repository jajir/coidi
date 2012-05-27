package com.coroptis.coidi.rp.view.services;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.view.services.impl.DiscoveryResult;

public interface RpService {

	String authentication(DiscoveryResult discoveryResult,
			SessionType sessionType, String mode, String userSuppliedId,
			Association association);
}
