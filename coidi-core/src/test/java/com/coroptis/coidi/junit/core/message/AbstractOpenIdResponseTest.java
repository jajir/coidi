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
package com.coroptis.coidi.junit.core.message;

import java.util.HashMap;
import java.util.Map;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;

import junit.framework.TestCase;

public class AbstractOpenIdResponseTest extends TestCase {

    public void testCreate() throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("mode", "association");

	AbstractOpenIdResponse response = new AbstractOpenIdResponse(map);

	assertEquals("association", response.getMode());
    }

}
