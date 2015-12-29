/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
import com.coroptis.coidi.rp.services.impl.AuthReqRegistration10;
import com.coroptis.coidi.rp.services.impl.AuthReqRegistration11;
import com.coroptis.coidi.rp.services.impl.AuthReqTerminator;
import com.coroptis.coidi.rp.services.impl.AuthReqUiIcon;
import com.coroptis.coidi.rp.services.impl.AuthRespDecoderOpenId;
import com.coroptis.coidi.rp.services.impl.AuthRespDecoderSreg;
import com.coroptis.coidi.rp.services.impl.AuthRespDecoderTerminator;
import com.coroptis.coidi.rp.services.impl.AuthRespSupportImpl;
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
	binder.bind(AuthenticationService.class, AuthenticationServiceImpl.class);
	binder.bind(HttpTransportService.class, HttpTranportServiceImpl.class);
	binder.bind(AuthRespSupport.class, AuthRespSupportImpl.class);
    }

    public static DiscoveryProcessor buildRestChainProcessor(List<DiscoveryProcessor> commands,
	    @InjectService("ChainBuilder") ChainBuilder chainBuilder) {
	return chainBuilder.build(DiscoveryProcessor.class, commands);
    }

    public static void contributeRestChainProcessor(
	    OrderedConfiguration<DiscoveryProcessor> configuration,
	    @Autobuild DiscoveryProcessorHtml discoveryProcessorHtml,
	    @Autobuild DiscoveryProcessorGoogle discoveryProcessorGoogle,
	    @Autobuild DiscoveryProcessorTerminator discoveryProcessorTerminator,
	    @Autobuild DiscoveryProcessorYadis discoveryProcessorYadis) {
	configuration.add("discoveryProcessorGoogle", discoveryProcessorGoogle);
	configuration.add("discoveryProcessorYadis", discoveryProcessorYadis);
	configuration.add("discoveryProcessorHtml", discoveryProcessorHtml);
	configuration.add("discoveryProcessorTerminator", discoveryProcessorTerminator);
    }

    public static AuthReq buildAuthReqChainProcessor(List<AuthReq> commands,
	    @InjectService("ChainBuilder") ChainBuilder chainBuilder) {
	return chainBuilder.build(AuthReq.class, commands);
    }

    public static void contributeAuthReqChainProcessor(OrderedConfiguration<AuthReq> configuration,
	    @Autobuild AuthReqPreconditions authReqPreconditions,
	    @Autobuild AuthReqOpenId authReqOpenId,
	    @Autobuild AuthReqGoogleAttributeExchange authReqGoogleAttributeExchange,
	    @Autobuild AuthReqUiIcon auReqUiIcon,
	    @Autobuild AuthReqRegistration10 authReqRegistration10,
	    @Autobuild AuthReqRegistration11 authReqRegistration11,
	    @Autobuild AuthReqTerminator authReqTerminator) {
	configuration.add("authReqPreconditions", authReqPreconditions);
	configuration.add("authReqOpenId", authReqOpenId);
	configuration.add("authReqGoogleAttributeExchange", authReqGoogleAttributeExchange);
	configuration.add("auReqUiIcon", auReqUiIcon);
	configuration.add("authReqRegistration10", authReqRegistration10);
	configuration.add("authReqRegistration11", authReqRegistration11);
	configuration.add("authReqTerminator", authReqTerminator);
    }

    public static AuthRespDecoder buildAuthRespDecoderChainProcessor(
	    List<AuthRespDecoder> commands, @InjectService("ChainBuilder") ChainBuilder chainBuilder) {
	return chainBuilder.build(AuthRespDecoder.class, commands);
    }

    public static void contributeAuthRespDecoderChainProcessor(
	    OrderedConfiguration<AuthRespDecoder> configuration,
	    @Autobuild AuthRespDecoderOpenId authRespDecoderOpenId,
	    @Autobuild AuthRespDecoderSreg authRespDecoderSreg,
	    @Autobuild AuthRespDecoderTerminator authRespDecoderTerminator) {
	configuration.add("authRespDecoderOpenId", authRespDecoderOpenId);
	configuration.add("authRespDecoderSreg", authRespDecoderSreg);
	configuration.add("authRespDecoderTerminator", authRespDecoderTerminator);
    }
}
