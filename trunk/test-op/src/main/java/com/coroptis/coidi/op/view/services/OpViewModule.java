package com.coroptis.coidi.op.view.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.services.Dispatcher;

import com.coroptis.coidi.op.view.services.impl.AccessControllerDispatcher;

public class OpViewModule {// NO_UCD

	public static void bind(ServiceBinder binder) {
		binder.bind(Dispatcher.class, AccessControllerDispatcher.class).withId(
				"accessControllerDispatcher");
	}

	public static void contributeMasterDispatcher(
			OrderedConfiguration<Dispatcher> configuration,
			@InjectService("accessControllerDispatcher") Dispatcher accessController) {
		configuration.add("accessControllerDispatcher", accessController,
				"before:PageRender");
	}

	public static void contributeHibernateEntityPackageManager(
			Configuration<String> conf) {
		conf.add("com.coroptis.coidi.op.entities");
	}

}
