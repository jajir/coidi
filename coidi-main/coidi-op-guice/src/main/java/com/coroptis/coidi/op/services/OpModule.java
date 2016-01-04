package com.coroptis.coidi.op.services;

import com.coroptis.coidi.op.services.impl.AssociationProcessorImpl;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.services.impl.AssociationToolImpl;
import com.coroptis.coidi.op.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.op.services.impl.CryptoServiceImpl;
import com.coroptis.coidi.op.services.impl.IdentityNamesConvertorImpl;
import com.coroptis.coidi.op.services.impl.IdentityServiceImpl;
import com.coroptis.coidi.op.services.impl.NegativeResponseGeneratorImpl;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherChecker11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherChecker20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherTerminator;
import com.coroptis.coidi.op.services.impl.OpenIdRequestProcessorImpl;
import com.coroptis.coidi.op.services.impl.OpenIdRequestToolImpl;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationImmediate11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationImmediate20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationSetup11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationSetup20;
import com.coroptis.coidi.op.services.impl.RealmToolImpl;
import com.coroptis.coidi.op.services.impl.SregServiceImpl;
import com.coroptis.coidi.op.services.impl.StatelessModeNonceServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class OpModule extends AbstractModule {

    @Override
    protected void configure() {
	
	bind(AssociationService.class).to(AssociationServiceImpl.class);
	bind(IdentityService.class).to(IdentityServiceImpl.class);
	bind(AssociationTool.class).to(AssociationToolImpl.class);
	bind(CryptoService.class).to(CryptoServiceImpl.class);
	bind(AuthenticationService.class).to(AuthenticationServiceImpl.class);
	bind(StatelessModeNonceService.class).to(StatelessModeNonceServiceImpl.class);
	bind(SregService.class).to(SregServiceImpl.class);
	bind(RealmTool.class).to(RealmToolImpl.class);
	bind(NegativeResponseGenerator.class).to(NegativeResponseGeneratorImpl.class);
	bind(IdentityNamesConvertor.class).to(IdentityNamesConvertorImpl.class);
	bind(OpenIdRequestProcessor.class).to(OpenIdRequestProcessorImpl.class);
	bind(AssociationProcessor.class).to(AssociationProcessorImpl.class);
	bind(OpenIdRequestTool.class).to(OpenIdRequestToolImpl.class);
	bind(OpConfigurationService.class).to(OpConfigurationServiceImpl.class);

	/**
	 * OpenID 2.0 - message dispatcher
	 */
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherChecker20")).to(OpenIdDispatcherChecker20.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAuthenticationImmediate20")).to(OpenIdDispatcherAuthenticationImmediate20.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAuthenticationSetup20")).to(OpenIdDispatcherAuthenticationSetup20.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherCheckAuthentication20")).to(OpenIdDispatcherCheckAuthentication20.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAssociation20")).to(OpenIdDispatcherAssociation20.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherTerminator")).to(OpenIdDispatcherTerminator.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcher20")).to(OpenIdDispatcher20.class);
	
	/**
	 * OpenID 1.1 - message dispatcher
	 */
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherChecker11")).to(OpenIdDispatcherChecker11.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAuthenticationImmediate11")).to(OpenIdDispatcherAuthenticationImmediate11.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAuthenticationSetup11")).to(OpenIdDispatcherAuthenticationSetup11.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherCheckAuthentication11")).to(OpenIdDispatcherCheckAuthentication11.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAssociation11")).to(OpenIdDispatcherAssociation11.class);
	bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcher11")).to(OpenIdDispatcher11.class);
	
	
	/**
	 * OpenID 1.1 - Authentication processor - mode=checkid_setup 
	 */
	
	/**
	 * OpenID 1.1 - Authentication processor - mode=checkid_immediate
	 */
	
	/**
	 * OpenID 2.0 - Authentication processor - mode=checkid_setup 
	 */
	
	/**
	 * OpenID 2.0 - Authentication processor - mode=checkid_immediate
	 */
	
    }
}
