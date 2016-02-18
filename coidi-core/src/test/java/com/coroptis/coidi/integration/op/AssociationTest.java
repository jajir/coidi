package com.coroptis.coidi.integration.op;

import static org.junit.Assert.assertEquals;
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
import com.coroptis.coidi.integration.op.util.OpModule;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.op.services.impl.OpenIdDispatcherAssociation20;
import com.coroptis.coidi.util.PropertyModule;
import com.coroptis.coidi.util.Services;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Tests mainly class {@link OpenIdDispatcherAssociation20}. Test verify
 * validation messages.
 * 
 * @author jan
 *
 */
public class AssociationTest {

    private final Logger logger = LoggerFactory.getLogger(AssociationTest.class);

    private OpenIdRequestProcessor openIdRequestProcessor;

    private Services services;

    private Map<String, String> params;

    @Test
    public void test_dh_consumer_public_missing() throws Exception {
	params.remove("openid.dh_consumer_public");
	services.replay();
	AbstractMessage ret = openIdRequestProcessor.process(params, services.getHttpSession());

	logger.debug(ret.getMessage());
	assertTrue(ret instanceof ErrorResponse);
	ErrorResponse err = (ErrorResponse) ret;
	assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
	assertEquals("Required parameter 'openid.dh_consumer_public' is missing.", err.getError());
	assertEquals("error@company.com", err.getContact());
    }

    @Test
    public void test_session_type_missing() throws Exception {
	params.remove("openid.session_type");
	services.replay();
	AbstractMessage ret = openIdRequestProcessor.process(params, services.getHttpSession());

	logger.debug(ret.getMessage());
	assertTrue(ret instanceof ErrorResponse);
	ErrorResponse err = (ErrorResponse) ret;
	assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
	assertEquals("Required parameter 'openid.session_type' is missing.", err.getError());
	assertEquals("error@company.com", err.getContact());
    }

    @Test
    public void test_session_type_invalid() throws Exception {
	params.put("openid.session_type", "invalid");
	services.replay();
	AbstractMessage ret = openIdRequestProcessor.process(params, services.getHttpSession());

	logger.debug(ret.getMessage());
	assertTrue(ret instanceof ErrorResponse);
	ErrorResponse err = (ErrorResponse) ret;
	assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
	assertEquals("Invalid value 'invalid' of property 'openid.session_type'", err.getError());
	assertEquals("error@company.com", err.getContact());
    }

    @Test
    public void test_assoc_type_missing() throws Exception {
	params.remove("openid.assoc_type");
	services.replay();
	AbstractMessage ret = openIdRequestProcessor.process(params, services.getHttpSession());

	logger.debug(ret.getMessage());
	assertTrue(ret instanceof ErrorResponse);
	ErrorResponse err = (ErrorResponse) ret;
	assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
	assertEquals("Required parameter 'openid.assoc_type' is missing.", err.getError());
	assertEquals("error@company.com", err.getContact());
    }

    @Test
    public void test_assoc_type_invalid() throws Exception {
	params.put("openid.assoc_type", "invalid");
	services.replay();
	AbstractMessage ret = openIdRequestProcessor.process(params, services.getHttpSession());

	logger.debug(ret.getMessage());
	assertTrue(ret instanceof ErrorResponse);
	ErrorResponse err = (ErrorResponse) ret;
	assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
	assertEquals("Invalid value 'invalid' of property 'openid.assoc_type'", err.getError());
	assertEquals("error@company.com", err.getContact());
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

	System.setProperty("configuration-file", "op_application.properties");
	Injector injector = Guice.createInjector(new OpModule(), new PropertyModule());
	openIdRequestProcessor = injector.getInstance(OpenIdRequestProcessor.class);
	services = injector.getInstance(Services.class);
	services.reset();
    }

    @After
    public void tearDown() {
	services.verify();
	services = null;
	openIdRequestProcessor = null;
    }

}