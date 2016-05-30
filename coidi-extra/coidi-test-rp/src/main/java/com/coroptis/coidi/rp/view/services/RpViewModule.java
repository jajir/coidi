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
package com.coroptis.coidi.rp.view.services;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.Dispatcher;

import com.coroptis.coidi.rp.iocsupport.RpBinding;
import com.coroptis.coidi.rp.services.RpConfigurationService;
import com.coroptis.coidi.rp.view.services.impl.AccessControllerDispatcher;
import com.coroptis.coidi.rp.view.services.impl.AuthenticationResponseDispatcher;

public class RpViewModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(Dispatcher.class, AuthenticationResponseDispatcher.class)
				.withId("authenticationResponseDispatcher");
		binder.bind(Dispatcher.class, AccessControllerDispatcher.class).withId("accessControllerDispatcher");
		binder.bind(RpConfigurationService.class, RpConfigurationServiceImpl.class);
	}

	public static void contributeMasterDispatcher(OrderedConfiguration<Dispatcher> configuration,
			@InjectService("authenticationResponseDispatcher") Dispatcher authenticationResponseDispatcher,
			@InjectService("accessControllerDispatcher") Dispatcher accessControllerDispatcher) {
		configuration.add("authenticationResponseDispatcher", authenticationResponseDispatcher, "before:PageRender");
		configuration.add("accessControllerDispatcher", accessControllerDispatcher, "before:PageRender");
	}

	public static RpBinding buildRpBinding(final RpConfigurationService conf) {
		return new RpBinding(conf) {
		};
	}

}
