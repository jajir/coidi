package com.coroptis.coidi.rp.services;

import java.util.List;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.ChainBuilder;

import com.coroptis.coidi.rp.services.impl.AssociationServiseImpl;
import com.coroptis.coidi.rp.services.impl.AuthReqGoogleAttributeExchange;
import com.coroptis.coidi.rp.services.impl.AuthReqOpenId;
import com.coroptis.coidi.rp.services.impl.AuthReqPreconditions;
import com.coroptis.coidi.rp.services.impl.AuthReqTerminator;
import com.coroptis.coidi.rp.services.impl.AuthReqUiIcon;
import com.coroptis.coidi.rp.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.rp.services.impl.DiscoveryProcessorGoogle;
import com.coroptis.coidi.rp.services.impl.DiscoveryProcessorHtml;
import com.coroptis.coidi.rp.services.impl.DiscoveryProcessorTerminator;
import com.coroptis.coidi.rp.services.impl.DiscoveryProcessorYadis;
import com.coroptis.coidi.rp.services.impl.DiscoveryServiceImpl;
import com.coroptis.coidi.rp.services.impl.DiscoverySupportImpl;
import com.coroptis.coidi.rp.services.impl.HttpServiceImpl;
import com.coroptis.coidi.rp.services.impl.HttpTranportServiceImpl;
import com.coroptis.coidi.rp.services.impl.NonceDaoImpl;
import com.coroptis.coidi.rp.services.impl.RpServiceImpl;
import com.coroptis.coidi.rp.services.impl.XmlProcessingImpl;
import com.coroptis.coidi.rp.services.impl.XrdsServiceImpl;
import com.coroptis.coidi.rp.services.impl.YadisServiceImpl;

public class RpModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(HttpService.class, HttpServiceImpl.class);
		binder.bind(YadisService.class, YadisServiceImpl.class);
		binder.bind(XrdsService.class, XrdsServiceImpl.class);
		binder.bind(AssociationServise.class, AssociationServiseImpl.class);
		binder.bind(NonceDao.class, NonceDaoImpl.class);
		binder.bind(DiscoveryService.class, DiscoveryServiceImpl.class);
		binder.bind(DiscoverySupport.class, DiscoverySupportImpl.class);
		binder.bind(RpService.class, RpServiceImpl.class);
		binder.bind(XmlProcessing.class, XmlProcessingImpl.class);
		binder.bind(AuthenticationService.class,
				AuthenticationServiceImpl.class);
		binder.bind(HttpTransportService.class, HttpTranportServiceImpl.class);

	}

	public static DiscoveryProcessor buildRestChainProcessor(
			List<DiscoveryProcessor> commands,
			@InjectService("ChainBuilder") ChainBuilder chainBuilder) {
		return chainBuilder.build(DiscoveryProcessor.class, commands);
	}

	public static void contributeRestChainProcessor(
			OrderedConfiguration<DiscoveryProcessor> configuration,
			@Autobuild DiscoveryProcessorHtml discoveryProcessorHtml,
			@Autobuild DiscoveryProcessorGoogle discoveryProcessorGoogle,
			@Autobuild DiscoveryProcessorTerminator discoveryProcessorTerminator,
			@Autobuild DiscoveryProcessorYadis discoveryProcessorYadis) {
		configuration.add("discoveryProcessorHtml", discoveryProcessorHtml);
		configuration.add("discoveryProcessorGoogle", discoveryProcessorGoogle);
		configuration.add("discoveryProcessorYadis", discoveryProcessorYadis);
		configuration.add("discoveryProcessorTerminator",
				discoveryProcessorTerminator);
	}

	public static AuthReq buildAuthReqChainProcessor(List<AuthReq> commands,
			@InjectService("ChainBuilder") ChainBuilder chainBuilder) {
		return chainBuilder.build(AuthReq.class, commands);
	}

	public static void contributeAuthReqChainProcessor(
			OrderedConfiguration<AuthReq> configuration,
			@Autobuild AuthReqPreconditions authReqPreconditions,
			@Autobuild AuthReqGoogleAttributeExchange authReqGoogleAttributeExchange,
			@Autobuild AuthReqOpenId authReqOpenId,
			@Autobuild AuthReqUiIcon auReqUiIcon,
			@Autobuild AuthReqTerminator authReqTerminator) {
		configuration.add("authReqPreconditions", authReqPreconditions);
		configuration.add("authReqGoogleAttributeExchange",
				authReqGoogleAttributeExchange);
		configuration.add("authReqOpenId", authReqOpenId);
		configuration.add("auReqUiIcon", auReqUiIcon);
		configuration.add("authReqTerminator", authReqTerminator);
	}

}
