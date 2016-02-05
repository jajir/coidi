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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.op.entities.IdentitySreg;
import com.coroptis.coidi.op.services.SregService;

public class SregServiceImpl implements SregService {

    @Override
    public boolean isSreg10(final AuthenticationRequest request) {
	if (!SREG_NS.equals(request.get("ns.sreg"))) {
	    return !StringUtils.isEmpty(request.get(SREG_REQUIRED))
		    || !StringUtils.isEmpty(request.get(SREG_OPTIONAL));
	}
	return false;
    }

    @Override
    public boolean isSreg11(final AuthenticationRequest request) {
	return SREG_NS.equals(request.get("ns.sreg"));
    }

    @Override
    public Set<String> extractRequestedKeys(final AuthenticationRequest authenticationRequest) {
	Set<String> keys = new HashSet<String>();
	if (authenticationRequest.get(SREG_OPTIONAL) != null) {
	    Collections.addAll(keys, authenticationRequest.get(SREG_OPTIONAL).split(","));
	}
	if (authenticationRequest.get(SREG_REQUIRED) != null) {
	    Collections.addAll(keys, authenticationRequest.get(SREG_REQUIRED).split(","));
	}
	return keys;
    }

    @Override
    public void fillSregResponse(final Set<String> keys, final AuthenticationResponse response,
	    final IdentitySreg identity, final Set<String> fieldsToSign) {
	// FIXME following code disable sreg extension, correct it
	if (keys.contains(SREG_NICKNAME) && StringUtils.isNotEmpty(identity.getNickname())) {
	    response.put(SREG + SREG_NICKNAME, identity.getNickname());
	    fieldsToSign.add(SREG + SREG_NICKNAME);
	}
	if (keys.contains(SREG_EMAIL) && StringUtils.isNotEmpty(identity.getEmail())) {
	    response.put(SREG + SREG_EMAIL, identity.getEmail());
	    fieldsToSign.add(SREG + SREG_EMAIL);
	}
	if (keys.contains(SREG_FULLNAME) && StringUtils.isNotEmpty(identity.getFullname())) {
	    response.put(SREG + SREG_FULLNAME, identity.getFullname());
	    fieldsToSign.add(SREG + SREG_FULLNAME);
	}
	if (keys.contains(SREG_DOB) && identity.getDob() != null) {
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    response.put(SREG + SREG_DOB, sdf.format(identity.getDob()));
	    fieldsToSign.add(SREG + SREG_DOB);
	}
	if (keys.contains(SREG_GENDRE) && identity.getGendre() != null) {
	    response.put(SREG + SREG_GENDRE, identity.getGendre().name());
	    fieldsToSign.add(SREG + SREG_GENDRE);
	}
	if (keys.contains(SREG_POSTCODE) && StringUtils.isNotEmpty(identity.getPostcode())) {
	    response.put(SREG + SREG_POSTCODE, identity.getPostcode());
	    fieldsToSign.add(SREG + SREG_POSTCODE);
	}
	if (keys.contains(SREG_COUNTRY) && StringUtils.isNotEmpty(identity.getCountry())) {
	    response.put(SREG + SREG_COUNTRY, identity.getCountry());
	    fieldsToSign.add(SREG + SREG_COUNTRY);
	}
	if (keys.contains(SREG_LANGUAGE) && StringUtils.isNotEmpty(identity.getLanguage())) {
	    response.put(SREG + SREG_LANGUAGE, identity.getLanguage());
	    fieldsToSign.add(SREG + SREG_LANGUAGE);
	}
	if (keys.contains(SREG_TIMEZONE) && StringUtils.isNotEmpty(identity.getTimezone())) {
	    response.put(SREG + SREG_TIMEZONE, identity.getTimezone());
	    fieldsToSign.add(SREG + SREG_TIMEZONE);
	}
    }

}
