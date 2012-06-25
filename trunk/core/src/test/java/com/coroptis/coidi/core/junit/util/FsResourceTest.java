package com.coroptis.coidi.core.junit.util;

import org.apache.log4j.Logger;

import com.coroptis.coidi.core.util.FsResource;

import junit.framework.TestCase;

public class FsResourceTest extends TestCase {

	private final static Logger logger = Logger.getLogger(FsResourceTest.class);

	public void testToURI() throws Exception {
		FsResource resource = new FsResource(
				"src/test/resources/configuration-junit.xml");

		assertNotNull(resource.toURL());
		logger.debug("resource URL: " + resource.toURL());
		assertTrue(resource.toURL().getFile()
				.endsWith("sources/configuration-junit.xml"));
	}

}
