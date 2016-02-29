package com.coroptis.coidi.op.iocsupport;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.services.RealmTool;
import com.google.common.base.Preconditions;

public class OpConfServiceImpl implements OpConfigurationService {

    private Integer timeToLiveInSeconds;

    private String assocTypeStr;

    private String errorContact;

    private Boolean openidVersion11Enabled;

    private String opServer;

    private String identityPattern;

    private boolean wildCardEnabled;

    public OpConfServiceImpl(final String propertyFileName) {
	Preconditions.checkNotNull(propertyFileName);
	Properties prop = new Properties();
	try {
	    prop.load(new BufferedInputStream(OpConfigurationServiceImpl.class.getClassLoader()
		    .getResourceAsStream(propertyFileName)));
	} catch (IOException e) {
	    throw new CoidiException(e.getMessage(), e);
	}
	assocTypeStr = prop.getProperty(AssociationTool.DEFAULT_ASSOCITION_TYPE);
	errorContact = prop.getProperty("op.err.contact");
	identityPattern = prop.getProperty("op.identity.pattern");
	openidVersion11Enabled = Boolean
		.valueOf(prop.getProperty(OpenIdRequestProcessor.CONF_OPENID_VERSION_11_ENABLED));
	opServer = prop.getProperty("op.server");
	timeToLiveInSeconds = Integer
		.valueOf(prop.getProperty(AssociationTool.DEFAULT_TIME_TO_LIVE_IN_SECONDS));
	wildCardEnabled = Boolean
		.valueOf(prop.getProperty(RealmTool.KEY_IS_WILD_CARD_IN_REALM_ENABLED));
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
