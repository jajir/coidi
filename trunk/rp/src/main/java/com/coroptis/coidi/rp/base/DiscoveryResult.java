package com.coroptis.coidi.rp.base;

import java.util.HashSet;
import java.util.Set;

public class DiscoveryResult {

	private final Set<XrdService> services = new HashSet<XrdService>();

	public XrdService getPreferedService() {
		XrdService out = null;
		for (XrdService service : services) {
			if (out == null) {
				out = service;
			} else if (out.getEffectivePriority() > service
					.getEffectivePriority()) {
				out = service;
			}
		}
		return out;
	}

	/**
	 * @return the endPoint
	 */
	public String getEndPoint() {
		return getPreferedService() == null ? null : getPreferedService()
				.getUrl();
	}

	/**
	 * @return the services
	 */
	public Set<XrdService> getServices() {
		return services;
	}

}
