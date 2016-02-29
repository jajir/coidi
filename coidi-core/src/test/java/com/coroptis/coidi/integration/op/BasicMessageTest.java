package com.coroptis.coidi.integration.op;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.integration.op.util.OpBindingMock;
import com.coroptis.coidi.integration.op.util.OpConfServiceImpl;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;

public class BasicMessageTest {

    private final Logger logger = LoggerFactory.getLogger(BasicMessageTest.class);

    private OpenIdRequestProcessor openIdRequestProcessor;

    private OpBindingMock mocks;

    private Map<String, String> params;

    @Test
    public void test_nameSpace_missing() throws Exception {
	params.remove("openid.ns");
	mocks.replay();
	AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

	logger.debug(ret.getMessage());
	assertTrue(ret instanceof ErrorResponse);
	ErrorResponse err = (ErrorResponse) ret;
	assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
	assertNotNull(err.getError());
	assertTrue(err.getError().contains("openid.ns"));
	assertEquals("error@company.com", err.getContact());
    }

    @Test
    public void test_nameSpace_invalid() throws Exception {
	params.put("openid.ns","Invalid OpenID name space");
	mocks.replay();
	AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

	logger.debug(ret.getMessage());
	assertTrue(ret instanceof ErrorResponse);
	ErrorResponse err = (ErrorResponse) ret;
	assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
	assertNotNull(err.getError());
	assertTrue(err.getError().contains("Unsupported OpenId namespace"));
	assertEquals("error@company.com", err.getContact());
    }

    @Test
    public void test_mode_missing() throws Exception {
	params.remove("openid.mode");
	mocks.replay();
	AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

	logger.debug(ret.getMessage());
	assertTrue(ret instanceof ErrorResponse);
	ErrorResponse err = (ErrorResponse) ret;
	assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
	assertEquals("error@company.com", err.getContact());
	assertNotNull(err.getError());
	assertTrue(err.getError().contains("openid.mode"));
    }

    @Test
    public void test_mode_invalid() throws Exception {
	params.put("openid.mode","invalid");
	mocks.replay();
	AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

	logger.debug(ret.getMessage());
	assertTrue(ret instanceof ErrorResponse);
	ErrorResponse err = (ErrorResponse) ret;
	assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
	assertEquals("error@company.com", err.getContact());
	assertNotNull(err.getError());
	assertTrue(err.getError().contains("invalid"));
	assertTrue(err.getError().contains("openid.mode"));
    }

    @Before
    public void setUp() {
	params = new HashMap<String, String>();
	params.put("openid.mode", "associate");
	params.put("openid.ns", "http://specs.openid.net/auth/2.0");
	params.put("openid.session_type", "DH-SHA256");
	params.put("openid.assoc_type", "HMAC-SHA256");
	params.put("openid.dh_consumer_public",
		"NbTkEmk0bUfs5DcLplIBuAo3UXixzCcQDUTP84o5mqp/ZoFss7ct8Fq0KTyb21XeOen5uVL+2n/BttaeuNA5FyaCCv7F5CTaRIwUrLOY12nIQsTZIoiBKlrD+xUpGjbQe31ckwCu1oJ3mEG2pKUfs/3yX3Ginn+1LthEoOxc3lY=");

	mocks = new OpBindingMock(new OpConfServiceImpl("op_application.properties"));
	openIdRequestProcessor = mocks.getOpenIdRequestProcessor();
    }

    @After
    public void tearDown() {
	mocks.verify();
	mocks = null;
	openIdRequestProcessor = null;
    }

}
