package com.coroptis.coidi.op.iocsupport;

import com.coroptis.coidi.op.services.AuthProc;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationImmediate11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationImmediate20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationSetup11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAuthenticationSetup20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherCheckAuthentication20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherChecker11;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherChecker20;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherTerminator;

public abstract class OpOpenIdDispatcherChainBinding extends OpCoreBinding {

    private final static String OPEN_ID_DISPATCHER_20 = "openIdDispatcher20";

    private final static String OPEN_ID_DISPATCHER_11 = "openIdDispatcher11";

    private final static String OPEN_ID_DISPATCHER_CHECKER_11 = "openIdDispatcherChecker11";
    private final static String OPEN_ID_DISPATCHER_AUTHENTICATION_IMMEDIATE_11 = "openIdDispatcherAuthenticationImmediate11";
    private final static String OPEN_ID_DISPATCHER_AUTHENTICATION_SETUP_11 = "openIdDispatcherAuthenticationSetup11";
    private final static String OPEN_ID_DISPATCHER_CHECK_AUTHENTICATION_11 = "openIdDispatcherCheckAuthentication11";
    private final static String OPEN_ID_DISPATCHER_ASSOCIATION_11 = "openIdDispatcherAssociation11";
    private final static String OPEN_ID_DISPATCHER_TERMINATOR = "openIdDispatcherTerminator";
    private final static String OPEN_ID_DISPATCHER_CHECKER_20 = "openIdDispatcherChecker20";
    private final static String OPEN_ID_DISPATCHER_AUTHENTICATION_IMMEDIATE_20 = "openIdDispatcherAuthenticationImmediate20";
    private final static String OPEN_ID_DISPATCHER_AUTHENTICATION_SETUP_20 = "openIdDispatcherAuthenticationSetup20";
    private final static String OPEN_ID_DISPATCHER_CHECK_AUTHENTICATION_20 = "openIdDispatcherCheckAuthentication20";
    private final static String OPEN_ID_DISPATCHER_ASSOCIATION_20 = "openIdDispatcherAssociation20";

    public abstract AuthProc getAuthProcOpenId20CheckIdSetup();

    public abstract AuthProc getAuthProcOpenId20CheckIdImmediate();

    public abstract AuthProc getAuthProcOpenId11CheckIdSetup();

    public abstract AuthProc getAuthProcOpenId11CheckIdImmediate();

    @Override
    public OpenIdDispatcher getOpenIdDispatcher20() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_20);
	if (out == null) {
	    OpenIdDispatcherChain chain = new OpenIdDispatcherChain();
	    chain.add(getOpenIdDispatcherChecker20());
	    chain.add(getOpenIdDispatcherAuthenticationImmediate20());
	    chain.add(getOpenIdDispatcherAuthenticationSetup20());
	    chain.add(getOpenIdDispatcherCheckAuthentication20());
	    chain.add(getOpenIdDispatcherAssociation20());
	    chain.add(getOpenIdDispatcherTerminator());
	    put(OPEN_ID_DISPATCHER_20, chain);
	    out = chain;
	}
	return out;
    }

    @Override
    public OpenIdDispatcher getOpenIdDispatcher11() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_11);
	if (out == null) {
	    OpenIdDispatcherChain chain = new OpenIdDispatcherChain();
	    chain.add(getOpenIdDispatcherChecker11());
	    chain.add(getOpenIdDispatcherAuthenticationImmediate11());
	    chain.add(getOpenIdDispatcherAuthenticationSetup11());
	    chain.add(getOpenIdDispatcherCheckAuthentication11());
	    chain.add(getOpenIdDispatcherAssociation11());
	    chain.add(getOpenIdDispatcherTerminator());
	    put(OPEN_ID_DISPATCHER_11, chain);
	    out = chain;
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherChecker11() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_CHECKER_11);
	if (out == null) {
	    out = new OpenIdDispatcherChecker11(getNegativeResponseGenerator());
	    put(OPEN_ID_DISPATCHER_CHECKER_11, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherAuthenticationImmediate11() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_AUTHENTICATION_IMMEDIATE_11);
	if (out == null) {
	    out = new OpenIdDispatcherAuthenticationImmediate11(
		    getAuthProcOpenId11CheckIdImmediate());
	    put(OPEN_ID_DISPATCHER_AUTHENTICATION_IMMEDIATE_11, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherAuthenticationSetup11() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_AUTHENTICATION_SETUP_11);
	if (out == null) {
	    out = new OpenIdDispatcherAuthenticationSetup11(getAuthProcOpenId11CheckIdSetup());
	    put(OPEN_ID_DISPATCHER_AUTHENTICATION_SETUP_11, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherCheckAuthentication11() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_CHECK_AUTHENTICATION_11);
	if (out == null) {
	    out = new OpenIdDispatcherCheckAuthentication11(getAssociationTool(),
		    getAssociationService(), getBaseAssociationDao());
	    put(OPEN_ID_DISPATCHER_CHECK_AUTHENTICATION_11, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherAssociation11() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_ASSOCIATION_11);
	if (out == null) {
	    out = new OpenIdDispatcherAssociation11(getNegativeResponseGenerator(),
		    getAssociationProcessor());
	    put(OPEN_ID_DISPATCHER_ASSOCIATION_11, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherChecker20() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_CHECKER_20);
	if (out == null) {
	    out = new OpenIdDispatcherChecker20(getNegativeResponseGenerator());
	    put(OPEN_ID_DISPATCHER_CHECKER_20, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherAuthenticationImmediate20() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_AUTHENTICATION_IMMEDIATE_20);
	if (out == null) {
	    out = new OpenIdDispatcherAuthenticationImmediate20(
		    getAuthProcOpenId20CheckIdImmediate());
	    put(OPEN_ID_DISPATCHER_AUTHENTICATION_IMMEDIATE_20, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherAuthenticationSetup20() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_AUTHENTICATION_SETUP_20);
	if (out == null) {
	    out = new OpenIdDispatcherAuthenticationSetup20(getAuthProcOpenId20CheckIdSetup());
	    put(OPEN_ID_DISPATCHER_AUTHENTICATION_SETUP_20, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherCheckAuthentication20() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_CHECK_AUTHENTICATION_20);
	if (out == null) {
	    out = new OpenIdDispatcherCheckAuthentication20(getStatelessModeNonceService(),
		    getSigningService(), getAssociationService());
	    put(OPEN_ID_DISPATCHER_CHECK_AUTHENTICATION_20, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherAssociation20() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_ASSOCIATION_20);
	if (out == null) {
	    out = new OpenIdDispatcherAssociation20(getNegativeResponseGenerator(),
		    getAssociationProcessor());
	    put(OPEN_ID_DISPATCHER_ASSOCIATION_20, out);
	}
	return out;
    }

    public OpenIdDispatcher getOpenIdDispatcherTerminator() {
	OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_TERMINATOR);
	if (out == null) {
	    out = new OpenIdDispatcherTerminator(getNegativeResponseGenerator());
	    put(OPEN_ID_DISPATCHER_TERMINATOR, out);
	}
	return out;
    }

}
