package com.coroptis.coidi.op.view.services;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.op.view.services.impl.XrdsServiceImpl;

public class OpViewModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(XrdsService.class, XrdsServiceImpl.class).eagerLoad();
	}
}
