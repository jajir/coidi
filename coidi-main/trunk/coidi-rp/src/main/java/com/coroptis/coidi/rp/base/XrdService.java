/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.rp.base;

import java.util.HashSet;
import java.util.Set;

import com.coroptis.coidi.OpenIdNs;

public class XrdService implements OpenIdNs {

    private Integer priority;

    private String url;

    private String localId;

    private final Set<String> types = new HashSet<String>();

    public Boolean idPresent(String serviceType) {
	return types.contains(serviceType);
    }

    public Integer getEffectivePriority() {
	return priority == null ? Integer.MAX_VALUE : priority;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
	return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(Integer priority) {
	this.priority = priority;
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

    /**
     * @return the types
     */
    public Set<String> getTypes() {
	return types;
    }

    /**
     * @return the localID
     */
    public String getLocalId() {
	return localId;
    }

    /**
     * @param localID
     *            the localID to set
     */
    public void setLocalId(String localID) {
	this.localId = localID;
    }

}
