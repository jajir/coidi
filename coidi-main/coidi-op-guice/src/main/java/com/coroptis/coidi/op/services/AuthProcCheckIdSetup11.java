package com.coroptis.coidi.op.services;

import javax.inject.Inject;
import javax.inject.Named;

public class AuthProcCheckIdSetup11 extends AbstractAuthProc {

    @Inject
    public AuthProcCheckIdSetup11(

	    final @Named("authProcSign") AuthenticationProcessor authProcSign,
	    final @Named("authProcAssociation") AuthenticationProcessor authProcAssociation,
	    final @Named("authProcStateLessAssociation") AuthenticationProcessor authProcStateLessAssociation,
	    final @Named("authProcVerifyIdentity11") AuthenticationProcessor authProcVerifyIdentity11,
	    final @Named("authProcVerifyLoggedUser") AuthenticationProcessor authProcVerifyLoggedUser,
	    final @Named("authProcNonce") AuthenticationProcessor authProcNonce,
	    final @Named("authProcResponse11") AuthenticationProcessor authProcResponse11) {

	dispatchers.add(authProcSign);
	dispatchers.add(authProcAssociation);
	dispatchers.add(authProcStateLessAssociation);
	dispatchers.add(authProcVerifyIdentity11);
	dispatchers.add(authProcVerifyLoggedUser);
	dispatchers.add(authProcNonce);
	dispatchers.add(authProcResponse11);
    }
}
