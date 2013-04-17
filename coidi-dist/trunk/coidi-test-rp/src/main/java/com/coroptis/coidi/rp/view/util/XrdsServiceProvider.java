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
package com.coroptis.coidi.rp.view.util;

import com.google.common.base.Objects;

public class XrdsServiceProvider implements Comparable<XrdsServiceProvider> {

    private String uri;
    private String delegate;
    private Integer priority;

    /**
     * @return the uri
     */
    public String getUri() {
	return uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri(String uri) {
	this.uri = uri;
    }

    /**
     * @return the delegate
     */
    public String getDelegate() {
	return delegate;
    }

    /**
     * @param delegate
     *            the delegate to set
     */
    public void setDelegate(String delegate) {
	this.delegate = delegate;
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

    @Override
    public int compareTo(XrdsServiceProvider service) {
	if (priority == null) {
	    return service.getPriority() == null ? 0 : -1;
	} else {
	    return service.getPriority() == null ? 1 : service.getPriority() - priority;
	}
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(XrdsServiceProvider.class).add("delegate", delegate)
		.add("uri", uri).add("priority", priority).toString();
    }

}
