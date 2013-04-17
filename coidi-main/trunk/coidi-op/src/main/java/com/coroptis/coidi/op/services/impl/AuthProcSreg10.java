/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.op.services.impl;

import java.util.Set;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.SregService;

/**
 * Simple Registration Extension 1.0.
 * 
 * @author jirout
 * 
 */
public class AuthProcSreg10 implements AuthenticationProcessor {

    protected static final String SREG_REQUIRED = "openid.sreg.required";

    protected static final String SREG_OPTIONAL = "openid.sreg.optional";

    protected static final String SREG_POLICY_URL = "openid.sreg.policy_url";

    protected static final String SREG_NICKNAME = "sreg.nickname";
    protected static final String SREG_EMAIL = "sreg.email";
    protected static final String SREG_FULLNAME = "sreg.fullname";
    protected static final String SREG_DOB = "sreg.dob";
    protected static final String SREG_GENDRE = "sreg.gendre";
    protected static final String SREG_POSTCODE = "sreg.postcode";
    protected static final String SREG_COUNTRY = "sreg.country";
    protected static final String SREG_LANGUAGE = "sreg.language";
    protected static final String SREG_TIMEZONE = "sreg.timezone";

    protected static final String DATE_FORMAT = "yyyy-MM-dd";

    @Inject
    private Logger logger;

    @Inject
    private SregService sregService;

    @Override
    public AbstractMessage process(AuthenticationRequest authenticationRequest,
	    AuthenticationResponse response, Identity identity, Set<String> fieldsToSign) {
	Set<String> keys = sregService.extractRequestedKeys(authenticationRequest);
	if (!keys.isEmpty()) {
	    logger.debug("simple registration extension 1.0 was detected");
	    for (String key : keys) {
		if (SREG_NICKNAME.equals(key) && identity.getNickname() != null) {
		    response.put(SREG_NICKNAME, identity.getNickname());
		} else if (SREG_EMAIL.equals(key) && identity.getEmail() != null) {
		    response.put(SREG_EMAIL, identity.getEmail());
		}
		if (SREG_FULLNAME.equals(key) && identity.getNickname() != null) {
		    response.put(SREG_FULLNAME, identity.getNickname());
		} else if (SREG_DOB.equals(key) && identity.getNickname() != null) {
		    response.put(SREG_DOB, identity.getNickname());
		} else if (SREG_GENDRE.equals(key) && identity.getNickname() != null) {
		    response.put(SREG_GENDRE, identity.getNickname());
		} else if (SREG_NICKNAME.equals(key) && identity.getNickname() != null) {
		    response.put(SREG_NICKNAME, identity.getNickname());
		} else if (SREG_POSTCODE.equals(key) && identity.getNickname() != null) {
		    response.put(SREG_POSTCODE, identity.getNickname());
		} else if (SREG_COUNTRY.equals(key) && identity.getNickname() != null) {
		    response.put(SREG_COUNTRY, identity.getNickname());
		} else if (SREG_LANGUAGE.equals(key) && identity.getNickname() != null) {
		    response.put(SREG_LANGUAGE, identity.getNickname());
		} else if (SREG_TIMEZONE.equals(key) && identity.getNickname() != null) {
		    response.put(SREG_TIMEZONE, identity.getNickname());
		}
	    }
	}
	return null;
    }

}
