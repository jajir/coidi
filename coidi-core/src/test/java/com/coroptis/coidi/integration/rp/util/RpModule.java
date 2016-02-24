package com.coroptis.coidi.integration.rp.util;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.core.services.impl.ConvertorServiceImpl;
import com.coroptis.coidi.core.services.impl.CryptoSessionServiceImpl;
import com.coroptis.coidi.core.services.impl.CryptographyServiceImpl;
import com.coroptis.coidi.core.services.impl.MessageServiceImpl;
import com.coroptis.coidi.core.services.impl.NonceServiceImpl;
import com.coroptis.coidi.core.services.impl.SigningServiceImpl;
import com.coroptis.coidi.rp.iocsupport.AuthProcSimpleRp;
import com.coroptis.coidi.rp.iocsupport.RpConfigurationServiceImpl;
import com.coroptis.coidi.rp.iocsupport.SimpleAuthResponseDecoder;
import com.coroptis.coidi.rp.services.AssociationFactory;
import com.coroptis.coidi.rp.services.AssociationHelper;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.services.AuthRespDecoder;
import com.coroptis.coidi.rp.services.AuthRespSupport;
import com.coroptis.coidi.rp.services.AuthenticationVerificationService;
import com.coroptis.coidi.rp.services.DiscoveryProcessor;
import com.coroptis.coidi.rp.services.DiscoveryService;
import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.HttpTransportService;
import com.coroptis.coidi.rp.services.NonceStorage;
import com.coroptis.coidi.rp.services.RpConfigurationService;
import com.coroptis.coidi.rp.services.RpService;
import com.coroptis.coidi.rp.services.XmlProcessing;
import com.coroptis.coidi.rp.services.XrdsService;
import com.coroptis.coidi.rp.services.YadisService;
import com.coroptis.coidi.rp.services.impl.AssociationFactoryImpl;
import com.coroptis.coidi.rp.services.impl.AssociationHelperImpl;
import com.coroptis.coidi.rp.services.impl.AuthReqPreconditions;
import com.coroptis.coidi.rp.services.impl.AuthReqTerminator;
import com.coroptis.coidi.rp.services.impl.AuthReqUiIcon;
import com.coroptis.coidi.rp.services.impl.AuthRespDecoderOpenId;
import com.coroptis.coidi.rp.services.impl.AuthRespDecoderTerminator;
import com.coroptis.coidi.rp.services.impl.AuthRespOpenId20Verify;
import com.coroptis.coidi.rp.services.impl.AuthRespSupportImpl;
import com.coroptis.coidi.rp.services.impl.AuthenticationVerificationServiceImpl;
import com.coroptis.coidi.rp.services.impl.DiscoveryProcessorYadis;
import com.coroptis.coidi.rp.services.impl.DiscoveryServiceImpl;
import com.coroptis.coidi.rp.services.impl.DiscoverySupportImpl;
import com.coroptis.coidi.rp.services.impl.HttpServiceImpl;
import com.coroptis.coidi.rp.services.impl.HttpTranportServiceImpl;
import com.coroptis.coidi.rp.services.impl.NonceStoreInMemory;
import com.coroptis.coidi.rp.services.impl.RpServiceImpl;
import com.coroptis.coidi.rp.services.impl.XmlProcessingImpl;
import com.coroptis.coidi.rp.services.impl.XrdsServiceImpl;
import com.coroptis.coidi.rp.services.impl.YadisServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Main application module.
 * 
 * @author jiroutj
 * 
 */
public class RpModule extends AbstractModule {

    @Override
    protected void configure() {
	/**
	 * Coidi generic bindings
	 */
	bind(NonceService.class).to(NonceServiceImpl.class);
	bind(SigningService.class).to(SigningServiceImpl.class);
	bind(CryptographyService.class).to(CryptographyServiceImpl.class);
	bind(CryptoSessionService.class).to(CryptoSessionServiceImpl.class);
	bind(ConvertorService.class).to(ConvertorServiceImpl.class);
	bind(MessageService.class).to(MessageServiceImpl.class);

	/**
	 * RP specific bindings
	 */
	bind(HttpService.class).to(HttpServiceImpl.class);
	bind(YadisService.class).to(YadisServiceImpl.class);
	bind(XrdsService.class).to(XrdsServiceImpl.class);
	bind(AssociationFactory.class).to(AssociationFactoryImpl.class);
	bind(NonceStorage.class).to(NonceStoreInMemory.class);
	bind(DiscoveryService.class).to(DiscoveryServiceImpl.class);
	bind(DiscoverySupport.class).to(DiscoverySupportImpl.class);
	bind(RpService.class).to(RpServiceImpl.class);
	bind(XmlProcessing.class).to(XmlProcessingImpl.class);
	bind(AuthenticationVerificationService.class).to(AuthenticationVerificationServiceImpl.class);
	bind(HttpTransportService.class).to(HttpTranportServiceImpl.class);
	bind(AuthRespSupport.class).to(AuthRespSupportImpl.class);
	bind(RpConfigurationService.class).to(RpConfigurationServiceImpl.class);
	bind(DiscoveryProcessor.class).to(DiscoveryProcessorYadis.class);
	bind(AssociationHelper.class).to(AssociationHelperImpl.class);

	bind(AuthReq.class)
		.to(AuthProcSimpleRp.class);
	bind(AuthReq.class).annotatedWith(Names.named("authReqPreconditions"))
		.to(AuthReqPreconditions.class);
	bind(AuthReq.class).annotatedWith(Names.named("authReqUiIcon")).to(AuthReqUiIcon.class);
	bind(AuthReq.class).annotatedWith(Names.named("authReqTerminator"))
		.to(AuthReqTerminator.class);
	

	bind(AuthRespDecoder.class).to(SimpleAuthResponseDecoder.class);
	bind(AuthRespDecoder.class).annotatedWith(Names.named("authRespDecoderOpenId"))
		.to(AuthRespDecoderOpenId.class);
	bind(AuthRespDecoder.class).annotatedWith(Names.named("authRespDecoderTerminator"))
	.to(AuthRespDecoderTerminator.class);
	bind(AuthRespDecoder.class).annotatedWith(Names.named("authRespOpenId20Verify"))
	.to(AuthRespOpenId20Verify.class);

    }

}
