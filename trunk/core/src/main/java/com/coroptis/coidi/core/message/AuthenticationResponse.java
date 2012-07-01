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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AuthenticationResponse extends AbstractOpenIdRequest {

	public final static String MODE_ID_RES = "id_res";
	public final static String OP_ENDPOINT = "op_endpoint";
	public final static String IDENTITY = "identity";
	public final static String CLAIMED_ID = "claimed_id";
	public final static String RETURN_TO = "return_to";
	public final static String NONCE = "response_nonce";
	public final static String INVALIDATE_HANDLE = "invalidate_handle";
	public final static String ASSOC_HANDLE = "assoc_handle";
	public final static String SIGNED = "signed";
	public final static String SIG = "sig";

	public AuthenticationResponse() {
		super();
		setNameSpace(OPENID_NS_20);
		setMode(MODE_ID_RES);
		setUrl(true);
	}

	public AuthenticationResponse(final Map<String, String> map) {
		super(map);
		setNameSpace(OPENID_NS_20);
		setUrl(true);
	}

	@Override
	public Map<String, String> getMap() {
		Map<String, String> out = new HashMap<String, String>();
		for (Entry<String, String> entry : super.getMap().entrySet()) {
			out.put(entry.getKey(), entry.getValue());
		}
		return out;
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
	 * @return the opEndpoint
	 */
	public String getOpEndpoint() {
		return get(OP_ENDPOINT);
	}

	/**
	 * @param opEndpoint
	 *            the opEndpoint to set
	 */
	public void setOpEndpoint(final String opEndpoint) {
		put(OP_ENDPOINT, opEndpoint);
	}

	/**
	 * @return the claimedId
	 */
	public String getClaimedId() {
		return get(CLAIMED_ID);
	}

	/**
	 * @param claimedId
	 *            the claimedId to set
	 */
	public void setClaimedId(final String claimedId) {
		put(CLAIMED_ID, claimedId);
	}
}
