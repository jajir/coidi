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
package com.coroptis.coidi.core.services;

import java.util.Date;

public interface NonceService {

	final static String ISO_DATETIME_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'";

	/**
	 * Extract a date from a nonce.
	 * 
	 * @param nonce
	 * @return date from nonce
	 */
	Date extractDate(String nonce);

	//FIXME rename to verifyNonceExpiratio
	/**
	 * Verifies whether a nonce is valid or not.
	 * 
	 * @param nonce
	 *            nonce to verify
	 * @param expirationMinutes
	 *            minutes how long the nonce is valid
	 * @return true if nonce is valide, false otherwise
	 */
	boolean verifyNonce(String nonce, Integer expirationInMinutes);

	String createNonce();

}