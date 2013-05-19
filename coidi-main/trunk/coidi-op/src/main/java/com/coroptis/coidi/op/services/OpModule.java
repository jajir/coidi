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
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.services.ChainBuilder;
import org.apache.tapestry5.services.Dispatcher;

import com.coroptis.coidi.op.services.impl.AssociationProcessorImpl;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.services.impl.AssociationToolImpl;
import com.coroptis.coidi.op.services.impl.AuthProcAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcNonce;
import com.coroptis.coidi.op.services.impl.AuthProcResponse11;
import com.coroptis.coidi.op.services.impl.AuthProcResponse20;
import com.coroptis.coidi.op.services.impl.AuthProcSign;
import com.coroptis.coidi.op.services.impl.AuthProcSreg11;
import com.coroptis.coidi.op.services.impl.AuthenticationImmediateProcessorImpl;
import com.coroptis.coidi.op.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.op.services.impl.AuthenticationSetupProcessorImpl;
import com.coroptis.coidi.op.services.impl.CryptoServiceImpl;
import com.coroptis.coidi.op.services.impl.IdentityNamesConvertorImpl;
import com.coroptis.coidi.op.services.impl.IdentityServiceImpl;
import com.coroptis.coidi.op.services.impl.NegativeResponseGeneratorImpl;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherChecker20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherTerminator;
import com.coroptis.coidi.op.services.impl.OpenIdRequestProcessorImpl;
import com.coroptis.coidi.op.services.impl.OpenIdRequestToolImpl;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationImmediate11;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationImmediate20;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationSetup11;
import com.coroptis.coidi.op.services.impl.OpenidDispatcherAuthenticationSetup20;
import com.coroptis.coidi.op.services.impl.RealmToolImpl;
import com.coroptis.coidi.op.services.impl.SregServiceImpl;
import com.coroptis.coidi.op.services.impl.StatelessModeNonceServiceImpl;
import com.coroptis.coidi.op.util.OpenId11;
import com.coroptis.coidi.op.util.OpenId20;

public class OpModule {// NO_UCD

    public static void bind(ServiceBinder binder) {

	/**
	 * Services
	 */
	binder.bind(AssociationService.class, AssociationServiceImpl.class);
	binder.bind(IdentityService.class, IdentityServiceImpl.class);
	binder.bind(AssociationTool.class, AssociationToolImpl.class);
	binder.bind(CryptoService.class, CryptoServiceImpl.class);
	binder.bind(AuthenticationService.class, AuthenticationServiceImpl.class);
	binder.bind(StatelessModeNonceService.class, StatelessModeNonceServiceImpl.class);
	binder.bind(SregService.class, SregServiceImpl.class);
	binder.bind(RealmTool.class, RealmToolImpl.class);
	binder.bind(NegativeResponseGenerator.class, NegativeResponseGeneratorImpl.class);
	binder.bind(IdentityNamesConvertor.class, IdentityNamesConvertorImpl.class);
	binder.bind(OpenIdRequestProcessor.class, OpenIdRequestProcessorImpl.class);
	binder.bind(AssociationProcessor.class, AssociationProcessorImpl.class);
	binder.bind(AuthenticationSetupProcessor.class, AuthenticationSetupProcessorImpl.class);
	binder.bind(AuthenticationImmediateProcessor.class,
		AuthenticationImmediateProcessorImpl.class);
	binder.bind(OpenIdRequestTool.class, OpenIdRequestToolImpl.class);

    }

    public static void contributeMasterDispatcher(OrderedConfiguration<Dispatcher> configuration,
	    @InjectService("accessControllerDispatcher") Dispatcher accessController) {
	configuration.add("accessControllerDispatcher", accessController, "before:PageRender");
    }

    @Marker(OpenId20.class)
    @Local
    public static OpenIdDispatcher buildOpenIdDispatcher20(List<OpenIdDispatcher> commands,
	    @InjectService("ChainBuilder") ChainBuilder chainBuilder) {
	return chainBuilder.build(OpenIdDispatcher.class, commands);
    }

    @Marker(OpenId11.class)
    @Local
    public static OpenIdDispatcher buildOpenIdDispatcher11(List<OpenIdDispatcher> commands,
	    @InjectService("ChainBuilder") ChainBuilder chainBuilder) {
	return chainBuilder.build(OpenIdDispatcher.class, commands);
    }

