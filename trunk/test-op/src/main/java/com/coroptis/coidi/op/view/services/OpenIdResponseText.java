package com.coroptis.coidi.op.view.services;

/**
 * Allows to process one specific openID message.
 * 
 * @author jan
 * 
 */
public class OpenIdResponseText extends AbstractOpenIdResponse {

	private String message;

	public OpenIdResponseText() {

	}

	public OpenIdResponseText(final String message) {
		this.message = message;
	}

	public OpenIdResponseText(final String message,
			final String redirectToUrl) {
		this.message = message;
		super.setRedirectToUrl(redirectToUrl);
	}

	/**
	 * Message that will be pass as response to user. When user will be
	 * redirected this value will be used as URL parameter.
	 * 
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
