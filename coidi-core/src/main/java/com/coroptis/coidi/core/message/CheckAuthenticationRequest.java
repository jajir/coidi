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

public class CheckAuthenticationRequest extends AbstractOpenIdRequest {

	public final static String MODE_CHECK_AUTHENTICATION = "check_authentication";

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
		return MoreObjects.toStringHelper(CheckAuthenticationRequest.class).add(MODE, getMode())
				.add(AuthenticationResponse.IDENTITY, getIdentity())
				.add(AuthenticationResponse.INVALIDATE_HANDLE, getInvalidateHandle())
				.add(AuthenticationResponse.NONCE, getNonce()).add(AuthenticationResponse.RETURN_TO, getReturnTo())
				.add(AuthenticationResponse.SIG, getSignature()).add(AuthenticationResponse.SIGNED, getSigned())
				.add(OPENID_NS, getNameSpace()).add(AuthenticationRequest.ASSOC_HANDLE, getAssocHandle()).toString();
	}

	@Override
	public Map<String, String> getMap() {
		return super.getMap();
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return get(AuthenticationResponse.IDENTITY);
	}

	/**
	 * @param identity
	 *            the identity to set
	 */
	public void setIdentity(final String identity) {
		put(AuthenticationResponse.IDENTITY, identity);
	}

	/**
	 * @return the returnTo
	 */
	public String getReturnTo() {
		return get(AuthenticationResponse.RETURN_TO);
	}

	/**
	 * @param returnTo
	 *            the returnTo to set
	 */
	public void setReturnTo(final String returnTo) {
		put(AuthenticationResponse.RETURN_TO, returnTo);
	}

	/**
	 * @return the nonce
	 */
	public String getNonce() {
		return get(AuthenticationResponse.NONCE);
	}

	/**
	 * @param nonce
	 *            the nonce to set
	 */
	public void setNonce(final String nonce) {
		put(AuthenticationResponse.NONCE, nonce);
	}

	/**
	 * @return the signed
	 */
	public String getSigned() {
		return get(AuthenticationResponse.SIGNED);
	}

	/**
	 * @param signed
	 *            the signed to set
	 */
	public void setSigned(final String signed) {
		put(AuthenticationResponse.SIGNED, signed);
	}

	/**
	 * @return the signature
	 */
	public String getSignature() {
		return get(AuthenticationResponse.SIG);
	}

	/**
	 * @param signature
	 *            the signature to set
	 */
	public void setSignature(final String signature) {
		put(AuthenticationResponse.SIG, signature);
	}

	/**
	 * @return the invalidateHandle
	 */
	public String getInvalidateHandle() {
		return get(AuthenticationResponse.INVALIDATE_HANDLE);
	}

	/**
	 * @param invalidateHandle
	 *            the invalidateHandle to set
	 */
	public void setInvalidateHandle(final String invalidateHandle) {
		put(AuthenticationResponse.INVALIDATE_HANDLE, invalidateHandle);
	}

	/**
	 * @return the assocHandle
	 */
	public String getAssocHandle() {
		return get(AuthenticationResponse.ASSOC_HANDLE);
	}

	/**
	 * @param assocHandle
	 *            the assocHandle to set
	 */
	public void setAssocHandle(final String assocHandle) {
		put(AuthenticationResponse.ASSOC_HANDLE, assocHandle);
	}

	/**
	 * @return the opEndpoint
	 */
	public String getOpEndpoint() {
		return get(AuthenticationResponse.OP_ENDPOINT);
	}

	/**
	 * @param opEndpoint
	 *            the opEndpoint to set
	 */
	public void setOpEndpoint(final String opEndpoint) {
		put(AuthenticationResponse.OP_ENDPOINT, opEndpoint);
	}

	/**
	 * @return the claimedId
	 */
	public String getClaimedId() {
		return get(AuthenticationResponse.CLAIMED_ID);
	}

	/**
	 * @param claimedId
	 *            the claimedId to set
	 */
	public void setClaimedId(final String claimedId) {
		put(AuthenticationResponse.CLAIMED_ID, claimedId);
	}
}
