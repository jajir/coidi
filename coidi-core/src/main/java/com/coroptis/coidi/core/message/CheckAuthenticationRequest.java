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

import com.google.common.base.Objects;

public class CheckAuthenticationRequest extends AbstractOpenIdRequest {

    public final static String MODE_CHECK_AUTHENTICATION = "check_authentication";
    public final static String INVALIDATE_HANDLE = "invalidate_handle";
    public final static String IDENTITY = "identity";
    public final static String RETURN_TO = "return_to";
    public final static String NONCE = "nonce";
    public final static String SIGNED = "signed";
    public final static String SIG = "sig";

    public CheckAuthenticationRequest() {
	super();
	setNameSpace(OPENID_NS_20);
	setMode(MODE_CHECK_AUTHENTICATION);
	setUrl(false);
    }

    public CheckAuthenticationRequest(final Map<String, String> map) {
	super(map);
	setNameSpace(OPENID_NS_20);
	setMode(MODE_CHECK_AUTHENTICATION);
	setUrl(false);
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(CheckAuthenticationRequest.class).add(MODE, getMode())
		.add(IDENTITY, getIdentity()).add(INVALIDATE_HANDLE, getInvalidateHandle())
		.add(NONCE, getNonce()).add(RETURN_TO, getReturnTo()).add(SIG, getSignature())
		.add(SIGNED, getSigned()).add(OPENID_NS, getNameSpace())
		.add(AuthenticationRequest.ASSOC_HANDLE, getAssocHandle()).toString();
    }

    @Override
    public Map<String, String> getMap() {
	return super.getMap();
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
     * @return the nonce
     */
    public String getNonce() {
	return get(NONCE);
    }

    /**
     * @param nonce
     *            the nonce to set
     */
    public void setNonce(final String nonce) {
	put(NONCE, nonce);
    }

    /**
     * @return the signed
     */
    public String getSigned() {
	return get(SIGNED);
    }

    /**
     * @param signed
     *            the signed to set
     */
    public void setSigned(final String signed) {
	put(SIGNED, signed);
    }

    /**
     * @return the signature
     */
    public String getSignature() {
	return get(SIG);
    }

    /**
     * @param signature
     *            the signature to set
     */
    public void setSignature(final String signature) {
	put(SIG, signature);
    }

    /**
     * @return the invalidateHandle
     */
    public String getInvalidateHandle() {
	return get(INVALIDATE_HANDLE);
    }

    /**
     * @param invalidateHandle
     *            the invalidateHandle to set
     */
    public void setInvalidateHandle(final String invalidateHandle) {
	put(INVALIDATE_HANDLE, invalidateHandle);
    }

    /**
     * @return the assocHandle
     */
    public String getAssocHandle() {
	return get(AuthenticationRequest.ASSOC_HANDLE);
    }

    /**
     * @param assocHandle
     *            the assocHandle to set
     */
    public void setAssocHandle(final String assocHandle) {
	put(AuthenticationRequest.ASSOC_HANDLE, assocHandle);
    }

}
