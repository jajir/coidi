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
package com.coroptis.coidi.op.view.junit;

import java.util.Map;

import junit.framework.TestCase;

import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.core.services.impl.MessageServiceImpl;

public class MessageServiceTest extends TestCase {

    private MessageService messageService;

    public void testConvertToMap() throws Exception {
	Map<String, String> ret = messageService.convertUrlToMap("name=karel&age=9");

	assertNotNull(ret);
	assertEquals("karel", ret.get("name"));
	assertEquals("9", ret.get("age"));
    }

    public void testConvertToMap_2() throws Exception {
	Map<String, String> ret = messageService.convertUrlToMap("openid.dh_consumer_public="
		+ "Plw3vp8AnS6okgJ%2BpcQLSoTNYZbtO245PwyyFNpAce70gX8arX"
		+ "MZ4ieTR1joxUzGB7oy0DJy0PjBN320P3dC8nlOqicOBuLFWUv5v7"
		+ "Rz1fjZ6s6QFTpP5mLPHoFhHibbI%2B%2BFG3zz1U1kGXUoZ8iju5DI"
		+ "FdelIPGdbjBzAnjpkaA%3D&openid.assoc_type=HMAC-SHA1&"
		+ "openid.ns=http%3A%2F%2Fopenid.net%2Fsignon%2F2.0&"
		+ "openid.session_type=DH-SHA1&openid.id_rp=RP1&" + "openid.mode=associate");

	assertNotNull(ret);
	assertEquals("associate", ret.get("openid.mode"));
	assertEquals("DH-SHA1", ret.get("openid.session_type"));
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	messageService = new MessageServiceImpl();
    }

    @Override
    protected void tearDown() throws Exception {
	messageService = null;
	super.tearDown();
    }

}
