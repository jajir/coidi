package com.coroptis.coidi.rp.view.services;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.Dispatcher;

import com.coroptis.coidi.rp.view.services.impl.AccessControllerDispatcher;
import com.coroptis.coidi.rp.view.services.impl.AuthenticationResponseDispatcher;

public class RpViewModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(Dispatcher.class, AuthenticationResponseDispatcher.class)
				.withId("authenticationResponseDispatcher");
		binder.bind(Dispatcher.class, AccessControllerDispatcher.class).withId(
				"accessControllerDispatcher");

	}

	public static void contributeMasterDispatcher(
			OrderedConfiguration<Dispatcher> configuration,
			@InjectService("authenticationResponseDispatcher") Dispatcher authenticationResponseDispatcher,
			@InjectService("accessControllerDispatcher") Dispatcher accessControllerDispatcher) {
		configuration.add("authenticationResponseDispatcher",
				authenticationResponseDispatcher, "before:PageRender");
		configuration.add("accessControllerDispatcher",
				accessControllerDispatcher, "before:PageRender");
	}

}
