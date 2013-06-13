package com.coroptis.coidi.op.services;

import java.util.Set;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Identity;

/**
 * Service helps process OpenID simple registration extension.
 * 
 * @author jirout
 * 
 */
public interface SregService {

    static final String SREG_NICKNAME = "sreg.nickname";
    static final String SREG_EMAIL = "sreg.email";
    static final String SREG_FULLNAME = "sreg.fullname";
    static final String SREG_DOB = "sreg.dob";
    static final String SREG_GENDRE = "sreg.gendre";
    static final String SREG_POSTCODE = "sreg.postcode";
    static final String SREG_COUNTRY = "sreg.country";
    static final String SREG_LANGUAGE = "sreg.language";
    static final String SREG_TIMEZONE = "sreg.timezone";

    /**
     * Define format in which is date of birth (DOB) formatted.
     */
    static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Detect if authentication request contains sreg extension 1.0.
     * 
     * @param request
     *            required authentication request
     * @return <code>true</code> when request contains SREG 1.0 otherwise return
     *         <code>false</code>.
     */
    boolean isSreg10(AuthenticationRequest request);

    /**
     * Detect if authentication request contains sreg extension 1.1.
     * 
     * @param request
     *            required authentication request
     * @return <code>true</code> when request contains SREG 1.0 otherwise return
     *         <code>false</code>.
     */
    boolean isSreg11(AuthenticationRequest request);

    /**
     * When SREG extension 1.1 is detected than keys are extracted and returned.
     * 
     * @param authenticationRequest
     *            required {@link AuthenticationRequest}
     * @return
     */
    Set<String> extractRequestedKeys(AuthenticationRequest authenticationRequest);

    /**
     * Based on filled values from identity is filled requested sreg keys.
     * 
     * @param keys
     *            required set of requested keys
     * @param response
     *            required response where will be values stored
     * @param identity
     *            required identity
     * @param fieldsToSign
     *            required set of fields that will be signed.
     */
    void fillSregResponse(Set<String> keys, AuthenticationResponse response, Identity identity,
	    Set<String> fieldsToSign);
}
