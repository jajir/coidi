package com.coroptis.coidi.rp.view.integration;

import java.io.File;
import java.nio.charset.Charset;

import com.coroptis.coidi.rp.services.XrdsService;
import com.coroptis.coidi.rp.view.pages.AbstractIntegrationTest;
import com.google.common.io.Files;

public class ProcessXrdsTest extends AbstractIntegrationTest {

	public void testProcessExample1() throws Exception {
		String ret = readValue("src/test/resources/example-xrds1.xml");

		assertEquals("http://www.myopenid.com/server", ret);
	}

	public void testProcessExample2() throws Exception {
		String ret = readValue("src/test/resources/example-xrds2.xml");

		assertEquals("http://www.livejournal.com/openid/server.bml", ret);
	}

	public void testProcessExample3() throws Exception {
		String ret = readValue("src/test/resources/example-xrds3.xml");

		assertEquals("http://localhost:8080/endpoint", ret);
	}

	private String readValue(String fileName) throws Exception {
		XrdsService xrdsService = getService(XrdsService.class);
		String xrds = Files.toString(new File(fileName),
				Charset.forName("UTF-8"));
		return xrdsService.getEndpoint(xrds);

	}

}
