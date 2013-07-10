package com.coroptis.coidi.op.util;

import com.google.common.base.Objects;

public class RealmRequest {

    private String realmPattern;

    private String url;

    @Override
    public String toString() {
	return Objects.toStringHelper(RealmRequest.class).add("realmPattern", realmPattern)
		.add("url", url).toString();
    }

    /**
     * @return the realmPattern
     */
    public String getRealmPattern() {
	return realmPattern;
    }

    /**
     * @param realmPattern
     *            the realmPattern to set
     */
    public void setRealmPattern(String realmPattern) {
	this.realmPattern = realmPattern;
    }

    /**
     * @return the url
     */
    public String getUrl() {
	return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
	this.url = url;
    }

}
