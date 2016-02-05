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

public class AbstractOpenIdRequest extends AbstractMessage {

    public AbstractOpenIdRequest() {
	super();
    }

    public AbstractOpenIdRequest(final Map<String, String> map) {
	super(map, OPENID);
    }

    @Override
    public String getMessage() {
	return getPrefixedMessage(OPENID);
    }

    @Override
    public Map<String, String> getMap() {
	Map<String, String> out = new HashMap<String, String>();
	for (Entry<String, String> entry : super.getMap().entrySet()) {
	    out.put(OPENID + entry.getKey(), entry.getValue());
	}
	return out;
    }

}
