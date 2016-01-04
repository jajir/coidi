package com.coroptis.coidi.op.services;

import javax.inject.Inject;
import javax.inject.Named;

public class OpenIdDispatcher11 extends AbstractOpenIdDispatcher {

    @Inject
    public OpenIdDispatcher11(
	    @Named("openIdDispatcherChecker11") final OpenIdDispatcher openIdDispatcherChecker11,
	    @Named("openIdDispatcherAuthenticationImmediate11") final OpenIdDispatcher openidDispatcherAuthenticationImmediate11,
	    @Named("openIdDispatcherAuthenticationSetup11") final OpenIdDispatcher openidDispatcherAuthenticationSetup11,
	    @Named("openIdDispatcherCheckAuthentication11") final OpenIdDispatcher openIdDispatcherCheckAuthentication11,
	    @Named("openIdDispatcherAssociation11") final OpenIdDispatcher openIdDispatcherAssociation11,
	    @Named("openIdDispatcherTerminator") final OpenIdDispatcher openIdDispatcherTerminator) {
	dispatchers.add(openIdDispatcherChecker11);
	dispatchers.add(openidDispatcherAuthenticationImmediate11);
	dispatchers.add(openidDispatcherAuthenticationSetup11);
	dispatchers.add(openIdDispatcherCheckAuthentication11);
	dispatchers.add(openIdDispatcherAssociation11);
	dispatchers.add(openIdDispatcherTerminator);
    }

}
