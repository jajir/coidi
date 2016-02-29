package com.coroptis.coidi.op.iocsupport;

import com.coroptis.coidi.op.services.AuthProc;
import com.coroptis.coidi.op.services.UserVerifier;
import com.coroptis.coidi.op.services.impl.AuthProcAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcAx10;
import com.coroptis.coidi.op.services.impl.AuthProcIdentity11;
import com.coroptis.coidi.op.services.impl.AuthProcIdentity20;
import com.coroptis.coidi.op.services.impl.AuthProcNonce;
import com.coroptis.coidi.op.services.impl.AuthProcSign;
import com.coroptis.coidi.op.services.impl.AuthProcSreg10;
import com.coroptis.coidi.op.services.impl.AuthProcSreg11;
import com.coroptis.coidi.op.services.impl.AuthProcStateLessAssociation;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentity11;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyIdentity20;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyLoggedUser11Immediate;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyLoggedUser11Setup;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyLoggedUser20Immediate;
import com.coroptis.coidi.op.services.impl.AuthProcVerifyLoggedUser20Setup;

/**
 * Last part of coidi OP. Here are defined authentication processors.
 * 
 * @author jan
 *
 */
public abstract class OpBinding extends OpOpenIdDispatcherChainBinding {

    private final static String AUTH_PROC_OPEN_ID_20_CHECKID_SETUP = "authProcOpenId20CheckIdSetup";
    private final static String AUTH_PROC_OPEN_ID_20_CHECKID_IMMEDIATE = "authProcOpenId20CheckIdImmediate";
    private final static String AUTH_PROC_OPEN_ID_11_CHECKID_SETUP = "authProcOpenId11CheckIdSetup";
    private final static String AUTH_PROC_OPEN_ID_11_CHECKID_IMMEDIATE = "authProcOpenId11CheckIdImmediate";

    private final static String AUTH_PROC_ASSOCIATION = "authProcAssociation";

    private final static String AUTH_PROC_AX_10 = "authProcAx10";

    private final static String AUTH_PROC_IDENTITY_11 = "authProcIdentity11";

    private final static String AUTH_PROC_IDENTITY_20 = "authProcIdentity20";

    private final static String AUTH_PROC_NONCE = "authProcNonce";

    private final static String AUTH_PROC_SIGN = "authProcSign";

    private final static String AUTH_PROC_SREG_10 = "authProcSreg10";

    private final static String AUTH_PROC_SREG_11 = "authProcSreg11";

    private final static String AUTH_PROC_STATE_LESS_ASSOCIATION = "authProcStateLessAssociation";

    private final static String AUTH_PROC_VERIFY_IDENTITY_11 = "authProcVerifyIdentity11";

    private final static String AUTH_PROC_VERIFY_IDENTITY_20 = "authProcVerifyIdentity20";

    private final static String AUTH_PROC_VERIFY_LOGGED_USER_11_IMMEDIATE = "authProcVerifyLoggedUser11Immediate";

    private final static String AUTH_PROC_VERIFY_LOGGED_USER_11_SETUP = "authProcVerifyLoggedUser11Setup";

    private final static String AUTH_PROC_VERIFY_LOGGED_USER_20_IMMEDIATE = "authProcVerifyLoggedUser20Immediate";

    private final static String AUTH_PROC_VERIFY_LOGGED_USER_20_SETUP = "authProcVerifyLoggedUser20Setup";

    public abstract UserVerifier getUserVerifier();

    @Override
    public AuthProc getAuthProcOpenId20CheckIdSetup() {
	AuthProc out = get(AUTH_PROC_OPEN_ID_20_CHECKID_SETUP);
	if (out == null) {
	    AuthProcChain chain = new AuthProcChain();
	    chain.add(getAuthProcVerifyLoggedUser20Setup());
	    chain.add(getAuthProcVerifyIdentity20());
	    chain.add(getAuthProcIdentity20());
	    chain.add(getAuthProcNonce());
	    chain.add(getAuthProcAssociation());
	    chain.add(getAuthProcStateLessAssociation());
	    // chain.add(getAuthProcSreg10());
	    // chain.add(getAuthProcSreg11());
	    chain.add(getAuthProcSign());
	    put(AUTH_PROC_OPEN_ID_20_CHECKID_SETUP, chain);
	    out = chain;
	}
	return out;
    }

