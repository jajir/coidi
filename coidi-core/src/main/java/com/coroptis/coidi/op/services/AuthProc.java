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

import java.util.Set;

import javax.servlet.http.HttpSession;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;

/**
 * Interface define chain of commands that process incoming authentication
 * request and helps produce authentication response.
 * <p>
 * Each parts of chain of command change response, only terminator return non
 * null value.
 * </p>
 * 
 * @author jirout
 * 
 */
public interface AuthProc {

	/**
	 * 
	 * @param authenticationRequest
	 *            required authentication request
	 * @param response
	 *            required response
	 * @param userSession
	 *            required user's session
	 * @param fieldsToSign
	 *            required parameter where will be added all fields from message
	 *            that should be signed
	 * @return if request could be processed than retun response message
	 *         otherwise <code>null</code>
	 */
	AbstractMessage process(AuthenticationRequest authenticationRequest, AuthenticationResponse response,
			HttpSession userSession, Set<String> fieldsToSign);

	String AUTH_PROC_ASSOCIATION = "authProcAssociation";

	String AUTH_PROC_AX_10 = "authProcAx10";

	String AUTH_PROC_IDENTITY_11 = "authProcIdentity11";

	String AUTH_PROC_IDENTITY_20 = "authProcIdentity20";

	String AUTH_PROC_NONCE = "authProcNonce";

	String AUTH_PROC_SIGN = "authProcSign";

	String AUTH_PROC_SREG_10 = "authProcSreg10";

	String AUTH_PROC_SREG_11 = "authProcSreg11";

	String AUTH_PROC_STATE_LESS_ASSOCIATION = "authProcStateLessAssociation";

	String AUTH_PROC_VERIFY_IDENTITY_11 = "authProcVerifyIdentity11";

	String AUTH_PROC_VERIFY_IDENTITY_20 = "authProcVerifyIdentity20";

	String AUTH_PROC_VERIFY_LOGGED_USER_11_IMMEDIATE = "authProcVerifyLoggedUser11Immediate";

	String AUTH_PROC_VERIFY_LOGGED_USER_11_SETUP = "authProcVerifyLoggedUser11Setup";

	String AUTH_PROC_VERIFY_LOGGED_USER_20_IMMEDIATE = "authProcVerifyLoggedUser20Immediate";

	String AUTH_PROC_VERIFY_LOGGED_USER_20_SETUP = "authProcVerifyLoggedUser20Setup";

}
