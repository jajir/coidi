package com.coroptis.coidi.conf.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.google.common.base.Objects;

public class AuthenticationRequest {

	private String mode;
	private String identity;
	private String assocHandle;
	private String returnTo;
	private String realm;

	private Logger logger = Logger.getLogger(AuthenticationRequest.class);

	public String createMessage() {
		StringBuilder buff = new StringBuilder();
		try {
			buff.append("openid.ns");
			buff.append("=");
			buff.append("http://specs.openid.net/auth/2.0");
			buff.append("&");
			buff.append("openid.mode");
			buff.append("=");
			buff.append(getMode());
			buff.append("&");
			buff.append("openid.identity");
			buff.append("=");
			buff.append(URLEncoder.encode(getIdentity(), "UTF-8"));
			buff.append("&");
			buff.append("openid.claimed_id");
			buff.append("=");
			buff.append(URLEncoder.encode(getIdentity(), "UTF-8"));
			buff.append("&");
			buff.append("openid.assoc_handle");
			buff.append("=");
			buff.append(URLEncoder.encode(getAssocHandle(), "UTF-8"));
			buff.append("&");
			buff.append("openid.return_to");
			buff.append("=");
			buff.append(URLEncoder.encode(getReturnTo(), "UTF-8"));
			buff.append("&");
			buff.append("openid.realm");
			buff.append("=");
			buff.append(URLEncoder.encode(getRealm(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
		return buff.toString();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(AuthenticationRequest.class)
				.add("assocHandle", assocHandle).add("identity", identity)
				.add("mode", mode).add("realm", realm)
				.add("returnTo", returnTo).toString();
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the identity
	 */
	public String getIdentity() {
		return identity;
	}

	/**
	 * @param identity
	 *            the identity to set
	 */
	public void setIdentity(String identity) {
		this.identity = identity;
	}

	/**
	 * @return the assocHandle
	 */
	public String getAssocHandle() {
		return assocHandle;
	}

	/**
	 * @param assocHandle
	 *            the assocHandle to set
	 */
	public void setAssocHandle(String assocHandle) {
		this.assocHandle = assocHandle;
	}

	/**
	 * @return the returnTo
	 */
	public String getReturnTo() {
		return returnTo;
	}

	/**
	 * @param returnTo
	 *            the returnTo to set
	 */
	public void setReturnTo(String returnTo) {
		this.returnTo = returnTo;
	}

	/**
	 * @return the realm
	 */
	public String getRealm() {
		return realm;
	}

	/**
	 * @param realm
	 *            the realm to set
	 */
	public void setRealm(String realm) {
		this.realm = realm;
	}

}
