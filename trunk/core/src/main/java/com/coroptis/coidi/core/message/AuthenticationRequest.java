package com.coroptis.coidi.core.message;

import java.util.Map;

import com.google.common.base.Objects;

public class AuthenticationRequest extends AbstractOpenIdRequest {

	public final static String IDENTITY = "identity";
	public final static String CLAIMED_ID = "claimed_id";
	public final static String ASSOC_HANDLE = "assoc_handle";
	public final static String RETURN_TO = "return_to";
	public final static String REALM = "realm";

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
		return Objects.toStringHelper(AuthenticationRequest.class)
				.add("assocHandle", getAssocHandle())
				.add("identity", getIdentity()).add("mode", getMode())
				.add("realm", getRealm()).add("returnTo", getReturnTo())
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

}
