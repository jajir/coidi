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
package com.coroptis.coidi.junit.op.services;

import org.easymock.EasyMock;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.services.IdentityNamesConvertor;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.impl.IdentityNamesConvertorImpl;

import junit.framework.TestCase;

/**
 * Tests for {@link IdentityNamesConvertor}.
 * 
 * @author jirout
 * 
 */
public class IdentityNamesConvertorTest extends TestCase {

	private OpConfigurationService conf;

	private Object[] mocks;

	public void test_convertToIdentifier() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://localhost:8080/user/%identity%");

		assertEquals("http://localhost:8080/user/karel", convertor.convertToIdentifier("karel"));
	}

	public void convertToIdentifier() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://localhost:8080/user/%identity%");

		try {
			convertor.convertToIdentifier(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	public void test_convertToIdentifier_invalidPattern() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://localhost:8080/user/%idenity%");

		try {
			convertor.convertToIdentifier("karel");
			fail();
		} catch (CoidiException e) {
			assertTrue(true);
		}
	}

	public void test_convertToIdentifier_placeholderInTheMiddle() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://%identity%.server.com:8080/");

		assertEquals("http://karel.server.com:8080/", convertor.convertToIdentifier("karel"));
	}

	public void test_convertToOpLocalIdentifier() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://localhost:8080/user/%identity%");

		assertEquals("karel", convertor.convertToOpLocalIdentifier("http://localhost:8080/user/karel"));
	}

	public void test_convertToOpLocalIdentifier_placeholderInTheMiddle() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://%identity%.server.com:8080/");

		assertEquals("karel", convertor.convertToOpLocalIdentifier("http://karel.server.com:8080/"));
	}

	public void test_convertToOpLocalIdentifier_placeholderInTheMiddle_invalidInput() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://%identity%.server.com:8080/");

		try {
			convertor.convertToOpLocalIdentifier("http://karel.com:8080/");
			fail();
		} catch (CoidiException e) {
			assertTrue(true);
		}
	}

	public void test_convertToOpLocalIdentifier_null() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://localhost:8080/user/%identity%");

		try {
			convertor.convertToOpLocalIdentifier(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	public void test_convertToOpLocalIdentifier_invalidInoutIdentity() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://localhost:8080/user/%identity%");

		try {
			convertor.convertToOpLocalIdentifier("qwe");
			fail();
		} catch (CoidiException e) {
			assertTrue(true);
		}
	}

	public void test_isOpLocalIdentifier() throws Exception {
		final IdentityNamesConvertor convertor = initConvertor("http://localhost:8080/user/%identity%/");

		assertFalse(convertor.isOpLocalIdentifier(null));
		assertFalse(convertor.isOpLocalIdentifier("karel"));
		assertFalse(convertor.isOpLocalIdentifier("/karel/"));
		assertFalse(convertor.isOpLocalIdentifier("https://localhost:8080/user/karel"));
		assertTrue(convertor.isOpLocalIdentifier("http://localhost:8080/user/karel/"));
		/**
		 * this could cause some problems. When RP sends multiple slashes.
		 */
		assertTrue(convertor.isOpLocalIdentifier("http://localhost:8080/user/karel//"));

	}

	private IdentityNamesConvertor initConvertor(final String pattern) {
		EasyMock.expect(conf.getOpIdentityPattern()).andReturn(pattern);
		EasyMock.replay(mocks);
		return new IdentityNamesConvertorImpl(conf);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		conf = EasyMock.createMock(OpConfigurationService.class);
		mocks = new Object[] { conf };

	}

	@Override
	protected void tearDown() throws Exception {
		EasyMock.verify(mocks);
		mocks = null;
		conf = null;
		super.tearDown();
	}

}
