package com.coroptis.coidi.rp.view.util;

/**
 * Object represents user's http session.
 * 
 * @author jan
 * 
 */
public class UserSession {

	private String ssoIdentity;

	public Boolean isLogged() {
		return ssoIdentity != null;
	}

	/**
	 * @return the ssoIdentity
	 */
	public String getSsoIdentity() {
		return ssoIdentity;
	}

	/**
	 * @param ssoIdentity the ssoIdentity to set
	 */
	public void setSsoIdentity(String ssoIdentity) {
		this.ssoIdentity = ssoIdentity;
	}

}
