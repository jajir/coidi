package com.coroptis.coidi.rp.junit.service;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.rp.services.XmlProcessing;
import com.coroptis.coidi.rp.services.impl.XmlProcessingImpl;
import com.coroptis.coidi.rp.util.AbstractLocalJunitTest;
import com.google.common.io.Files;

public class XmlProcessingTest extends AbstractLocalJunitTest {

	private final static String SERVICE_NAME = "realService";

	private XmlProcessing xmlProcessing;

	public void testGetMetaContent1() throws Exception {
		services.replay();
		String ret = xmlProcessing.getMetaContent(Files.toString(new File(
				"src/test/resources/example1.html"), Charset.forName("UTF-8")),
				"x-xrds-location");

		assertNotNull(ret);
		assertEquals("http://claimid.com/jirout/xrds", ret);
	}

	public void testGetMetaContent2() throws Exception {
		services.replay();
		String ret = xmlProcessing.getMetaContent(Files.toString(new File(
				"src/test/resources/example2.html"), Charset.forName("UTF-8")),
				"x-xrds-location");

		assertNotNull(ret);
		assertEquals("http://claimid.com/jirout/xrds", ret);
	}

	public void testGetMetaContent3() throws Exception {
		services.replay();
		String ret = xmlProcessing.getMetaContent(Files.toString(new File(
				"src/test/resources/example3.html"), Charset.forName("UTF-8")),
				"x-xrds-location");

		assertNotNull(ret);
		assertEquals("http://claimid.com/jirout/xrds", ret);
	}

	public void testGetMetaContent_missingTag() throws Exception {
		services.replay();
		String ret = xmlProcessing.getMetaContent(Files.toString(new File(
				"src/test/resources/example4.html"), Charset.forName("UTF-8")),
				"x-xrds-location");

		assertNull(ret);
	}

	public void testGetMetaContent_missingElementInTag() throws Exception {
		services.replay();
		String ret = xmlProcessing.getMetaContent(Files.toString(new File(
				"src/test/resources/example5.html"), Charset.forName("UTF-8")),
				"x-xrds-location");

		assertNull(ret);
	}

	@Override
	public void bind(ServiceBinder binder) {
		binder.bind(XmlProcessing.class, XmlProcessingImpl.class).withId(
				SERVICE_NAME);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		xmlProcessing = getService(SERVICE_NAME, XmlProcessing.class);
	}

	@Override
	protected void tearDown() throws Exception {
		services.verify();
		xmlProcessing = null;
		super.tearDown();
	}

}
