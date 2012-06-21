package com.coroptis.coidi.core.util;

import org.easymock.EasyMock;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.services.MessageService;

public class Services {

	private final ConfigurationService configurationService = EasyMock
			.createMock(ConfigurationService.class);
	private final ConvertorService convertorService = EasyMock
			.createMock(ConvertorService.class);
	private final CryptographyService cryptographyService = EasyMock
			.createMock(CryptographyService.class);
	private final MessageService messageService = EasyMock
			.createMock(MessageService.class);

	private final Object[] mocks = new Object[] { getConfigurationService(),
			getConvertorService(), getCryptographyService(),
			getMessageService() };

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

	/**
	 * @return the cryptographyService
	 */
	public CryptographyService getCryptographyService() {
		return cryptographyService;
	}

	/**
	 * @return the messageService
	 */
	public MessageService getMessageService() {
		return messageService;
	}
}