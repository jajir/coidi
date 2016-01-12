package com.coroptis.coidi.op.services;

import javax.inject.Named;

import com.coroptis.coidi.op.services.impl.AuthProcAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcNonce;
import com.coroptis.coidi.op.services.impl.AuthProcResponse11;
import com.coroptis.coidi.op.services.impl.AuthProcSign;
import com.coroptis.coidi.op.services.impl.AuthProcStateLessAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentity11;

public class AuthProcCheckIdImmediate11 extends AbstractAuthProc {

    public AuthProcCheckIdImmediate11(

	    final @Named("authProcSign") AuthProcSign authProcSign,
	    final @Named("authProcVerifyIdentity11") AuthProcVerifyIdentity11 authProcVerifyIdentity11,
	    final @Named("authProcAssociation") AuthProcAssociation authProcAssociation,
	    final @Named("authProcStateLessAssociation") AuthProcStateLessAssociation authProcStateLessAssociation,
	    final @Named("authProcNonce") AuthProcNonce authProcNonce,
	    final @Named("authProcResponse11") AuthProcResponse11 authProcResponse11) {

	dispatchers.add(authProcSign);
	dispatchers.add(authProcVerifyIdentity11);
	dispatchers.add(authProcAssociation);
	dispatchers.add(authProcStateLessAssociation);
	dispatchers.add(authProcNonce);
	dispatchers.add(authProcResponse11);

    }

}
