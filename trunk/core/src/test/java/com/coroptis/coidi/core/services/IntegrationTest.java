package com.coroptis.coidi.core.services;

import java.util.Map;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.util.AbstractIntegrationTest;

public class IntegrationTest extends AbstractIntegrationTest {

	public void testClient() throws Exception {
		ConfigurationService configurationService = getService(ConfigurationService.class);

		Map<String, String> conf = configurationService
				.loadDefaultConfiguration("test");
		assertEquals("George", conf.get("name"));
	}

	public void testSymbolProvider_name() throws Exception {
		TestService testService = getService(TestService.class);

		assertEquals("George", testService.getName());
	}

	public void testSymbolProvider_surname() throws Exception {
		TestService testService = getService(TestService.class);

		assertEquals("Valentine", testService.getSurname());
	}

	public void testSymbolProvider_favoriteColor() throws Exception {
		TestService testService = getService(TestService.class);

		assertEquals("black", testService.getFavoriteColor());
	}
}
