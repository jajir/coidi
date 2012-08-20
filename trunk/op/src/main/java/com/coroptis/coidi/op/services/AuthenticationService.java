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
package com.coroptis.coidi.op.services;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;

/**
 * This service produce authentication response that is send in redirect to RP.
 */
public interface AuthenticationService {

	/**
	 * Return true if it's valid authentication request. It's when it have mode
	 * checkid_setup or checkid_immediate and contains claimed_id or identity.
	 * 
	 * @param authenticationRequest
	 *            required {@link AuthenticationRequest} object
	 * @return true when it's valid authentication request otherwise return
	 *         false
	 */
	boolean isAuthenticationRequest(AuthenticationRequest authenticationRequest);

	/**
	 * Process authentication request from RP and generate positive or negative
	 * authentication response according to OpenID specifications.
	 * 
	 * @param authenticationRequest
	 *            required {@link AuthenticationRequest}
	 * @return {@link AuthenticationResponse} object
	 */
	AuthenticationResponse process(AuthenticationRequest authenticationRequest);

	/**
	 * Method return used name space in open id authentication request for given
	 * name space extension. For 'openid.ns.ax=http://openid.net/srv/ax/1.0'
	 * from authentication request return 'ax'. If in requestis not given name
	 * space URL than return null.
	 * 
	 * @param authenticationRequest
	 * @param nameSpaceUrl
	 * @return
	 */
	String getNameSpace(AuthenticationRequest authenticationRequest,
			String nameSpaceUrl);
}
