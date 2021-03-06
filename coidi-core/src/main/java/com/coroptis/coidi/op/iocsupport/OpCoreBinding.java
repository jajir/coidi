package com.coroptis.coidi.op.iocsupport;

import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.util.CoreBinding;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.dao.BaseIdentityDao;
import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.services.AssociationProcessor;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.CryptoService;
import com.coroptis.coidi.op.services.IdentityNamesConvertor;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.services.OpenIdRequestTool;
import com.coroptis.coidi.op.services.RealmTool;
import com.coroptis.coidi.op.services.SregService;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.coroptis.coidi.op.services.impl.AssociationProcessorImpl;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.services.impl.AssociationToolImpl;
import com.coroptis.coidi.op.services.impl.AuthenticationServiceImpl;
import com.coroptis.coidi.op.services.impl.CryptoServiceImpl;
import com.coroptis.coidi.op.services.impl.IdentityNamesConvertorImpl;
import com.coroptis.coidi.op.services.impl.IdentityServiceImpl;
import com.coroptis.coidi.op.services.impl.NegativeResponseGeneratorImpl;
import com.coroptis.coidi.op.services.impl.OpenIdRequestProcessorImpl;
import com.coroptis.coidi.op.services.impl.OpenIdRequestToolImpl;
import com.coroptis.coidi.op.services.impl.RealmToolImpl;
import com.coroptis.coidi.op.services.impl.SregServiceImpl;
import com.coroptis.coidi.op.services.impl.StatelessModeNonceServiceImpl;

/**
 * Provide bindings for coidi OP. All bindings could be rewritten or overridden
 * in OP implementing application.
 * 
 * @author jiroutj
 *
 */
public abstract class OpCoreBinding extends CoreBinding {

    public abstract BaseNonceDao getBaseNonceDao();

    public abstract BaseAssociationDao getBaseAssociationDao();

    public abstract BaseIdentityDao getBaseIdentityDao();

    public abstract OpenIdDispatcher getOpenIdDispatcher20();

    public abstract OpenIdDispatcher getOpenIdDispatcher11();

    /**
     * @return OP configuration
     */
    public abstract OpConfigurationService getConf();

    /**
     * @return {@link AssociationService}
     */
    public AssociationService getAssociationService() {
	AssociationService out = get(AssociationService.class);
	if (out == null) {
	    out = new AssociationServiceImpl(getBaseAssociationDao(), getCryptoService(),
		    getAssociationTool(), getConvertorService());
	    put(AssociationService.class, out);
	}
	return out;
    }

    /**
     * @return {@link IdentityService}
     */
    public IdentityService getIdentityService() {
	IdentityService out = get(IdentityService.class);
	if (out == null) {
	    out = new IdentityServiceImpl(getBaseIdentityDao(), getIdentityNamesConvertor());
	    put(IdentityService.class, out);
	}
	return out;
    }

    /**
     * @return {@link AssociationTool}
     */
    public AssociationTool getAssociationTool() {
	AssociationTool out = get(AssociationTool.class);
	if (out == null) {
	    out = new AssociationToolImpl(getConf());
	    put(AssociationTool.class, out);
	}
	return out;
    }

    /**
     * @return {@link CryptoService}
     */
    public CryptoService getCryptoService() {
	CryptoService out = get(CryptoService.class);
	if (out == null) {
	    out = new CryptoServiceImpl();
	    put(CryptoService.class, out);
	}
	return out;
    }

    /**
     * @return {@link ConvertorService}
     */
    public AuthenticationService getAuthenticationService() {
	AuthenticationService out = get(AuthenticationService.class);
	if (out == null) {
	    out = new AuthenticationServiceImpl();
	    put(AuthenticationService.class, out);
	}
	return out;
    }

    /**
     * @return {@link ConvertorService}
     */
    public StatelessModeNonceService getStatelessModeNonceService() {
	StatelessModeNonceService out = get(StatelessModeNonceService.class);
	if (out == null) {
	    out = new StatelessModeNonceServiceImpl(getBaseNonceDao(), getAssociationTool(),
		    getBaseAssociationDao(), getNonceTool());
	    put(StatelessModeNonceService.class, out);
	}
	return out;
    }

    /**
     * @return {@link SregService}
     */
    public SregService getSregService() {
	SregService out = get(SregService.class);
	if (out == null) {
	    out = new SregServiceImpl();
	    put(SregService.class, out);
	}
	return out;
    }

    /**
     * @return {@link RealmTool}
     */
    public RealmTool getRealmTool() {
	RealmTool out = get(RealmTool.class);
	if (out == null) {
	    out = new RealmToolImpl(getConf(), getOpenIdRequestTool());
	    put(RealmTool.class, out);
	}
	return out;
    }

    /**
     * @return {@link NegativeResponseGenerator}
     */
    public NegativeResponseGenerator getNegativeResponseGenerator() {
	NegativeResponseGenerator out = get(NegativeResponseGenerator.class);
	if (out == null) {
	    out = new NegativeResponseGeneratorImpl(getConf());
	    put(NegativeResponseGenerator.class, out);
	}
	return out;
    }

    /**
     * @return {@link IdentityNamesConvertor}
     */
    public IdentityNamesConvertor getIdentityNamesConvertor() {
	IdentityNamesConvertor out = get(IdentityNamesConvertor.class);
	if (out == null) {
	    out = new IdentityNamesConvertorImpl(getConf());
	    put(IdentityNamesConvertor.class, out);
	}
	return out;
    }

    /**
     * @return {@link OpenIdRequestProcessor}
     */
    public OpenIdRequestProcessor getOpenIdRequestProcessor() {
	OpenIdRequestProcessor out = get(OpenIdRequestProcessor.class);
	if (out == null) {
	    out = new OpenIdRequestProcessorImpl(getConf(), getOpenIdDispatcher11(),
		    getOpenIdDispatcher20(), getOpenIdRequestTool());
	    put(OpenIdRequestProcessor.class, out);
	}
	return out;
    }

    /**
     * @return {@link AssociationProcessor}
     */
    public AssociationProcessor getAssociationProcessor() {
	AssociationProcessor out = get(AssociationProcessor.class);
	if (out == null) {
	    out = new AssociationProcessorImpl(getBaseAssociationDao(), getCryptoSessionService(),
		    getCryptoService(), getAssociationService(), getConvertorService());
	    put(AssociationProcessor.class, out);
	}
	return out;
    }

    /**
     * @return {@link ConvertorService}
     */
    public OpenIdRequestTool getOpenIdRequestTool() {
	OpenIdRequestTool out = get(OpenIdRequestTool.class);
	if (out == null) {
	    out = new OpenIdRequestToolImpl();
	    put(OpenIdRequestTool.class, out);
	}
	return out;
    }

}
