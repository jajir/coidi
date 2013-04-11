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
package com.coroptis.coidi.op.services;

import java.util.List;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Autobuild;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.Dispatcher;

import com.coroptis.coidi.op.services.impl.AssociationToolImpl;
import com.coroptis.coidi.op.services.impl.AuthProcAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcNonce;
import com.coroptis.coidi.op.services.impl.AuthProcResponse;
import com.coroptis.coidi.op.services.impl.AuthProcSign;
import com.coroptis.coidi.op.services.impl.AuthProcSreg11;
import com.coroptis.coidi.op.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.op.services.impl.CryptoServiceImpl;
import com.coroptis.coidi.op.services.impl.IdentityNamesConvertorImpl;
import com.coroptis.coidi.op.services.impl.IdentityServiceImpl;
import com.coroptis.coidi.op.services.impl.NegativeResponseGeneratorImpl;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherChecker;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherTerminator;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationImmediate;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationSetup;
import com.coroptis.coidi.op.services.impl.SregServiceImpl;
import com.coroptis.coidi.op.services.impl.StatelessModeNonceServiceImpl;
import com.coroptis.coidi.op.services.impl.XrdsServiceImpl;

public class OpModule {// NO_UCD

    public static void bind(ServiceBinder binder) {

	/**
	 * Services
	 */
	binder.bind(XrdsService.class, XrdsServiceImpl.class);
	binder.bind(IdentityService.class, IdentityServiceImpl.class);
	binder.bind(AssociationTool.class, AssociationToolImpl.class);
	binder.bind(CryptoService.class, CryptoServiceImpl.class);
	binder.bind(AuthenticationService.class, AuthenticationServiceImpl.class);
	binder.bind(StatelessModeNonceService.class, StatelessModeNonceServiceImpl.class);
	binder.bind(SregService.class, SregServiceImpl.class);
	binder.bind(NegativeResponseGenerator.class, NegativeResponseGeneratorImpl.class);
	binder.bind(IdentityNamesConvertor.class, IdentityNamesConvertorImpl.class);
    }

    public static void contributeMasterDispatcher(OrderedConfiguration<Dispatcher> configuration,
	    @InjectService("accessControllerDispatcher") Dispatcher accessController) {
	configuration.add("accessControllerDispatcher", accessController, "before:PageRender");
    }

    public static OpenIdDispatcher buildOpenIdDispatcher(List<OpenIdDispatcher> commands,
	    @InjectService("ChainBuilder") ChainBuilder chainBuilder) {
	return chainBuilder.build(OpenIdDispatcher.class, commands);
    }

    public static void contributeOpenIdDispatcher(
	    OrderedConfiguration<OpenIdDispatcher> configuration,
	    @Autobuild OpenIdDispatcherChecker openIdDispatcherChecker,
	    @Autobuild OpenidDispatcherAuthenticationImmediate openidDispatcherAuthenticationImmediate,
	    @Autobuild OpenidDispatcherAuthenticationSetup openidDispatcherAuthenticationSetup,
	    @Autobuild OpenIdDispatcherCheckAuthentication openIdDispatcherCheckAuthentication,
	    @Autobuild OpenIdDispatcherAssociation openIdDispatcherAssociation,
	    @Autobuild OpenIdDispatcherTerminator openIdDispatcherTerminator) {
	configuration.add("openIdDispatcherChecker", openIdDispatcherChecker);
	configuration.add("openidDispatcherAuthenticationImmediate",
		openidDispatcherAuthenticationImmediate);
	configuration.add("openidDispatcherAuthenticationSetup",
		openidDispatcherAuthenticationSetup);
	configuration.add("openIdDispatcherAssociation", openIdDispatcherAssociation);
	configuration.add("openIdDispatcherCheckAuthentication",
		openIdDispatcherCheckAuthentication);
	configuration.add("openIdDispatcherTerminator", openIdDispatcherTerminator);
    }

    /**
     * Chain of commands process authentication response.
     * 
     * @param commands
     * @param chainBuilder
     * @return
     */
    public static AuthenticationProcessor buildAuthenticationProcessor(
	    List<AuthenticationProcessor> commands,
	    @InjectService("ChainBuilder") ChainBuilder chainBuilder) {
	return chainBuilder.build(AuthenticationProcessor.class, commands);
    }

    public static void contributeAuthenticationProcessor(
	    OrderedConfiguration<AuthenticationProcessor> configuration,
	    @Autobuild AuthProcSreg11 authProcSreg11, @Autobuild AuthProcSign authProcSign,
	    @Autobuild AuthProcAssociation authProcAssociation,
	    @Autobuild AuthProcNonce authProcNonce, @Autobuild AuthProcResponse authProcResponse) {
	configuration.add("authProcResponse", authProcResponse);
	configuration.add("association", authProcAssociation);
	configuration.add("authProcNonce", authProcNonce);
	configuration.add("authProcSreg11", authProcSreg11);
	configuration.add("authProcSign", authProcSign);
    }

}
