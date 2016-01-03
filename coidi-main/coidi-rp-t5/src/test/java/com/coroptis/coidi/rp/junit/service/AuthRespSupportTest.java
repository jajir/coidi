package com.coroptis.coidi.rp.junit.service;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.rp.services.AuthRespSupport;
import com.coroptis.coidi.rp.services.impl.AuthRespSupportImpl;
import com.coroptis.coidi.rp.util.AbstractLocalJunitTest;

public class AuthRespSupportTest extends AbstractLocalJunitTest {

    private final static String SERVICE_NAME = "realService";

    private AuthRespSupport discoverySupport;

    public void testGetNameSpacePrefix() throws Exception {
	String ret = discoverySupport.getNameSpacePrefix("openid.ns.ext1");

	assertEquals("ext1", ret);
    }

    public void testGetNameSpacePrefix_exception() throws Exception {
	try {
	    discoverySupport.getNameSpacePrefix("bleed.openid.ns.ext1");
	    fail();
	} catch (CoidiException e) {
	    assertTrue(true);
	}
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(AuthRespSupport.class, AuthRespSupportImpl.class).withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	discoverySupport = getService(SERVICE_NAME, AuthRespSupport.class);
    }

    @Override
    protected void tearDown() throws Exception {
	discoverySupport = null;
	super.tearDown();
    }
}
