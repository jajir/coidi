package com.coroptis.coidi.op.services;

import javax.inject.Inject;

import org.apache.tapestry5.ioc.annotations.Symbol;

import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.services.RealmTool;

public class OpConfigurationServiceImpl implements OpConfigurationService {

    @Inject
    @Symbol(AssociationTool.DEFAULT_TIME_TO_LIVE_IN_SECONDS)
    private Integer timeToLiveInSeconds;

    @Inject
    @Symbol(AssociationTool.DEFAULT_ASSOCITION_TYPE)
    private String assocTypeStr;

    @Inject
    @Symbol("op.err.contact")
    private String errorContact;

    @Inject
    @Symbol(OpenIdRequestProcessor.CONF_OPENID_VERSION_11_ENABLED)
    private Boolean openidVersion11Enabled;

    @Inject
    @Symbol("op.server")
    private String opServer;

    @Inject
    @Symbol("op.identity.pattern")
    private String identityPattern;

    @Inject
    @Symbol(RealmTool.KEY_IS_WILD_CARD_IN_REALM_ENABLED)
    private boolean wildCardEnabled;

    @Override
    public int getAssociationTimeToLiveInSeconds() {
	return timeToLiveInSeconds;
    }

    @Override
    public String getDefaultAssociationType() {
	return assocTypeStr;
    }

    @Override
    public String getOpServerUrl() {
	return opServer;
    }

    @Override
    public String getOpIdentityPattern() {
	return identityPattern;
    }

    @Override
    public String getErrorContact() {
	return errorContact;
    }

    @Override
    public boolean isOpenId11Enabled() {
	return openidVersion11Enabled;
    }

    @Override
    public boolean isWildcardAllowedInRealm() {
	return wildCardEnabled;
    }

}
