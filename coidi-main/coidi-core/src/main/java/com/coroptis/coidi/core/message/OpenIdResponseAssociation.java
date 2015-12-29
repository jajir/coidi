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
package com.coroptis.coidi.core.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class OpenIdResponseAssociation extends AbstractOpenIdResponse {

    private final Map<String, String> map = new HashMap<String, String>();

    public void put(final String key, final String value) {
	map.put(key, value);
    }

    public String getMessage() {
	StringBuilder sb = new StringBuilder();
	// try {
	for (Entry<String, String> entry : map.entrySet()) {
	    sb.append(entry.getKey());
	    // sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
	    sb.append(":");
	    // sb.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
	    sb.append(entry.getValue());
	    sb.append("\n");
	}
	return sb.toString();
	// } catch (UnsupportedEncodingException e) {
	// logger.error(e.getMessage(), e);
	// throw new CoidiException(e.getMessage(), e);
	// }
    }
}
