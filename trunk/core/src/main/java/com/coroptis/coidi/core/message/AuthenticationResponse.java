package com.coroptis.coidi.core.message;

import java.util.Map;

public class AuthenticationResponse extends AbstractOpenIdResponse {

	public final static String ASSOC_HANDLE = "assoc_handle";
	public final static String IDENTITY = "identity";
	public final static String RETURN_TO = "return_to";
	public final static String NONCE = "nonce";
	public final static String SIGNED = "signed";
	public final static String SIG = "sig";

	public AuthenticationResponse() {
		super();
		setUrl(true);
		setNameSpace(OPENID_NS_20);
	}

	public AuthenticationResponse(final Map<String, String> map) {
		super(map);
		setUrl(true);
		setNameSpace(OPENID_NS_20);
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

}
