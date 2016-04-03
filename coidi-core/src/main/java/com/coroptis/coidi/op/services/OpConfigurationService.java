package com.coroptis.coidi.op.services;

import com.coroptis.coidi.op.entities.Association.AssociationType;

/**
 * Provide configuration properties.
 * 
 * @author jirout
 *
 */
public interface OpConfigurationService {

    int getAssociationTimeToLiveInSeconds();

    /**
     * Association types are defined in {@link AssociationType}.
     * 
     * @return association type string
     */
    String getDefaultAssociationType();

    /**
     * 
     * @return OpenID provider URL
     */
    String getOpServerUrl();

    /**
     * User identity pattern, for example
     * <code>http://localhost:8080/identity/%identity%</code>
     * 
     * @return identity URL pattern.
     */
    String getOpIdentityPattern();

    /**
     * In OpenID error responses is field contact.
     * 
     * @return error contact
     */
    String getErrorContact();

    /**
     * Older version of OpenID protocol 1.1 could be switched off by this
     * parameter.
     * 
     * @return return <code>true</code> when OpenID 1.1 should be supported
     *         otherwise <code>false</code>
     */
    boolean isOpenId11Enabled();

    /**
     * Realm in OpenID authentication request can contain wild cards '*'. This
     * feature could be turn off for security reasons.
     * 
     * @return return <code>true</code> when wild cards are supported otherwise
     *         <code>false</code>
     */
    boolean isWildcardAllowedInRealm();

    /**
     * When server use HTTP than 'no-encryption' have to be disabled. It could
     * be enabled just when TLS is used.
     * 
     * @return return <code>true</code>
     */
    boolean isNoEncryptionSessionTypeEnabled();
}
