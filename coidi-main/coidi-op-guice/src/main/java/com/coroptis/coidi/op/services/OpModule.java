package com.coroptis.coidi.op.services;

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
import com.coroptis.coidi.op.services.impl.AssociationProcessorImpl;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.services.impl.AssociationToolImpl;
import com.coroptis.coidi.op.services.impl.AuthProcAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcNonce;
import com.coroptis.coidi.op.services.impl.AuthProcResponse11;
import com.coroptis.coidi.op.services.impl.AuthProcResponse20;
import com.coroptis.coidi.op.services.impl.AuthProcSign;
import com.coroptis.coidi.op.services.impl.AuthProcSreg10;
import com.coroptis.coidi.op.services.impl.AuthProcSreg11;
import com.coroptis.coidi.op.services.impl.AuthProcStateLessAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentity11;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentity20;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentitySelect20;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyLoggedUser;
import com.coroptis.coidi.op.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.op.services.impl.CryptoServiceImpl;
import com.coroptis.coidi.op.services.impl.IdentityNamesConvertorImpl;
import com.coroptis.coidi.op.services.impl.IdentityServiceImpl;
import com.coroptis.coidi.op.services.impl.NegativeResponseGeneratorImpl;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationImmediate11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationImmediate20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationSetup11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationSetup20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherChecker11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherChecker20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherTerminator;
import com.coroptis.coidi.op.services.impl.OpenIdRequestProcessorImpl;
import com.coroptis.coidi.op.services.impl.OpenIdRequestToolImpl;
import com.coroptis.coidi.op.services.impl.RealmToolImpl;
import com.coroptis.coidi.op.services.impl.SregServiceImpl;
import com.coroptis.coidi.op.services.impl.StatelessModeNonceServiceImpl;
import com.coroptis.coidi.op.util.OpenId11;
import com.coroptis.coidi.op.util.OpenId11CheckIdImmediate;
import com.coroptis.coidi.op.util.OpenId11CheckIdSetup;
import com.coroptis.coidi.op.util.OpenId20;
import com.coroptis.coidi.op.util.OpenId20CheckIdImmediate;
import com.coroptis.coidi.op.util.OpenId20CheckIdSetup;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class OpModule extends AbstractModule {

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
		 * OP specific bindings
		 */
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
		
		//TODO following bindings should singletons
		/**
		 * OpenID 2.0 - message dispatcher
		 */
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherChecker20"))
				.to(OpenIdDispatcherChecker20.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAuthenticationImmediate20"))
				.to(OpenIdDispatcherAuthenticationImmediate20.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAuthenticationSetup20"))
				.to(OpenIdDispatcherAuthenticationSetup20.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherCheckAuthentication20"))
				.to(OpenIdDispatcherCheckAuthentication20.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAssociation20"))
				.to(OpenIdDispatcherAssociation20.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherTerminator"))
				.to(OpenIdDispatcherTerminator.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcher20")).to(OpenIdDispatcher20.class);

		/**
		 * OpenID 1.1 - message dispatcher
		 */
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherChecker11"))
				.to(OpenIdDispatcherChecker11.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAuthenticationImmediate11"))
				.to(OpenIdDispatcherAuthenticationImmediate11.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAuthenticationSetup11"))
				.to(OpenIdDispatcherAuthenticationSetup11.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherCheckAuthentication11"))
				.to(OpenIdDispatcherCheckAuthentication11.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcherAssociation11"))
				.to(OpenIdDispatcherAssociation11.class);
		bind(OpenIdDispatcher.class).annotatedWith(Names.named("openIdDispatcher11")).to(OpenIdDispatcher11.class);

		bind(OpenIdDispatcher.class).annotatedWith(OpenId20.class).to(OpenIdDispatcher20.class);
		bind(OpenIdDispatcher.class).annotatedWith(OpenId11.class).to(OpenIdDispatcher11.class);
		
		bind(AuthenticationProcessor.class).annotatedWith(OpenId11CheckIdSetup.class).to(AuthProcCheckIdSetup11.class);
		bind(AuthenticationProcessor.class).annotatedWith(OpenId11CheckIdImmediate.class).to(AuthProcCheckIdImmediate11.class);
		bind(AuthenticationProcessor.class).annotatedWith(OpenId20CheckIdSetup.class).to(AuthProcCheckIdSetup20.class);
		bind(AuthenticationProcessor.class).annotatedWith(OpenId20CheckIdImmediate.class).to(AuthProcCheckIdImmediate20.class);
		
		
		
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcSreg10")).to(AuthProcSreg10.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcSreg11")).to(AuthProcSreg11.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcSign")).to(AuthProcSign.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcVerifyLoggedUser")).to(AuthProcVerifyLoggedUser.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcVerifyIdentitySelect20")).to(AuthProcVerifyIdentitySelect20.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcVerifyIdentity11")).to(AuthProcVerifyIdentity11.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcVerifyIdentity20")).to(AuthProcVerifyIdentity20.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcAssociation")).to(AuthProcAssociation.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcStateLessAssociation")).to(AuthProcStateLessAssociation.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcNonce")).to(AuthProcNonce.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcResponse11")).to(AuthProcResponse11.class);
		bind(AuthenticationProcessor.class).annotatedWith(Names.named("authProcResponse20")).to(AuthProcResponse20.class);


	}
}
