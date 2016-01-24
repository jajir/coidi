package com.coroptis.coidi.op.services;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class AuthProcCheckIdSetup20 extends AbstractAuthProc {

    @Inject
    public AuthProcCheckIdSetup20(

	    final @Named("authProcSreg10") AuthenticationProcessor authProcSreg10,
	    final @Named("authProcSreg11") AuthenticationProcessor authProcSreg11,
	    final @Named("authProcSign") AuthenticationProcessor authProcSign,
	    final @Named("authProcVerifyLoggedUser") AuthenticationProcessor authProcVerifyLoggedUser,
	    final @Named("authProcVerifyIdentitySelect20") AuthenticationProcessor authProcVerifyIdentitySelect20,
	    final @Named("authProcVerifyIdentity20") AuthenticationProcessor authProcVerifyIdentity20,
	    final @Named("authProcAssociation") AuthenticationProcessor authProcAssociation,
	    final @Named("authProcStateLessAssociation") AuthenticationProcessor authProcStateLessAssociation,
	    final @Named("authProcNonce") AuthenticationProcessor authProcNonce,
	    final @Named("authProcResponse20") AuthenticationProcessor authProcResponse20) {

	dispatchers.add(authProcResponse20);
	dispatchers.add(authProcVerifyLoggedUser);
	dispatchers.add(authProcVerifyIdentitySelect20);
	dispatchers.add(authProcVerifyIdentity20);
	dispatchers.add(authProcNonce);
	dispatchers.add(authProcAssociation);
	dispatchers.add(authProcStateLessAssociation);
	dispatchers.add(authProcSreg10);
	dispatchers.add(authProcSreg11);
	dispatchers.add(authProcSign);
    }

}