    @Override
    public AuthProc getAuthProcOpenId20CheckIdImmediate() {
	AuthProc out = get(AUTH_PROC_OPEN_ID_20_CHECKID_IMMEDIATE);
	if (out == null) {
	    AuthProcChain chain = new AuthProcChain();
	    chain.add(getAuthProcVerifyLoggedUser20Immediate());
	    chain.add(getAuthProcVerifyIdentity20());
	    chain.add(getAuthProcIdentity20());
	    chain.add(getAuthProcNonce());
	    chain.add(getAuthProcAssociation());
	    chain.add(getAuthProcStateLessAssociation());
	    // chain.add(getAuthProcSreg10());
	    // chain.add(getAuthProcSreg11());
	    chain.add(getAuthProcSign());
	    put(AUTH_PROC_OPEN_ID_20_CHECKID_IMMEDIATE, chain);
	    out = chain;
	}
	return out;
    }

    @Override
    public AuthProc getAuthProcOpenId11CheckIdSetup() {
	AuthProc out = get(AUTH_PROC_OPEN_ID_11_CHECKID_SETUP);
	if (out == null) {
	    AuthProcChain chain = new AuthProcChain();
	    chain.add(getAuthProcVerifyLoggedUser11Setup());
	    chain.add(getAuthProcVerifyIdentity11());
	    chain.add(getAuthProcIdentity11());
	    chain.add(getAuthProcAssociation());
	    chain.add(getAuthProcSign());
	    chain.add(getAuthProcStateLessAssociation());
	    chain.add(getAuthProcNonce());
	    put(AUTH_PROC_OPEN_ID_11_CHECKID_SETUP, chain);
	    out = chain;
	}
	return out;
    }

    @Override
    public AuthProc getAuthProcOpenId11CheckIdImmediate() {
	AuthProc out = get(AUTH_PROC_OPEN_ID_11_CHECKID_IMMEDIATE);
	if (out == null) {
	    AuthProcChain chain = new AuthProcChain();
	    chain.add(getAuthProcVerifyLoggedUser11Immediate());
	    chain.add(getAuthProcVerifyIdentity11());
	    chain.add(getAuthProcNonce());
	    chain.add(getAuthProcAssociation());
	    chain.add(getAuthProcStateLessAssociation());
	    chain.add(getAuthProcSign());
	    put(AUTH_PROC_OPEN_ID_11_CHECKID_IMMEDIATE, chain);
	    out = chain;
	}
	return out;
    }

    public AuthProc getAuthProcAssociation() {
	AuthProc out = get(AUTH_PROC_ASSOCIATION);
	if (out == null) {
	    out = new AuthProcAssociation(getAssociationService());
	    put(AUTH_PROC_ASSOCIATION, out);
	}
	return out;
    }

    public AuthProc getAuthProcAx10() {
	AuthProc out = get(AUTH_PROC_AX_10);
	if (out == null) {
	    out = new AuthProcAx10(getAuthenticationService());
	    put(AUTH_PROC_AX_10, out);
	}
	return out;
    }

    public AuthProc getAuthProcIdentity11() {
	AuthProc out = get(AUTH_PROC_IDENTITY_11);
	if (out == null) {
	    out = new AuthProcIdentity11(getNegativeResponseGenerator());
	    put(AUTH_PROC_IDENTITY_11, out);
	}
	return out;
    }

    public AuthProc getAuthProcIdentity20() {
	AuthProc out = get(AUTH_PROC_IDENTITY_20);
	if (out == null) {
	    out = new AuthProcIdentity20(getConf(), getUserVerifier(),
		    getNegativeResponseGenerator());
	    put(AUTH_PROC_IDENTITY_20, out);
	}
	return out;
    }

    public AuthProc getAuthProcNonce() {
	AuthProc out = get(AUTH_PROC_NONCE);
	if (out == null) {
	    out = new AuthProcNonce(getNonceService());
	    put(AUTH_PROC_NONCE, out);
	}
	return out;
    }

