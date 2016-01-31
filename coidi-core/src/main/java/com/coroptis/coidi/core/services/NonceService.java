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
     *            string noce value
     * @return date from nonce
     */
    Date extractDate(String nonce);

    /**
     * Verifies whether a nonce is valid or not base on expiration.
     * 
     * @param nonce
     *            nonce to verify
     * @param expirationInMinutes
     *            minutes how long the nonce is valid
     * @return <code>true</code> if nonce is valid otherwise <code>false</code>
     */
    boolean verifyNonceExpiration(String nonce, Integer expirationInMinutes);

    String createNonce();

}