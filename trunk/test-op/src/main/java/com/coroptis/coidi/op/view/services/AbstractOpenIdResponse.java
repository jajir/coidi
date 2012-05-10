package com.coroptis.coidi.op.view.services;

public abstract class AbstractOpenIdResponse {

	private String redirectToUrl;

	public abstract String getMessage();

	/**
	 * @return the redirectToUrl
	 */
	public String getRedirectToUrl() {
		return redirectToUrl;
	}

	/**
	 * @param redirectToUrl
	 *            the redirectToUrl to set
	 */
	public void setRedirectToUrl(String redirectToUrl) {
		this.redirectToUrl = redirectToUrl;
	}

}
