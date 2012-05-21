package com.coroptis.coidi.core.message;

import java.util.Map;

public class ErrorResponse extends AbstractOpenIdResponse {

	public final static String ERROR = "error";

	public final static String CONTACT = "contact";

	public ErrorResponse(final Boolean isUrl) {
		super();
		setUrl(isUrl);
		setNameSpace(OPENID_NS_20);
	}

	public ErrorResponse(final Boolean isUrl, final String error) {
		super();
		setUrl(isUrl);
		setError(error);
		setNameSpace(OPENID_NS_20);
	}

	public ErrorResponse(final Boolean isUrl, final String error,
			final String contact) {
		super();
		setUrl(isUrl);
		setNameSpace(OPENID_NS_20);
		setError(error);
		setContact(contact);
	}

	public ErrorResponse(final Map<String, String> map, final Boolean isUrl) {
		super(map);
		setUrl(isUrl);
		setNameSpace(OPENID_NS_20);
	}

	public String getError() {
		return get(ERROR);
	}

	public void setError(String error) {
		put(ERROR, error);
	}

	public String getContact() {
		return get(CONTACT);
	}

	public void setContact(String error) {
		put(CONTACT, error);
	}

}
