package com.coroptis.coidi.test;

import org.apache.tapestry5.ioc.ServiceBuilder;
import org.apache.tapestry5.ioc.ServiceResources;

/**
 * Provide T5 access to mocked service implementations.
 * 
 * @author jan
 * 
 * @param <T>
 */
public class EasyMockServicebuilder<T> implements ServiceBuilder<T> {

	private final T service;

	public EasyMockServicebuilder(final T service) {
		this.service = service;
	}

	public T buildService(ServiceResources resources) {
		return service;
	}

}
