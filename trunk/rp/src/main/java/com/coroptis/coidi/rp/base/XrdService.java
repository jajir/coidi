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

public class XrdService {

	private Integer priority;

	private String url;

	private final Set<String> types = new HashSet<String>();

	public static final String TYPE_OPENID_2_0 = "http://specs.openid.net/auth/2.0/server";
	public static final String TYPE_ATTRIBUTE_EXCHANGE_2_0 = "http://openid.net/srv/ax/1.0";
	public static final String TYPE_UI_POPUP_1_0 = "http://specs.openid.net/extensions/ui/1.0/mode/popup";
	public static final String TYPE_UI_ICON_1_0 = "http://specs.openid.net/extensions/ui/1.0/icon";
	public static final String TYPE_PAPE_1_0 = "http://specs.openid.net/extensions/pape/1.0";
	public static final String TYPE_SREG_1_0 = "http://openid.net/sreg/1.0";
	public static final String TYPE_SREG_1_1 = "http://openid.net/sreg/1.1";
	public static final String TYPE_CLAIMED_IDENTIFIER_ELEMENT_1_0 = "http://openid.net/signon/1.0";
	public static final String TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0 = "http://specs.openid.net/auth/2.0/signon";

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

}
