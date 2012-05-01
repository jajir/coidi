package com.coroptis.coidi.op.view.services;

import java.util.List;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.ChainBuilder;

import com.coroptis.coidi.op.view.services.impl.DiscoveryProcessorHtml;
import com.coroptis.coidi.op.view.services.impl.DiscoveryProcessorTerminator;
import com.coroptis.coidi.op.view.services.impl.DiscoveryProcessorYadis;

public class RpViewModule {

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