    @Contribute(OpenIdDispatcher.class)
    @OpenId20
    public static void contributeOpenIdDispatcher20(
	    OrderedConfiguration<OpenIdDispatcher> configuration,
	    @Autobuild OpenIdDispatcherChecker20 openIdDispatcherChecker,
	    @Autobuild OpenidDispatcherAuthenticationImmediate20 openidDispatcherAuthenticationImmediate20,
	    @Autobuild OpenidDispatcherAuthenticationSetup20 openidDispatcherAuthenticationSetup20,
	    @Autobuild OpenIdDispatcherCheckAuthentication20 openIdDispatcherCheckAuthentication20,
	    @Autobuild OpenIdDispatcherAssociation20 openIdDispatcherAssociation20,
	    @Autobuild OpenIdDispatcherTerminator openIdDispatcherTerminator) {
	configuration.add("openIdDispatcherChecker", openIdDispatcherChecker);
	configuration.add("openidDispatcherAuthenticationImmediate20",
		openidDispatcherAuthenticationImmediate20);
	configuration.add("openidDispatcherAuthenticationSetup20",
		openidDispatcherAuthenticationSetup20);
	configuration.add("openIdDispatcherAssociation20", openIdDispatcherAssociation20);
	configuration.add("openIdDispatcherCheckAuthentication20",
		openIdDispatcherCheckAuthentication20);
	configuration.add("openIdDispatcherTerminator", openIdDispatcherTerminator);
    }

    @Contribute(OpenIdDispatcher.class)
    @OpenId11
    public static void contributeOpenIdDispatcher11(
	    OrderedConfiguration<OpenIdDispatcher> configuration,
	    @Autobuild OpenidDispatcherAuthenticationImmediate11 openidDispatcherAuthenticationImmediate11,
	    @Autobuild OpenidDispatcherAuthenticationSetup11 openidDispatcherAuthenticationSetup11,
	    @Autobuild OpenIdDispatcherCheckAuthentication11 openIdDispatcherCheckAuthentication11,
	    @Autobuild OpenIdDispatcherAssociation11 openIdDispatcherAssociation11,
	    @Autobuild OpenIdDispatcherTerminator openIdDispatcherTerminator) {
	configuration.add("openidDispatcherAuthenticationImmediate11",
		openidDispatcherAuthenticationImmediate11);
	configuration.add("openidDispatcherAuthenticationSetup11",
		openidDispatcherAuthenticationSetup11);
	configuration.add("openIdDispatcherAssociation11", openIdDispatcherAssociation11);
	configuration.add("openIdDispatcherCheckAuthentication11",
		openIdDispatcherCheckAuthentication11);
	configuration.add("openIdDispatcherTerminator", openIdDispatcherTerminator);
    }

    /**
     * Chain of commands process authentication response for protocol version
     * 2.0
     * 
     * @param commands
     * @param chainBuilder
     * @return
     */
    @Marker(OpenId20.class)
    @Local
    public static AuthenticationProcessor buildAuthenticationProcessor20(
	    List<AuthenticationProcessor> commands,
	    @InjectService("ChainBuilder") ChainBuilder chainBuilder) {
	return chainBuilder.build(AuthenticationProcessor.class, commands);
    }

    @Contribute(AuthenticationProcessor.class)
    @OpenId20
    public static void contributeAuthenticationProcessor20(
	    OrderedConfiguration<AuthenticationProcessor> configuration,
	    @Autobuild AuthProcSreg11 authProcSreg11, @Autobuild AuthProcSign authProcSign,
	    @Autobuild AuthProcAssociation authProcAssociation,
	    @Autobuild AuthProcNonce authProcNonce, @Autobuild AuthProcResponse20 authProcResponse20) {
	configuration.add("authProcResponse20", authProcResponse20);
	configuration.add("association", authProcAssociation);
	configuration.add("authProcNonce", authProcNonce);
	configuration.add("authProcSreg11", authProcSreg11);
	configuration.add("authProcSign", authProcSign);
    }

    @Marker(OpenId11.class)
    @Local
    public static AuthenticationProcessor buildAuthenticationProcessor11(
	    List<AuthenticationProcessor> commands,
	    @InjectService("ChainBuilder") ChainBuilder chainBuilder) {
	return chainBuilder.build(AuthenticationProcessor.class, commands);
    }

    @Contribute(AuthenticationProcessor.class)
    @OpenId11
    public static void contributeAuthenticationProcessor11(
	    OrderedConfiguration<AuthenticationProcessor> configuration,
	    @Autobuild AuthProcSign authProcSign,
	    @Autobuild AuthProcAssociation authProcAssociation,
	    @Autobuild AuthProcNonce authProcNonce, @Autobuild AuthProcResponse11 authProcResponse11) {
	configuration.add("authProcResponse11", authProcResponse11);
	configuration.add("association", authProcAssociation);
	configuration.add("authProcNonce", authProcNonce);
	configuration.add("authProcSign", authProcSign);
    }

}
