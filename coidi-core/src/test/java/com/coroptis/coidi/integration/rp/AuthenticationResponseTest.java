package com.coroptis.coidi.integration.rp;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.integration.op.AuthenticationTest;
import com.coroptis.coidi.integration.rp.util.RpModule;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.AuthenticationVerificationService;
import com.coroptis.coidi.util.PropertyModule;
import com.coroptis.coidi.util.Services;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Verify Association response processing.
 * 
 * @author jiroutj
 *
 */
public class AuthenticationResponseTest {
	
	private final Logger logger = LoggerFactory.getLogger(AuthenticationTest.class);

	private AuthenticationVerificationService authVerification;

	private Services services;

	private Map<String, String> params;

	private Association assoc;

	@Test
	public void test_process_assoc_response() throws Exception {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse(params);
		services.replay();
		AuthenticationResult authenticationResult = authVerification.verify(authenticationResponse,
				assoc);
		
		logger.debug(authenticationResult.toString());
		assertFalse(authenticationResult.isPositive());
	}
	
	@Before
	public void setUp() {
		params = new HashMap<String, String>();
		params.put("ns", "http://specs.openid.net/auth/2.0");
		params.put("assoc_handle", "2aadbe11-ce00-47e4-bedd-a254ad922cb7");
		params.put("dh_server_public", "aUcqqw1pw3pU/ewJdVo9dVGPwyvG8A+DoBVI8UY2FzjnBTgMOwKcvMR1RD0haRoCdBLEeHpLOoFo9EOKE7rRAR4N3n9XSNT2BAa9rEXj3eqWFGFxTqlOpjUBC3rROrLPkcBg1ovYob9dUJppwZPTB8wyagAi/qc4tnilhsCft70=");
		params.put("session_type", "DH-SHA1");
		params.put("expires_in", "3598");
		params.put("enc_mac_key", "nEuit6YB6FttN7j2JSwYUO+JV6I=");
		params.put("assoc_type", "HMAC-SHA1");

		System.setProperty("configuration-file", "rp_application.properties");
		Injector injector = Guice.createInjector(new RpModule(), new PropertyModule());
		authVerification = injector.getInstance(AuthenticationVerificationService.class);
		services = injector.getInstance(Services.class);
		services.reset();
		assoc = services.getAssociation();
	}

	@After
	public void tearDown() {
		services.verify();
		services = null;
		authVerification = null;
		assoc = null;
	}

}