    public AuthProc getAuthProcSign() {
	AuthProc out = get(AUTH_PROC_SIGN);
	if (out == null) {
	    out = new AuthProcSign(getBaseAssociationDao(), getSigningService());
	    put(AUTH_PROC_SIGN, out);
	}
	return out;
    }

    public AuthProc getAuthProcSreg10() {
	AuthProc out = get(AUTH_PROC_SREG_10);
	if (out == null) {
	    out = new AuthProcSreg10(getSregService(), getIdentityService(),
		    getNegativeResponseGenerator());
	    put(AUTH_PROC_SREG_10, out);
	}
	return out;
    }

    public AuthProc getAuthProcSreg11() {
	AuthProc out = get(AUTH_PROC_SREG_11);
	if (out == null) {
	    out = new AuthProcSreg11(getIdentityService(), getSregService(),
		    getNegativeResponseGenerator());
	    put(AUTH_PROC_SREG_11, out);
	}
	return out;
    }

    public AuthProc getAuthProcStateLessAssociation() {
	AuthProc out = get(AUTH_PROC_STATE_LESS_ASSOCIATION);
	if (out == null) {
	    out = new AuthProcStateLessAssociation(getAssociationService(), getBaseAssociationDao(),
		    getBaseNonceDao());
	    put(AUTH_PROC_STATE_LESS_ASSOCIATION, out);
	}
	return out;
    }

    public AuthProc getAuthProcVerifyIdentity11() {
	AuthProc out = get(AUTH_PROC_VERIFY_IDENTITY_11);
	if (out == null) {
	    out = new AuthProcVerifyIdentity11(getNegativeResponseGenerator(), getUserVerifier());
	    put(AUTH_PROC_VERIFY_IDENTITY_11, out);
	}
	return out;
    }

    public AuthProc getAuthProcVerifyIdentity20() {
	AuthProc out = get(AUTH_PROC_VERIFY_IDENTITY_20);
	if (out == null) {
	    out = new AuthProcVerifyIdentity20(getNegativeResponseGenerator(), getUserVerifier());
	    put(AUTH_PROC_VERIFY_IDENTITY_20, out);
	}
	return out;
    }

    public AuthProc getAuthProcVerifyLoggedUser11Immediate() {
	AuthProc out = get(AUTH_PROC_VERIFY_LOGGED_USER_11_IMMEDIATE);
	if (out == null) {
	    out = new AuthProcVerifyLoggedUser11Immediate(getUserVerifier());
	    put(AUTH_PROC_VERIFY_LOGGED_USER_11_IMMEDIATE, out);
	}
	return out;
    }

    public AuthProc getAuthProcVerifyLoggedUser11Setup() {
	AuthProc out = get(AUTH_PROC_VERIFY_LOGGED_USER_11_SETUP);
	if (out == null) {
	    out = new AuthProcVerifyLoggedUser11Setup(getNegativeResponseGenerator(),
		    getUserVerifier());
	    put(AUTH_PROC_VERIFY_LOGGED_USER_11_SETUP, out);
	}
	return out;
    }

    public AuthProc getAuthProcVerifyLoggedUser20Immediate() {
	AuthProc out = get(AUTH_PROC_VERIFY_LOGGED_USER_20_IMMEDIATE);
	if (out == null) {
	    out = new AuthProcVerifyLoggedUser20Immediate(getUserVerifier());
	    put(AUTH_PROC_VERIFY_LOGGED_USER_20_IMMEDIATE, out);
	}
	return out;
    }

    public AuthProc getAuthProcVerifyLoggedUser20Setup() {
	AuthProc out = get(AUTH_PROC_VERIFY_LOGGED_USER_20_SETUP);
	if (out == null) {
	    out = new AuthProcVerifyLoggedUser20Setup(getNegativeResponseGenerator(),
		    getUserVerifier());
	    put(AUTH_PROC_VERIFY_LOGGED_USER_20_SETUP, out);
	}
	return out;
    }

}
