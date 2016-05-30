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
package com.coroptis.coidi.rp.view.util;

import com.coroptis.coidi.rp.base.AuthenticationResult;

/**
 * Object represents user's http session.
 * 
 * @author jan
 * 
 */
public class UserSession {

	private String ssoIdentity;

	/**
	 * When it comes from OP it's stored here.
	 */
	private AuthenticationResult authenticationResult;

	public boolean isLogged() {
		return ssoIdentity != null;
	}

	/**
	 * @return the ssoIdentity
	 */
	public String getSsoIdentity() {
		return ssoIdentity;
	}

	/**
	 * @param ssoIdentity
	 *            the ssoIdentity to set
	 */
	public void setSsoIdentity(String ssoIdentity) {
		this.ssoIdentity = ssoIdentity;
	}

	/**
	 * @return the authenticationResult
	 */
	public AuthenticationResult getAuthenticationResult() {
		return authenticationResult;
	}

	/**
	 * @param authenticationResult
	 *            the authenticationResult to set
	 */
	public void setAuthenticationResult(AuthenticationResult authenticationResult) {
		this.authenticationResult = authenticationResult;
	}

}
