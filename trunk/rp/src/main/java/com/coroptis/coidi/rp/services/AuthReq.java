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
package com.coroptis.coidi.rp.services;

import java.util.Map;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;

/**
 * 
 * @author jan
 * 
 */
public interface AuthReq {

	final static String REG_NEW_IDENTITY = "registration.itIsNewIdentity";

	/**
	 * 
	 * @param authenticationRequest
	 * @param discoveryResult
	 * @param parameters
	 *            required map of parameters, this parameters will be used by
	 *            particular implementations, parameters will be used for
	 *            passing values from front-end.
	 * @return
	 */
	boolean process(AuthenticationRequest authenticationRequest,
			DiscoveryResult discoveryResult, Map<String, String> parameters);

}
