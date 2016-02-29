package com.coroptis.coidi.integration.op.util;

import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.services.RealmTool;
import com.coroptis.coidi.util.AbstractConfService;

public class OpConfServiceImpl extends AbstractConfService implements OpConfigurationService {

    private Integer timeToLiveInSeconds;

    private String assocTypeStr;

    private String errorContact;

    private Boolean openidVersion11Enabled;

    private String opServer;

    private String identityPattern;

    private boolean wildCardEnabled;

    public OpConfServiceImpl(final String propertyFileName) {
	super(propertyFileName);
	assocTypeStr = getProp().getProperty(AssociationTool.DEFAULT_ASSOCITION_TYPE);
	errorContact = getProp().getProperty("op.err.contact");
	identityPattern = getProp().getProperty("op.identity.pattern");
	openidVersion11Enabled = Boolean.valueOf(
		getProp().getProperty(OpenIdRequestProcessor.CONF_OPENID_VERSION_11_ENABLED));
	opServer = getProp().getProperty("op.server");
	timeToLiveInSeconds = Integer
		.valueOf(getProp().getProperty(AssociationTool.DEFAULT_TIME_TO_LIVE_IN_SECONDS));
	wildCardEnabled = Boolean
		.valueOf(getProp().getProperty(RealmTool.KEY_IS_WILD_CARD_IN_REALM_ENABLED));
    }

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
