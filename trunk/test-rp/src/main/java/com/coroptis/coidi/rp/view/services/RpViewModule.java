package com.coroptis.coidi.rp.view.services;

import java.util.List;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.ChainBuilder;

import com.coroptis.coidi.rp.view.services.impl.ConvertorServiceImpl;
import com.coroptis.coidi.rp.view.services.impl.DiscoveryProcessorHtml;
import com.coroptis.coidi.rp.view.services.impl.DiscoveryProcessorTerminator;
import com.coroptis.coidi.rp.view.services.impl.DiscoveryProcessorYadis;
import com.coroptis.coidi.rp.view.services.impl.HttpServiceImpl;
import com.coroptis.coidi.rp.view.services.impl.XrdsServiceImpl;
import com.coroptis.coidi.rp.view.services.impl.YadisServiceImpl;

public class RpViewModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(HttpService.class, HttpServiceImpl.class);
		binder.bind(ConvertorService.class, ConvertorServiceImpl.class);
		binder.bind(YadisService.class, YadisServiceImpl.class);
		binder.bind(XrdsService.class, XrdsServiceImpl.class);
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

}
