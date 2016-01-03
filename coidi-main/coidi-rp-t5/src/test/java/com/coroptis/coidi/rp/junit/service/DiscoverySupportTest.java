/**
w * Copyright 2012 coroptis.com
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
package com.coroptis.coidi.rp.junit.service;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.rp.services.DiscoverySupport;
import com.coroptis.coidi.rp.services.impl.DiscoverySupportImpl;
import com.coroptis.coidi.rp.util.AbstractLocalJunitTest;

public class DiscoverySupportTest extends AbstractLocalJunitTest {

    private final static String SERVICE_NAME = "realService";

    private DiscoverySupport discoverySupport;

    public void testIsItEmail() throws Exception {
	assertTrue(discoverySupport.isItEmail("kachna@gmail.com"));
	assertTrue(discoverySupport.isItEmail("kachna@gmail.cz"));
	assertTrue(discoverySupport.isItEmail("kj.kjjk@some-server.com"));
	assertTrue(discoverySupport.isItEmail("kj.kjjk@some.server.com"));

	assertFalse(discoverySupport.isItEmail("kj.kjjk@some-server.com-ll"));
	assertFalse(discoverySupport.isItEmail("#$kj.kjjk@some-server.com-ll"));
	assertFalse(discoverySupport.isItEmail("http://localhost:8080/user/juan"));
	assertFalse(discoverySupport.isItEmail(""));
	assertFalse(discoverySupport.isItEmail(null));

    }

    public void testIsXri() throws Exception {
	assertTrue(discoverySupport.isXri("=Mary.Jones"));
	assertTrue(discoverySupport.isXri("xri://=example"));
	assertTrue(discoverySupport.isXri("@Jones.and.Company"));
	assertTrue(discoverySupport.isXri("+phone.number"));
	assertTrue(discoverySupport.isXri("+phone.number/(+area.code)"));
	assertTrue(discoverySupport.isXri("=Mary.Jones/(+phone.number)"));
	assertTrue(discoverySupport.isXri("@Jones.and.Company/(+phone.number)"));
	assertTrue(discoverySupport.isXri("@Jones.and.Company/((+phone.number)/(+area.code))"));
	assertTrue(discoverySupport.isXri("=!13cf.4da5.9371.a7c5"));
	assertTrue(discoverySupport.isXri("@!280d.3822.17bf.ca48!78d2/!12"));

	assertFalse(discoverySupport.isXri("kachna@gmail.com"));
	assertFalse(discoverySupport.isXri("kj.kjjk@some.server.com"));
    }

    public void testNormalize() throws Exception {
	assertEquals("=example", discoverySupport.normalize("xri://=example"));
	assertEquals("=example", discoverySupport.normalize("=example"));

	assertEquals("http://kachna.test.com/", discoverySupport.normalize("kachna.test.com"));
	assertEquals("http://kachna.test.com/ahoj",
		discoverySupport.normalize("http://kachna.test.com/ahoj#somePart"));
	assertEquals("http://example.com/", discoverySupport.normalize("http://example.com/"));
	assertEquals("http://example.com/user/",
		discoverySupport.normalize("http://example.com/user/"));
	assertEquals("http://example.com/user",
		discoverySupport.normalize("http://example.com/user"));
	assertEquals("https://example.com/", discoverySupport.normalize("https://example.com/"));
	assertEquals("http://example.com/", discoverySupport.normalize("http://example.com"));
    }

    public void testIsUrl() throws Exception {
	assertTrue(discoverySupport.isItUrl("http://www.google.com/a"));
	assertTrue(discoverySupport.isItUrl("http://www.google.com"));
	assertTrue(discoverySupport.isItUrl("http://www.google.com/"));
	assertTrue(discoverySupport.isItUrl("http://www.google.com/a/"));
	assertTrue(discoverySupport.isItUrl("http://localhost:8080/user/juan"));

    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(DiscoverySupport.class, DiscoverySupportImpl.class).withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	discoverySupport = getService(SERVICE_NAME, DiscoverySupport.class);
    }

    @Override
    protected void tearDown() throws Exception {
	discoverySupport = null;
	super.tearDown();
    }

}
