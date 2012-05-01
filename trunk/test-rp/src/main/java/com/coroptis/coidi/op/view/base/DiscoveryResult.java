package com.coroptis.coidi.op.view.base;


public class DiscoveryResult {

	private String endPoint;

	private String claimedIdentifier;

	/**
	 * OpenID protocol version.
	 */
	private String version;

	/**
	 * @return the endPoint
	 */
	public String getEndPoint() {
		return endPoint;
	}

	/**
	 * @param endPoint the endPoint to set
	 */
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * @return the claimedIdentifier
	 */
	public String getClaimedIdentifier() {
		return claimedIdentifier;
	}

	/**
	 * @param claimedIdentifier the claimedIdentifier to set
	 */
	public void setClaimedIdentifier(String claimedIdentifier) {
		this.claimedIdentifier = claimedIdentifier;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

}
