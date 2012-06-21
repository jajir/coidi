package com.coroptis.coidi.core.util;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.services.MessageService;
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
				ConvertorService.class,
				new EasyMockServicebuilder<ConvertorService>(services
						.getConvertorService()));
		binder.bind(
				CryptographyService.class,
				new EasyMockServicebuilder<CryptographyService>(services
						.getCryptographyService()));
		binder.bind(
				MessageService.class,
				new EasyMockServicebuilder<MessageService>(services
						.getMessageService()));
	}
}
