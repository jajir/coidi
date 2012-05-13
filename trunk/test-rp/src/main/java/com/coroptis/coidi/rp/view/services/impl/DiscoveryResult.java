package com.coroptis.coidi.rp.view.services.impl;

public class DiscoveryResult {

	private String endPoint;

	public DiscoveryResult(final String endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * @return the endPoint
	 */
	public String getEndPoint() {
		return endPoint;
	}

	/**
	 * @param endPoint
	 *            the endPoint to set
	 */
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

}
