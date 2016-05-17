package com.coroptis.coidi.rp.base;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.MoreObjects;

/**
 * 
 * This object is generated as result of authentication response.
 * 
 * @author jirout
 * 
 */
public class AuthenticationResult {

	public enum Status {
		res, cancel;
	}

	private Status status;

	private String identity;

	private final Map<String, ExtensionResult> extensions = new HashMap<String, ExtensionResult>();

	public boolean isPositive() {
		return Status.res.equals(status);
	}

	/**
	 * @return the extensions
	 */
	public Map<String, ExtensionResult> getExtensions() {
		return extensions;
	}

	public ExtensionResultSreg getSreg() {
		return (ExtensionResultSreg) extensions.get(ExtensionResultSreg.CODE);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(AuthenticationResult.class).add("identity", identity).add("status", status)
				.toString();
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
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

}
