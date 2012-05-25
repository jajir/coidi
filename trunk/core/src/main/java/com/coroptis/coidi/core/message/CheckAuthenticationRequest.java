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
		return Objects.toStringHelper(CheckAuthenticationRequest.class)
				.add(MODE, getMode()).add(IDENTITY, getIdentity())
				.add(INVALIDATE_HANDLE, getInvalidateHandle())
				.add(NONCE, getNonce()).add(RETURN_TO, getReturnTo())
				.add(SIG, getSignature()).add(SIGNED, getSigned())
				.add(OPENID_NS, getNameSpace()).toString();
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

}
