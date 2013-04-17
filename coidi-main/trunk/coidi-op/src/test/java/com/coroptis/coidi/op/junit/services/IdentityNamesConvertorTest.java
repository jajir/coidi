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

    public void testGetOpIdentifier() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%");

	assertEquals("http://localhost:8080/user/karel",
		convertor.convertToOpLocalIdentifier("karel"));
    }

    public void testGetOpIdentifier_null() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%");

	try {
	    convertor.convertToOpLocalIdentifier(null);
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
    }

    public void testGetOpIdentifier_invalidPattern() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%idenity%");

	try {
	    convertor.convertToOpLocalIdentifier("karel");
	    fail();
	} catch (CoidiException e) {
	    assertTrue(true);
	}
    }

    public void testGetOpIdentifier_placeholderInTheMiddle() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://%identity%.server.com:8080/");

	assertEquals("http://karel.server.com:8080/", convertor.convertToOpLocalIdentifier("karel"));
    }

    public void testGetOpLocalIdentifier() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%");

	assertEquals("karel", convertor.convertToIdentityId("http://localhost:8080/user/karel"));
    }

    public void testGetOpLocalIdentifier_placeholderInTheMiddle() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://%identity%.server.com:8080/");

	assertEquals("karel", convertor.convertToIdentityId("http://karel.server.com:8080/"));
    }

    public void testGetOpLocalIdentifier_null() throws Exception {
	IdentityNamesConvertor convertor = new IdentityNamesConvertorImpl(
		"http://localhost:8080/user/%identity%");

	try {
	    convertor.convertToIdentityId(null);
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
    }

    public void testGetOpLocalIdentifier_invalidInoutIdentity() throws Exception {
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
		"http://localhost:8080/user/%identity%");

	assertTrue(convertor.isOpLocalIdentifier("karel"));
	assertFalse(convertor.isOpLocalIdentifier("http://localhost:8080/user/karel"));

    }

}
