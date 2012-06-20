package com.coroptis.coidi.core.util;

import org.easymock.EasyMock;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.services.ConvertorService;

public class Services {

	private final ConfigurationService configurationService = EasyMock
			.createMock(ConfigurationService.class);
	private final ConvertorService convertorService = EasyMock
			.createMock(ConvertorService.class);

	private final Object[] mocks = new Object[] { getConfigurationService(),
			getConvertorService() };

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

	/**
	 * @return the convertorService
	 */
	public ConvertorService getConvertorService() {
		return convertorService;
	}
}