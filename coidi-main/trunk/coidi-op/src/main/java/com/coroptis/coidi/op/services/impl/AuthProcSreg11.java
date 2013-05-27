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

import java.text.SimpleDateFormat;
import java.util.Set;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.base.UserSessionSkeleton;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.SregService;

/**
 * Simple Registration Extension 1.1.
 * 
 * @author jirout
 * 
 */
public class AuthProcSreg11 extends AuthProcSreg10 implements AuthenticationProcessor {

    @Inject
    private Logger logger;

    @Inject
    private SregService sregService;

    @Override
    public AbstractMessage process(AuthenticationRequest authenticationRequest,
	    AuthenticationResponse response, Identity identity,
	    final UserSessionSkeleton userSession, Set<String> fieldsToSign) {
	Set<String> keys = sregService.extractRequestedKeys(authenticationRequest);
	if (!keys.isEmpty()) {
	    logger.debug("simple registration extension 1.0 was detected");
	    response.put("ns.sreg", OpenIdNs.TYPE_SREG_1_1);
	    for (String key : keys) {
		/**
		 * Keys in sreg extension parameters are without sreg. prefix
		 */
		String sregKey = "sreg." + key;
		if (SREG_EMAIL.equals(sregKey) && identity.getEmail() != null) {
		    response.put(SREG_EMAIL, identity.getEmail());
		    fieldsToSign.add(SREG_EMAIL);
		}
		if (SREG_FULLNAME.equals(sregKey) && identity.getFullname() != null) {
		    response.put(SREG_FULLNAME, identity.getFullname());
		    fieldsToSign.add(SREG_FULLNAME);
		}
		if (SREG_DOB.equals(sregKey) && identity.getDob() != null) {
		    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		    response.put(SREG_DOB, sdf.format(identity.getDob()));
		    fieldsToSign.add(SREG_DOB);
		}
		if (SREG_GENDRE.equals(sregKey) && identity.getGendre() != null) {
		    response.put(SREG_GENDRE, identity.getGendre().name());
		    fieldsToSign.add(SREG_GENDRE);
		}
		if (SREG_NICKNAME.equals(sregKey) && identity.getNickname() != null) {
		    response.put(SREG_NICKNAME, identity.getNickname());
		    fieldsToSign.add(SREG_NICKNAME);
		}
		if (SREG_POSTCODE.equals(sregKey) && identity.getPostcode() != null) {
		    response.put(SREG_POSTCODE, identity.getPostcode());
		    fieldsToSign.add(SREG_POSTCODE);
		}
		if (SREG_COUNTRY.equals(sregKey) && identity.getCountry() != null) {
		    response.put(SREG_COUNTRY, identity.getCountry());
		    fieldsToSign.add(SREG_COUNTRY);
		}
		if (SREG_LANGUAGE.equals(sregKey) && identity.getLanguage() != null) {
		    response.put(SREG_LANGUAGE, identity.getLanguage());
		    fieldsToSign.add(SREG_LANGUAGE);
		}
		if (SREG_TIMEZONE.equals(sregKey) && identity.getTimezone() != null) {
		    response.put(SREG_TIMEZONE, identity.getTimezone());
		    fieldsToSign.add(SREG_TIMEZONE);
		}
	    }
	}
	return null;
    }

}
