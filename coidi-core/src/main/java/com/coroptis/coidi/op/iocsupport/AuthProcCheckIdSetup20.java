package com.coroptis.coidi.op.iocsupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.coroptis.coidi.core.util.AbstractAuthProc;
import com.coroptis.coidi.op.services.AuthProc;

@Singleton
public class AuthProcCheckIdSetup20 extends AbstractAuthProc {

    @Inject
    public AuthProcCheckIdSetup20(

	    final @Named(AUTH_PROC_SREG_10) AuthProc authProcSreg10,
	    final @Named(AUTH_PROC_SREG_11) AuthProc authProcSreg11,
	    final @Named(AUTH_PROC_SIGN) AuthProc authProcSign,
	    final @Named(AUTH_PROC_VERIFY_LOGGED_USER_20) AuthProc authProcVerifyLoggedUser20,
	    final @Named(AUTH_PROC_VERIFY_IDENTITY_20) AuthProc authProcVerifyIdentity20,
	    final @Named(AUTH_PROC_ASSOCIATION) AuthProc authProcAssociation,
	    final @Named(AUTH_PROC_STATE_LESS_ASSOCIATION) AuthProc authProcStateLessAssociation,
	    final @Named(AUTH_PROC_NONCE) AuthProc authProcNonce,
	    final @Named(AUTH_PROC_IDENTITY_20) AuthProc authProcIdentity20) {

	dispatchers.add(authProcVerifyLoggedUser20);
	dispatchers.add(authProcVerifyIdentity20);
	dispatchers.add(authProcIdentity20);
	dispatchers.add(authProcNonce);
	dispatchers.add(authProcAssociation);
	dispatchers.add(authProcStateLessAssociation);
	dispatchers.add(authProcSreg10);
	dispatchers.add(authProcSreg11);
	dispatchers.add(authProcSign);
    }

}
