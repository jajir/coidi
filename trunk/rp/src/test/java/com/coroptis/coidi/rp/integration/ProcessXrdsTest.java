package com.coroptis.coidi.rp.integration;

import java.io.File;
import java.nio.charset.Charset;

import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.XrdsService;
import com.coroptis.coidi.rp.util.AbstractIntegrationTest;
import com.google.common.io.Files;

public class ProcessXrdsTest extends AbstractIntegrationTest {

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

		assertEquals("http://localhost:8080/endpoint", ret.getEndPoint());
	}

	public void testProcessExample4() throws Exception {
		DiscoveryResult ret = readValue("src/test/resources/example-xrds4.xml");

		assertEquals("https://www.google.com/accounts/o8/ud", ret.getEndPoint());
	}

	private DiscoveryResult readValue(String fileName) throws Exception {
		XrdsService xrdsService = getService(XrdsService.class);
		String xrds = Files.toString(new File(fileName),
				Charset.forName("UTF-8"));
		return xrdsService.extractDiscoveryResult(xrds);

	}

}
