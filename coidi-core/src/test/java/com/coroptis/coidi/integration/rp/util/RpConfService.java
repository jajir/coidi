package com.coroptis.coidi.integration.rp.util;

import com.coroptis.coidi.rp.services.RpConfigurationService;
import com.coroptis.coidi.util.AbstractConfService;

public class RpConfService extends AbstractConfService implements RpConfigurationService {

    private String realm;

    private String proxyServer;

    private Integer proxyPort;

    private String proxyUsername;

    private String proxyPassword;

    private String requiredFields;

    private String optionalFields;

    private String policyUrl;

    public RpConfService(String propertyFileName) {
	super(propertyFileName);
	optionalFields = getProp().getProperty("common.extension.registration.optionalFields");
	policyUrl = getProp().getProperty("common.extension.registration.policyUrl");
	proxyPassword = getProp().getProperty("proxy.password");
	proxyPort = Integer.valueOf(getProp().getProperty("proxy.port"));
	proxyServer = getProp().getProperty("proxy.server");
	proxyUsername = getProp().getProperty("proxy.userName");
	realm = getProp().getProperty("openid.realm");
	requiredFields = getProp().getProperty("common.extension.registration.requiredFields");
    }

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
