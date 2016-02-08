package com.coroptis.coidi.op.services;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.dao.BaseIdentityDao;
import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.junit.Services;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * 
 * @author jiroutj
 *
 */
public class OpTest {
	
    private final Logger logger = LoggerFactory.getLogger(OpTest.class);
	
	/**
	 * Guice context.
	 */
	private Injector injector;

	private OpenIdRequestProcessor requestProcessor;

	private Services services;

	@Test
	public void test_initialization() throws Exception {
		assertNotNull(injector);
		assertNotNull(requestProcessor);
	}

	@Test
	public void test_authenticationRequest() throws Exception {
		services.replay();
		final AbstractMessage msg = requestProcessor.process(prepareRequest(), services.getHttpSession());

		logger.debug(msg.getMessage());
	}

	@Before
	public void setup() {
		services = Services.getServices();
		services.reset();
		injector = Guice.createInjector(new PropertyModule(), new AbstractModule() {
			@Override
			protected void configure() {
				final Services services = Services.getServices();
				bind(BaseAssociationDao.class).toInstance(services.getBaseAssociationDao());
				bind(BaseNonceDao.class).toInstance(services.getBaseNonceDao());
				bind(UserVerifier.class).toInstance(services.getUserVerifier());
				bind(BaseIdentityDao.class).toInstance(services.getBaseIdentityDao());
			}
		}, new OpModule());
		requestProcessor = injector.getInstance(OpenIdRequestProcessor.class);
	}

	@After
	public void tearDown() {
		requestProcessor = null;
		injector = null;
	}

	public Map<String, String> prepareRequest() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put(OpenIdDispatcher.OPENID_MODE, CheckAuthenticationRequest.MODE_CHECK_AUTHENTICATION);
		params.put("openid.assoc_handle", "e12ccf51-2484-442c-ba08-61b05be6546f");
		params.put("openid.op_endpoint", "http://www.coidi.com/openid");
		params.put("openid.identity", "http://www.coidi.com/identity/qwe");
		params.put("openid.ns", "http://specs.openid.net/auth/2.0");
		params.put("openid.return_to",
				"https://login.janrain.com/openid/finish?token=6546ccbfa92b4f533c397eff74f8cce1c5aa2f4b");
		params.put("openid.signed", "assoc_handle,op_endpoint,identity,return_to,response_nonce,claimed_id");
		params.put("openid.ns.sreg", "http://openid.net/sreg/1.1");
		params.put("openid.response_nonce", "2013-04-14T00:31:31ZivR4+/GQEHI6sw==");
		params.put("openid.claimed_id", "http://specs.openid.net/auth/2.0/identifier_select");
		params.put("openid.sig", "tPjev37UGi1As2UdcA9T/dMOMZref9ND4dBHwa4gwT4=");
		return params;
	}
}
