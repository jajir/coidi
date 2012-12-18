/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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