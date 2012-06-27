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
