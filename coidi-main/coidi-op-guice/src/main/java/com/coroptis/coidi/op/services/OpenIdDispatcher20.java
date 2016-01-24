package com.coroptis.coidi.op.services;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
@Singleton
public class OpenIdDispatcher20 extends AbstractOpenIdDispatcher {

    @Inject
    public OpenIdDispatcher20(
	    @Named("openIdDispatcherChecker20") final OpenIdDispatcher openIdDispatcherChecker20,
	    @Named("openIdDispatcherAuthenticationImmediate20") final OpenIdDispatcher openidDispatcherAuthenticationImmediate20,
	    @Named("openIdDispatcherAuthenticationSetup20") final OpenIdDispatcher openidDispatcherAuthenticationSetup20,
	    @Named("openIdDispatcherCheckAuthentication20") final OpenIdDispatcher openIdDispatcherCheckAuthentication20,
	    @Named("openIdDispatcherAssociation20") final OpenIdDispatcher openIdDispatcherAssociation20,
	    @Named("openIdDispatcherTerminator") final OpenIdDispatcher openIdDispatcherTerminator) {
	dispatchers.add(openIdDispatcherChecker20);
	dispatchers.add(openidDispatcherAuthenticationImmediate20);
	dispatchers.add(openidDispatcherAuthenticationSetup20);
	dispatchers.add(openIdDispatcherCheckAuthentication20);
	dispatchers.add(openIdDispatcherAssociation20);
	dispatchers.add(openIdDispatcherTerminator);
    }

}
