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

import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.services.impl.AuthenticationProcessorResponse;
import com.coroptis.coidi.op.services.impl.AuthenticationProcessorSreg11;
import com.coroptis.coidi.op.services.impl.AuthenticationProcessorTerminator;
import com.coroptis.coidi.op.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.op.services.impl.CryptoServiceImpl;
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
import com.coroptis.coidi.op.services.impl.UserServiceImpl;
import com.coroptis.coidi.op.services.impl.XrdsServiceImpl;

public class OpModule {// NO_UCD

    public static void bind(ServiceBinder binder) {

	/**
	 * Services
	 */
	binder.bind(XrdsService.class, XrdsServiceImpl.class);
	binder.bind(IdentityService.class, IdentityServiceImpl.class);
	binder.bind(AssociationService.class, AssociationServiceImpl.class);
	binder.bind(CryptoService.class, CryptoServiceImpl.class);
	binder.bind(UserService.class, UserServiceImpl.class);
	binder.bind(AuthenticationService.class, AuthenticationServiceImpl.class);
	binder.bind(StatelessModeNonceService.class, StatelessModeNonceServiceImpl.class);
	binder.bind(SregService.class, SregServiceImpl.class);
	binder.bind(NegativeResponseGenerator.class, NegativeResponseGeneratorImpl.class);
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
	    @Autobuild AuthenticationProcessorSreg11 authenticationFilterSreg11,
	    @Autobuild AuthenticationProcessorTerminator authenticationFilterTerminator,
	    @Autobuild AuthenticationProcessorResponse authenticationProcessorResponse) {
	configuration.add("authenticationProcessorResponse", authenticationProcessorResponse);
	configuration.add("authenticationFilterSreg11", authenticationFilterSreg11);
	configuration.add("authenticationFilterTerminator", authenticationFilterTerminator);
    }

}
