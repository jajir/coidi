package com.coroptis.coidi.core.util;

import org.easymock.EasyMock;

import com.coroptis.coidi.core.services.ConfigurationService;

public class Services {

	private final ConfigurationService configurationService = EasyMock
			.createMock(ConfigurationService.class);

	private final Object[] mocks = new Object[] { getConfigurationService() };

	private static Services services;

	public static Services getServices() {
		if (services == null) {
			services = new Services();
		}
		return services;
	}

	private Services() {
	}

	public void reset() {
		for (Object object : mocks) {
			EasyMock.reset(object);
		}
	}

	public void verify() {
		for (Object object : mocks) {
			EasyMock.verify(object);
		}
	}

	public void replay() {
		for (Object object : mocks) {
			EasyMock.replay(object);
		}
	}

	/**
	 * @return the configurationService
	 */
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}
}