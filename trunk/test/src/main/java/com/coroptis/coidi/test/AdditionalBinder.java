package com.coroptis.coidi.test;

import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * Class that implement this interface can be executed during binding of T5
 * startup. Implementing class have to be setted into
 * {@link AdditionalBinderProvider} class.
 * 
 * @author jan
 * 
 */
public interface AdditionalBinder {

	void bind(ServiceBinder binder);

}
