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
package com.coroptis.coidi.rp.integration;

import java.io.File;
import java.nio.charset.Charset;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.XrdsService;
import com.coroptis.coidi.rp.util.AbstractIntegrationTest;
import com.google.common.io.Files;

public class ProcessXrdsTest extends AbstractIntegrationTest {

    // TODO convert it to junit test

    public void testProcessExample1() throws Exception {
	DiscoveryResult ret = readValue("src/test/resources/example-xrds1.xml");

	assertEquals("http://www.myopenid.com/server", ret.getEndPoint());
    }

    public void testProcessExample2() throws Exception {
	DiscoveryResult ret = readValue("src/test/resources/example-xrds2.xml");

	assertEquals("http://www.livejournal.com/openid/server.bml", ret.getEndPoint());
    }

    public void testProcessExample3() throws Exception {
	DiscoveryResult ret = readValue("src/test/resources/example-xrds3.xml");

	assertEquals("http://localhost:8080/openid", ret.getEndPoint());
    }

    public void testProcessExample4() throws Exception {
	DiscoveryResult ret = readValue("src/test/resources/example-xrds4.xml");

	assertEquals("https://www.google.com/accounts/o8/ud", ret.getEndPoint());
    }

    public void testProcessExample5() throws Exception {
	DiscoveryResult ret = readValue("src/test/resources/example-xrds5.xml");

	assertEquals("http://server.myid.net/server/2.0", ret.getEndPoint());
    }

    private DiscoveryResult readValue(String fileName) throws Exception {
	XrdsService xrdsService = getService(XrdsService.class);
	String xrds = Files.toString(new File(fileName), Charset.forName("UTF-8"));
	return xrdsService.extractDiscoveryResult(xrds);
    }

}
