package com.coroptis.coidi.rp.services;

/**
 * OpenID relaying party specific configuration.
 * 
 * @author jirout
 *
 */
public interface RpConfigurationService {

    /**
     * Get relaying party realm.
     * 
     * @return relaying party realm
     */
    String getRealm();
    
    String getProxyServer();
    Integer getProxyPort();
    String getProxyUsername();
    String getProxyPassword();
    
    String getRegistrationPolicyUrl();
    String getRegistrationOptionalFields();
    String getRegistrationRequiredFields();
    

}
