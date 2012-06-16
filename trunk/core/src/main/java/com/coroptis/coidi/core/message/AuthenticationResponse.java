package com.coroptis.coidi.core.message;

import java.util.Map;

public class AuthenticationResponse extends AbstractOpenIdResponse {

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
