package com.coroptis.coidi.op.services;

import javax.inject.Inject;
import javax.inject.Named;

public class AuthProcCheckIdImmediate11 extends AbstractAuthProc {

	@Inject
    public AuthProcCheckIdImmediate11(

	    final @Named("authProcSign") AuthenticationProcessor authProcSign,
	    final @Named("authProcVerifyIdentity11") AuthenticationProcessor authProcVerifyIdentity11,
	    final @Named("authProcAssociation") AuthenticationProcessor authProcAssociation,
	    final @Named("authProcStateLessAssociation") AuthenticationProcessor authProcStateLessAssociation,
	    final @Named("authProcNonce") AuthenticationProcessor authProcNonce,
	    final @Named("authProcResponse11") AuthenticationProcessor authProcResponse11) {

	dispatchers.add(authProcSign);
	dispatchers.add(authProcVerifyIdentity11);
	dispatchers.add(authProcAssociation);
	dispatchers.add(authProcStateLessAssociation);
	dispatchers.add(authProcNonce);
	dispatchers.add(authProcResponse11);

    }

}
