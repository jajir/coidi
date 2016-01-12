package com.coroptis.coidi.op.services;

import javax.inject.Named;

import com.coroptis.coidi.op.services.impl.AuthProcAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcNonce;
import com.coroptis.coidi.op.services.impl.AuthProcResponse20;
import com.coroptis.coidi.op.services.impl.AuthProcSign;
import com.coroptis.coidi.op.services.impl.AuthProcSreg10;
import com.coroptis.coidi.op.services.impl.AuthProcSreg11;
import com.coroptis.coidi.op.services.impl.AuthProcStateLessAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentity20;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentitySelect20;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyLoggedUser;

public class AuthProcCheckIdSetup20 extends AbstractAuthProc {

    public AuthProcCheckIdSetup20(

	    final @Named("authProcSreg10") AuthProcSreg10 authProcSreg10,
	    final @Named("authProcSreg11") AuthProcSreg11 authProcSreg11,
	    final @Named("authProcSign") AuthProcSign authProcSign,
	    final @Named("authProcVerifyLoggedUser") AuthProcVerifyLoggedUser authProcVerifyLoggedUser,
	    final @Named("authProcVerifyIdentitySelect20") AuthProcVerifyIdentitySelect20 authProcVerifyIdentitySelect20,
	    final @Named("authProcVerifyIdentity20") AuthProcVerifyIdentity20 authProcVerifyIdentity20,
	    final @Named("authProcAssociation") AuthProcAssociation authProcAssociation,
	    final @Named("authProcStateLessAssociation") AuthProcStateLessAssociation authProcStateLessAssociation,
	    final @Named("authProcNonce") AuthProcNonce authProcNonce,
	    final @Named("authProcResponse20") AuthProcResponse20 authProcResponse20) {

	dispatchers.add(authProcSreg10);
	dispatchers.add(authProcSreg11);
	dispatchers.add(authProcSign);
	dispatchers.add(authProcVerifyLoggedUser);
	dispatchers.add(authProcVerifyIdentitySelect20);
	dispatchers.add(authProcVerifyIdentity20);
	dispatchers.add(authProcAssociation);
	dispatchers.add(authProcStateLessAssociation);
	dispatchers.add(authProcNonce);
	dispatchers.add(authProcResponse20);
    }

}
