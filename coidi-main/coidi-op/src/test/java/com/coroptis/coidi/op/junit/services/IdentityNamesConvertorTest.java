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
package com.coroptis.coidi.op.junit.services;

import junit.framework.TestCase;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.services.IdentityNamesConvertor;
import com.coroptis.coidi.op.services.impl.IdentityNamesConvertorImpl;

/**
 * Tests for {@link IdentityNamesConvertor}.
 * 
 * @author jirout
 * 
 */
public class IdentityNamesConvertorTest extends TestCase {

    public void test_convertToOpLocalIdentifier() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%");

	assertEquals("http://localhost:8080/user/karel",
		convertor.convertToOpLocalIdentifier("karel"));
    }

    public void test_convertToOpLocalIdentifier_null() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%");

	try {
	    convertor.convertToOpLocalIdentifier(null);
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
    }

    public void test_convertToOpLocalIdentifier_invalidPattern() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%idenity%");

	try {
	    convertor.convertToOpLocalIdentifier("karel");
	    fail();
	} catch (CoidiException e) {
	    assertTrue(true);
	}
    }

    public void test_convertToOpLocalIdentifier_placeholderInTheMiddle() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://%identity%.server.com:8080/");

	assertEquals("http://karel.server.com:8080/", convertor.convertToOpLocalIdentifier("karel"));
    }

    public void test_convertToIdentityId() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%");

	assertEquals("karel", convertor.convertToIdentityId("http://localhost:8080/user/karel"));
    }

    public void test_convertToIdentityId_placeholderInTheMiddle() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://%identity%.server.com:8080/");

	assertEquals("karel", convertor.convertToIdentityId("http://karel.server.com:8080/"));
    }

    public void test_convertToIdentityId_placeholderInTheMiddle_invalidInput() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://%identity%.server.com:8080/");

	try {
	    convertor.convertToIdentityId("http://karel.com:8080/");
	    fail();
	} catch (CoidiException e) {
	    assertTrue(true);
	}
    }

    public void test_convertToIdentityId_null() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%");

	try {
	    convertor.convertToIdentityId(null);
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
    }

    public void test_convertToIdentityId_invalidInoutIdentity() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%");

	try {
	    convertor.convertToIdentityId("qwe");
	    fail();
	} catch (CoidiException e) {
	    assertTrue(true);
	}
    }

    public void testIsOpLocalIdentifier() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%/");

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

}