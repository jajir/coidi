package com.coroptis.coidi.core.junit.message;

import junit.framework.TestCase;

import com.coroptis.coidi.core.message.AuthenticationRequest;

public class AbstractMessageTest extends TestCase {

    private AuthenticationRequest msg;

    public void testConstructor_null_map_throwException() throws Exception {
	try {
	    new AuthenticationRequest(null);
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
    }

    public void testEquals_nullValue() throws Exception {
	assertFalse(msg.equals(null));
    }

    public void testEquals_invalidObjectType() throws Exception {
	assertFalse(msg.equals("test string"));
    }

    public void testEquals_invalidMapMembersCount() throws Exception {
	AuthenticationRequest msg2 = new AuthenticationRequest();
	assertFalse(msg.equals(msg2));
    }

    public void testEquals_invalidMapMembersContent() throws Exception {
	AuthenticationRequest msg2 = new AuthenticationRequest();
	msg2.put("hello", "something else");
	assertFalse(msg.equals(msg2));
    }

    public void testEquals_mapContainsNull() throws Exception {
	AuthenticationRequest msg2 = new AuthenticationRequest();
	msg2.put("hello", null);
	assertFalse(msg.equals(msg2));
    }

    public void testEquals_sameMapContent() throws Exception {
	AuthenticationRequest msg2 = new AuthenticationRequest();
	msg2.put("hello", "world");
	assertTrue(msg.equals(msg2));
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	msg = new AuthenticationRequest();
	msg.put("hello", "world");
    }

    @Override
    protected void tearDown() throws Exception {
	msg = null;
	super.tearDown();
    }

}
