package com.coroptis.coidi.op.junit.services;

import junit.framework.TestCase;

import com.coroptis.coidi.op.services.RealmTool;
import com.coroptis.coidi.op.services.impl.RealmToolImpl;

public class RealmToolTest extends TestCase {

    public void test_isMatching_missingRealm() throws Exception {
	RealmTool service = new RealmToolImpl(true);

	try {
	    service.isMatching(null, "anything");
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
    }

    public void test_isMatching_missingReturnto() throws Exception {
	RealmTool service = new RealmToolImpl(true);

	try {
	    service.isMatching("anything", null);
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
    }

    public void test_isMatching_wilcardsEnabled() throws Exception {
	RealmTool service = new RealmToolImpl(true);
	assertTrue(service.isMatching("http://*.com/", "http://www.bcc.co.uk.com/"));
	assertFalse(service.isMatching("http://*.com/", "http://www.bcc.co.uk"));
	assertTrue(service.isMatching("http://www.coidi.com/identity/",
		"http://www.coidi.com/identity/karel"));
	assertTrue(service.isMatching("http://www.coidi.com/identity/",
		"http://www.coidi.com/identity/karel/pokus"));
	assertFalse(service.isMatching("http://www.coidi.com/identity/",
		"http://karel.coidi.com/identity/"));
    }

    public void test_isMatching_wilcardsDisabled() throws Exception {
	RealmTool service = new RealmToolImpl(false);
	assertFalse(service.isMatching("http://*.com/", "http://www.bcc.co.uk.com/"));
	assertFalse(service.isMatching("http://*.com/", "http://www.bcc.co.uk"));
	assertFalse(service.isMatching("http://www.coidi.com/identity/*",
		"http://www.coidi.com/identity/karel"));
	assertFalse(service.isMatching("http://www.coidi.com/identity/*",
		"http://www.coidi.com/identity/karel/pokus"));
	assertFalse(service.isMatching("http://www.coidi.com/identity/*",
		"http://karel.coidi.com/identity/"));
	assertTrue(service.isMatching("http://www.coidi.com/identity/karel",
		"http://www.coidi.com/identity/karel"));
	assertTrue(service
		.isMatching("https://login.janrain.com/",
			"https://login.janrain.com/openid/finish?token=63ebc6f6f135c697058a468f449ea92af3297e1e"));
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
	super.tearDown();
    }

}
