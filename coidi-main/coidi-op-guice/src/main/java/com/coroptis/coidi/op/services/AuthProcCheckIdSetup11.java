package com.coroptis.coidi.op.services;

import javax.inject.Inject;
import javax.inject.Named;

import com.coroptis.coidi.op.services.impl.AuthProcAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcNonce;
import com.coroptis.coidi.op.services.impl.AuthProcResponse11;
import com.coroptis.coidi.op.services.impl.AuthProcSign;
import com.coroptis.coidi.op.services.impl.AuthProcStateLessAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentity11;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyLoggedUser;

public class AuthProcCheckIdSetup11 extends AbstractAuthProc {

    @Inject
    public AuthProcCheckIdSetup11(

	    final @Named("authProcSign") AuthProcSign authProcSign,
	    final @Named("authProcAssociation") AuthProcAssociation authProcAssociation,
	    final @Named("authProcStateLessAssociation") AuthProcStateLessAssociation authProcStateLessAssociation,
	    final @Named("authProcVerifyIdentity11") AuthProcVerifyIdentity11 authProcVerifyIdentity11,
	    final @Named("authProcVerifyLoggedUser") AuthProcVerifyLoggedUser authProcVerifyLoggedUser,
	    final @Named("authProcNonce") AuthProcNonce authProcNonce,
	    final @Named("authProcResponse11") AuthProcResponse11 authProcResponse11) {

	dispatchers.add(authProcSign);
	dispatchers.add(authProcAssociation);
	dispatchers.add(authProcStateLessAssociation);
	dispatchers.add(authProcVerifyIdentity11);
	dispatchers.add(authProcVerifyLoggedUser);
	dispatchers.add(authProcNonce);
	dispatchers.add(authProcResponse11);
    }
}
