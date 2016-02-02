package com.coroptis.coidi.junit.op.services;

import org.easymock.EasyMock;

import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.RealmTool;
import com.coroptis.coidi.op.services.impl.RealmToolImpl;

import junit.framework.TestCase;

public class RealmToolTest extends TestCase {

    private OpConfigurationService conf;

    private Object[] mocks;

    public void test_isMatching_missingRealm() throws Exception {
	final RealmTool service = init(true);

	try {
	    service.isMatching(null, "anything");
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
    }

    public void test_isMatching_missingReturnto() throws Exception {
	final RealmTool service = init(true);

	try {
	    service.isMatching("anything", null);
	    fail();
	} catch (NullPointerException e) {
	    assertTrue(true);
	}
    }

    public void test_isMatching_wilcardsEnabled() throws Exception {
	final RealmTool service = init(true);
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
	final RealmTool service = init(false);
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
	assertTrue(service.isMatching("https://login.janrain.com/",
		"https://login.janrain.com/openid/finish?token=63ebc6f6f135c697058a468f449ea92af3297e1e"));
    }

    private RealmToolImpl init(boolean isWildcardAllowed) {
	EasyMock.expect(conf.isWildcardAllowedInRealm()).andReturn(isWildcardAllowed);
	EasyMock.replay(mocks);
	return new RealmToolImpl(conf);
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
	conf = null;
	mocks = null;
	super.tearDown();
    }

}
