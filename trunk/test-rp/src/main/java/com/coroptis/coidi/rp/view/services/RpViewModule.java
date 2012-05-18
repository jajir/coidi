package com.coroptis.coidi.rp.view.services;

import java.util.List;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.Dispatcher;

import com.coroptis.coidi.rp.view.services.impl.AccessControllerDispatcher;
import com.coroptis.coidi.rp.view.services.impl.AssociationServiseImpl;
import com.coroptis.coidi.rp.view.services.impl.AuthenticationResponseDispatcher;
import com.coroptis.coidi.rp.view.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.rp.view.services.impl.ConvertorServiceImpl;
import com.coroptis.coidi.rp.view.services.impl.DiscoveryProcessorHtml;
import com.coroptis.coidi.rp.view.services.impl.DiscoveryProcessorTerminator;
import com.coroptis.coidi.rp.view.services.impl.DiscoveryProcessorYadis;
import com.coroptis.coidi.rp.view.services.impl.HttpServiceImpl;
import com.coroptis.coidi.rp.view.services.impl.NonceDaoImpl;
import com.coroptis.coidi.rp.view.services.impl.NonceServiceImpl;
import com.coroptis.coidi.rp.view.services.impl.XrdsServiceImpl;
import com.coroptis.coidi.rp.view.services.impl.YadisServiceImpl;

public class RpViewModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(HttpService.class, HttpServiceImpl.class);
		binder.bind(ConvertorService.class, ConvertorServiceImpl.class);
		binder.bind(YadisService.class, YadisServiceImpl.class);
		binder.bind(XrdsService.class, XrdsServiceImpl.class);
		binder.bind(AssociationServise.class, AssociationServiseImpl.class);
		binder.bind(NonceDao.class, NonceDaoImpl.class);
		binder.bind(AuthenticationService.class,
				AuthenticationServiceImpl.class);
		binder.bind(NonceService.class, NonceServiceImpl.class);
		binder.bind(Dispatcher.class, AuthenticationResponseDispatcher.class)
				.withId("authenticationResponseDispatcher");
		binder.bind(Dispatcher.class, AccessControllerDispatcher.class).withId(
				"accessControllerDispatcher");

	}

	public static DiscoveryProcessor buildRestChainProcessor(
			List<DiscoveryProcessor> commands,
			@InjectService("ChainBuilder") ChainBuilder chainBuilder) {
		return chainBuilder.build(DiscoveryProcessor.class, commands);
	}

	public static void contributeRestChainProcessor(
			OrderedConfiguration<DiscoveryProcessor> configuration,
			@Autobuild DiscoveryProcessorHtml discoveryProcessorHtml,
			@Autobuild DiscoveryProcessorTerminator discoveryProcessorTerminator,
			@Autobuild DiscoveryProcessorYadis discoveryProcessorYadis) {
		configuration.add("discoveryProcessorHtml", discoveryProcessorHtml);
		configuration.add("discoveryProcessorYadis", discoveryProcessorYadis);
		configuration.add("discoveryProcessorTerminator",
				discoveryProcessorTerminator);
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
