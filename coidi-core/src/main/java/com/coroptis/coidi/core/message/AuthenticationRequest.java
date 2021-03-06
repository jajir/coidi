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
package com.coroptis.coidi.core.message;

import java.util.Map;

import com.google.common.base.MoreObjects;

public class AuthenticationRequest extends AbstractOpenIdRequest {

    public final static String IDENTITY_SELECT = "http://specs.openid.net/auth/2.0/identifier_select";
    public final static String IDENTITY = "identity";
    public final static String CLAIMED_ID = "claimed_id";
    public final static String ASSOC_HANDLE = "assoc_handle";
    public final static String RETURN_TO = "return_to";
    public final static String REALM = "realm";

    /**
     * OpenID 1.0 version attribute. It's not used in 2.0 protocol version.
     */
    public final static String TRUST_ROOT = "trust_root";

    @Override
    public String getUrl(final String targetUrl) {
	return getUrlMessage(OPENID, targetUrl);
    }

    public AuthenticationRequest() {
	super();
	setUrl(true);
	setNameSpace(OPENID_NS_20);
    }

    public AuthenticationRequest(final Map<String, String> map) {
	super(map);
	setUrl(true);
	setNameSpace(OPENID_NS_20);
    }

    @Override
    public String toString() {
	return MoreObjects.toStringHelper(AuthenticationRequest.class)
		.add("assocHandle", getAssocHandle()).add("identity", getIdentity())
		.add("mode", getMode()).add("realm", getRealm()).add("returnTo", getReturnTo())
		.toString();
    }

    /**
     * @return the identity
     */
    public String getIdentity() {
	return get(IDENTITY);
    }

    /**
     * @param identity
     *            the identity to set
     */
    public void setIdentity(final String identity) {
	put(IDENTITY, identity);
    }

    /**
     * @return the claimedId
     */
    public String getClaimedId() {
	return get(CLAIMED_ID);
    }

    /**
     * @param claimedId
     *            the identity to set
     */
    public void setClaimedId(final String claimedId) {
	put(CLAIMED_ID, claimedId);
    }

    /**
     * @return the assocHandle
     */
    public String getAssocHandle() {
	return get(ASSOC_HANDLE);
    }

    /**
     * @param assocHandle
     *            the assocHandle to set
     */
    public void setAssocHandle(final String assocHandle) {
	put(ASSOC_HANDLE, assocHandle);
    }

    /**
     * @return the returnTo
     */
    public String getReturnTo() {
	return get(RETURN_TO);
    }

    /**
     * @param returnTo
     *            the returnTo to set
     */
    public void setReturnTo(final String returnTo) {
	put(RETURN_TO, returnTo);
    }

    /**
     * @return the realm
     */
    public String getRealm() {
	return get(REALM);
    }

    /**
     * @param realm
     *            the realm to set
     */
    public void setRealm(final String realm) {
	put(REALM, realm);
    }

    /**
     * @return the trust root
     */
    public String getTrustRoot() {
	return get(TRUST_ROOT);
    }

    /**
     * @param trustRoot
     *            the trust root to set
     */
    public void setTrustRoot(final String trustRoot) {
	put(TRUST_ROOT, trustRoot);
    }
}
