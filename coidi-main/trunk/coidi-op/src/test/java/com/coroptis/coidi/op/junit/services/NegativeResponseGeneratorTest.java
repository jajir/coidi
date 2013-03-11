package com.coroptis.coidi.op.junit.services;

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.impl.NegativeResponseGeneratorImpl;
import com.coroptis.coidi.op.util.BaseJunitTest;

public class NegativeResponseGeneratorTest extends BaseJunitTest {
    private final static String SERVICE_NAME = "realService";

    private NegativeResponseGenerator service;

    public void testSimpleError() throws Exception {
	ErrorResponse ret = service.simpleError("some error");

	assertNotNull(ret);
	assertEquals("some error", ret.getError());
	assertEquals(false, ret.isUrl());
	assertEquals("john@gmail.com", ret.getContact());
	assertEquals(AbstractOpenIdResponse.OPENID_NS_20, ret.getNameSpace());
    }
    
    public void testSimpleError_messageIsNull() throws Exception {
	ErrorResponse ret = service.simpleError(null);

	assertNotNull(ret);
	assertNull(ret.getError());
	assertEquals(false, ret.isUrl());
	assertEquals("john@gmail.com", ret.getContact());
	assertEquals(AbstractOpenIdResponse.OPENID_NS_20, ret.getNameSpace());
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(NegativeResponseGenerator.class, NegativeResponseGeneratorImpl.class).withId(
		SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	service = getService(SERVICE_NAME, NegativeResponseGenerator.class);
    }

    @Override
    protected void tearDown() throws Exception {
	service = null;
	super.tearDown();
    }
}
