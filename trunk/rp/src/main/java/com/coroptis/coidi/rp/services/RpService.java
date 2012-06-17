package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.base.DiscoveryResult;

public interface RpService {

	String authentication(DiscoveryResult discoveryResult,
			SessionType sessionType, String mode, String userSuppliedId,
			Association association, String returnTo);
}
