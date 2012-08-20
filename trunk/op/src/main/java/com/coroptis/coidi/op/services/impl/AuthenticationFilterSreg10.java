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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationService;

public class AuthenticationFilterSreg10 implements AuthenticationProcessor {

	public static final String SREG_REQUIRED = "openid.sreg.required";

	public static final String SREG_OPTIONAL = "openid.sreg.optional";

	public static final String SREG_POLICY_URL = "openid.sreg.policy_url";

	public static final String SREG_NICKNAME = "nickname";
	public static final String SREG_EMAIL = "email";
	public static final String SREG_FULLNAME = "fullname";
	public static final String SREG_DOB = "dob";
	public static final String SREG_GENDRE = "gendre";
	public static final String SREG_POSTCODE = "postcode";
	public static final String SREG_COUNTRY = "country";
	public static final String SREG_LANGUAGE = "language";
	public static final String SREG_TIMEZONE = "timezone";

	@Inject
	private Logger logger;

	@Inject
	private AuthenticationService authenticationService;

	@Override
	public AbstractMessage process(AuthenticationRequest authenticationRequest,
			AbstractMessage response, Identity identity) {
		if (authenticationRequest.getMap().get(SREG_OPTIONAL) != null
				&& authenticationRequest.getMap().get(SREG_REQUIRED) != null) {
			logger.debug("simple registration extension 1.0 was detected");
			Set<String> keys = new HashSet<String>();
			if (authenticationRequest.getMap().get(SREG_OPTIONAL) != null) {
				Collections.addAll(keys,
						authenticationRequest.getMap().get(SREG_OPTIONAL)
								.split(","));
			}
			if (authenticationRequest.getMap().get(SREG_REQUIRED) != null) {
				Collections.addAll(keys,
						authenticationRequest.getMap().get(SREG_REQUIRED)
								.split(","));
			}
			for (String key : keys) {
				if (SREG_NICKNAME.equals(key) && identity.getNickname() != null) {
					response.put(SREG_NICKNAME, identity.getNickname());
				} else if (SREG_EMAIL.equals(key)
						&& identity.getEmail() != null) {
					response.put(SREG_EMAIL, identity.getEmail());
				}
				if (SREG_FULLNAME.equals(key) && identity.getNickname() != null) {
					response.put(SREG_FULLNAME, identity.getNickname());
				} else if (SREG_DOB.equals(key)
						&& identity.getNickname() != null) {
					response.put(SREG_DOB, identity.getNickname());
				} else if (SREG_GENDRE.equals(key)
						&& identity.getNickname() != null) {
					response.put(SREG_GENDRE, identity.getNickname());
				} else if (SREG_NICKNAME.equals(key)
						&& identity.getNickname() != null) {
					response.put(SREG_NICKNAME, identity.getNickname());
				} else if (SREG_POSTCODE.equals(key)
						&& identity.getNickname() != null) {
					response.put(SREG_POSTCODE, identity.getNickname());
				} else if (SREG_COUNTRY.equals(key)
						&& identity.getNickname() != null) {
					response.put(SREG_COUNTRY, identity.getNickname());
				} else if (SREG_LANGUAGE.equals(key)
						&& identity.getNickname() != null) {
					response.put(SREG_LANGUAGE, identity.getNickname());
				} else if (SREG_TIMEZONE.equals(key)
						&& identity.getNickname() != null) {
					response.put(SREG_TIMEZONE, identity.getNickname());
				}
			}
		}
		return null;
	}

}
