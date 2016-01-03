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

import com.coroptis.coidi.rp.base.DiscoveryResult;

/**
 * Provide support for particular {@link DiscoveryProcessor} implementations.
 * 
 * @author jan
 * 
 */
public interface DiscoverySupport {

    static final String EMAIL_PATTERN = "([_A-Za-z0-9-]+)(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";

    static final String XRI_PATTERN = "^([=@+$!\\(]|xri://).*";

    static final String DOMAIN_SLASH_PATTERN = "^(http://|https://)[^/]*";

    /**
     * Try to get and process XRDS document from giver URL.
     * 
     * @param xrdsDocumentUrl
     *            required XRDS document URL
     * @param claimedId
     *            required claimed user's id
     * @return resolved XRDS document
     * @throws AuthenticationProcessException
     *             When exception occurs during obtaining discovery result than
     *             this exception with proper description is throws
     */
    DiscoveryResult getXrdsDocument(String xrdsDocumentUrl, String claimedId)
	    throws AuthenticationProcessException;

    /**
     * Guess if given string is email.
     * 
     * @param email
     *            required email string
     * @return return <code>true</code> when given parameter is email otherwise
     *         return <code>null</code>
     */
    Boolean isItEmail(String email);

    Boolean isItUrl(final String url);

    Boolean isXri(String identifier);

    String normalize(String userSuppliedId);

}
