package com.coroptis.coidi.rp.util;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.XrdsService;
import com.coroptis.coidi.test.AdditionalBinderProvider;
import com.coroptis.coidi.test.EasyMockServicebuilder;

public class JunitAppModule {

	public static void bind(ServiceBinder binder) {
		/**
		 * Additional bindings
		 */
		AdditionalBinderProvider.bind(binder);

		Services services = Services.getServices();

		binder.bind(
				ConfigurationService.class,
				new EasyMockServicebuilder<ConfigurationService>(services
						.getConfigurationService()));
		binder.bind(
				HttpService.class,
				new EasyMockServicebuilder<HttpService>(services
						.getHttpService()));
		binder.bind(
				XrdsService.class,
				new EasyMockServicebuilder<XrdsService>(services
						.getXrdsService()));
	}
}
