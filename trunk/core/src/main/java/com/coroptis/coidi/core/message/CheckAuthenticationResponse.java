package com.coroptis.coidi.core.message;

import java.util.Map;

public class CheckAuthenticationResponse extends AbstractOpenIdResponse {

	public final static String IS_VALID = "is_valid";
	public final static String INVALIDATE_HANDLE = "invalidate_handle";

	public CheckAuthenticationResponse() {
		super();
		setNameSpace(OPENID_NS_20);
		setUrl(false);
	}

	public CheckAuthenticationResponse(final Map<String, String> map) {
		super(map);
		setNameSpace(OPENID_NS_20);
		setUrl(false);
	}

	/**
	 * @return the isValid
	 */
	public Boolean getIsValid() {
		return Boolean.valueOf(get(IS_VALID));
	}

	/**
	 * @param isValid
	 *            the isValid to set
	 */
	public void setIsValid(final Boolean isValid) {
		put(IS_VALID, isValid.toString());
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
