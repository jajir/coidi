package com.coroptis.coidi.rp.services.impl;

import javax.inject.Inject;

import org.apache.tapestry5.ioc.annotations.Symbol;

import com.coroptis.coidi.rp.services.RpConfigurationService;

/**
 * Allows tapestry to initialize this configuration bean.
 * 
 * @author jan
 *
 */
public class RpConfigurationServiceImpl implements RpConfigurationService {

    @Inject
    @Symbol("openid.realm")
    private String realm;

    @Inject
    @Symbol("proxy.server")
    private String proxyServer;

    @Inject
    @Symbol("proxy.port")
    private Integer proxyPort;

    @Inject
    @Symbol("proxy.userName")
    private String proxyUsername;

    @Inject
    @Symbol("proxy.password")
    private String proxyPassword;

    @Inject
    @Symbol("common.extension.registration.requiredFields")
    private String requiredFields;

    @Inject
    @Symbol("common.extension.registration.optionalFields")
    private String optionalFields;

    @Inject
    @Symbol("common.extension.registration.policyUrl")
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
