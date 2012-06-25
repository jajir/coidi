package com.coroptis.coidi.rp.util;

import org.easymock.EasyMock;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.XrdsService;

public class Services {

	private final ConfigurationService configurationService = EasyMock
			.createMock(ConfigurationService.class);
	private final HttpService httpService = EasyMock
			.createMock(HttpService.class);
	private final XrdsService xrdsService = EasyMock
			.createMock(XrdsService.class);

	private final Object[] mocks = new Object[] { getConfigurationService(),
			getHttpService(), getXrdsService() };

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
	 * @return the httpService
	 */
	public HttpService getHttpService() {
		return httpService;
	}

	/**
	 * @return the xrdsService
	 */
	public XrdsService getXrdsService() {
		return xrdsService;
	}
}