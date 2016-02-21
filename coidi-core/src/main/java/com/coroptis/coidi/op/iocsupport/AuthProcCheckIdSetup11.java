package com.coroptis.coidi.op.iocsupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.coroptis.coidi.core.util.AbstractAuthProc;
import com.coroptis.coidi.op.services.AuthProc;
@Singleton
public class AuthProcCheckIdSetup11 extends AbstractAuthProc {

    @Inject
    public AuthProcCheckIdSetup11(

	    final @Named(AUTH_PROC_SIGN) AuthProc authProcSign,
	    final @Named(AUTH_PROC_ASSOCIATION) AuthProc authProcAssociation,
	    final @Named(AUTH_PROC_STATE_LESS_ASSOCIATION) AuthProc authProcStateLessAssociation,
	    final @Named(AUTH_PROC_VERIFY_IDENTITY_11) AuthProc authProcVerifyIdentity11,
	    final @Named(AUTH_PROC_VERIFY_LOGGED_USER_20) AuthProc authProcVerifyLoggedUser,
	    final @Named(AUTH_PROC_NONCE) AuthProc authProcNonce,
	    final @Named(AUTH_PROC_VERIFY_LOGGED_USER_11) AuthProc authProcVerifyLoggedUser11) {

	dispatchers.add(authProcSign);
	dispatchers.add(authProcAssociation);
	dispatchers.add(authProcStateLessAssociation);
	dispatchers.add(authProcVerifyIdentity11);
	dispatchers.add(authProcVerifyLoggedUser);
	dispatchers.add(authProcNonce);
	dispatchers.add(authProcVerifyLoggedUser11);
    }
}
