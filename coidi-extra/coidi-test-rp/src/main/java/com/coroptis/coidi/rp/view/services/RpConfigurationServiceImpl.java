package com.coroptis.coidi.rp.view.services;

import javax.inject.Inject;
import javax.inject.Named;

import com.coroptis.coidi.rp.services.RpConfigurationService;

public class RpConfigurationServiceImpl implements RpConfigurationService {
	@Inject
	@Named("openid.realm")
	private String realm;

	@Inject
	@Named("proxy.server")
	private String proxyServer;

	@Inject
	@Named("proxy.port")
	private Integer proxyPort;

	@Inject
	@Named("proxy.userName")
	private String proxyUsername;

	@Inject
	@Named("proxy.password")
	private String proxyPassword;

	@Inject
	@Named("common.extension.registration.requiredFields")
	private String requiredFields;

	@Inject
	@Named("common.extension.registration.optionalFields")
	private String optionalFields;

	@Inject
	@Named("common.extension.registration.policyUrl")
	private String policyUrl;

	@Override
	public String getRealm() {
		return realm;
	}

	@Override
	public String getProxyServer() {
		return proxyServer;
	}

	@Override
	public Integer getProxyPort() {
		return proxyPort;
	}

	@Override
	public String getProxyUsername() {
		return proxyUsername;
	}

	@Override
	public String getProxyPassword() {
		return proxyPassword;
	}

	@Override
	public String getRegistrationPolicyUrl() {
		return policyUrl;
	}

	@Override
	public String getRegistrationOptionalFields() {
		return optionalFields;
	}

	@Override
	public String getRegistrationRequiredFields() {
		return requiredFields;
	}
}
