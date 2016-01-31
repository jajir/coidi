package com.coroptis.coidi.op.iocsupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.services.RealmTool;
@Singleton
public class OpConfigurationServiceImpl implements OpConfigurationService {

	@Inject
	@Named(AssociationTool.DEFAULT_TIME_TO_LIVE_IN_SECONDS)
	private Integer timeToLiveInSeconds;

	@Inject
	@Named(AssociationTool.DEFAULT_ASSOCITION_TYPE)
	private String assocTypeStr;

	@Inject
	@Named("op.err.contact")
	private String errorContact;

	@Inject
	@Named(OpenIdRequestProcessor.CONF_OPENID_VERSION_11_ENABLED)
	private Boolean openidVersion11Enabled;

	@Inject
	@Named("op.server")
	private String opServer;

	@Inject
	@Named("op.identity.pattern")
	private String identityPattern;

	@Inject
	@Named(RealmTool.KEY_IS_WILD_CARD_IN_REALM_ENABLED)
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
