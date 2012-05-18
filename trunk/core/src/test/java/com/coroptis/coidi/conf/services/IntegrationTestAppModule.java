package com.coroptis.coidi.conf.services;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.util.TestServiceImpl;

/**
 * Here should be defined testing services.
 * 
 * @author jan
 * 
 */
public class IntegrationTestAppModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(TestService.class, TestServiceImpl.class);
	}

}
