package com.coroptis.coidi.integration.rp;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.integration.op.AuthenticationSetupTest;
import com.coroptis.coidi.integration.rp.util.RpBingingMock;
import com.coroptis.coidi.integration.rp.util.RpConfService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.rp.base.AuthenticationResult;
import com.coroptis.coidi.rp.services.AuthenticationVerificationService;

/**
 * Verify Association response processing.
 * 
 * @author jiroutj
 *
 */
public class AuthenticationResponseTest {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationSetupTest.class);

    private AuthenticationVerificationService authVerification;

    private RpBingingMock mocks;

    private Map<String, String> params;

    private Association assoc;

    @Test
    public void test_process_assoc_response() throws Exception {
	AuthenticationResponse authenticationResponse = new AuthenticationResponse(params);
	mocks.replay();
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
	params.put("dh_server_public",
		"aUcqqw1pw3pU/ewJdVo9dVGPwyvG8A+DoBVI8UY2FzjnBTgMOwKcvMR1RD0haRoCdBLEeHpLOoFo9EOKE7rRAR4N3n9XSNT2BAa9rEXj3eqWFGFxTqlOpjUBC3rROrLPkcBg1ovYob9dUJppwZPTB8wyagAi/qc4tnilhsCft70=");
	params.put("session_type", "DH-SHA1");
	params.put("expires_in", "3598");
	params.put("enc_mac_key", "nEuit6YB6FttN7j2JSwYUO+JV6I=");
	params.put("assoc_type", "HMAC-SHA1");
	params.put("response_nonce", "2016-05-17T19:19:48Zhello");
	params.put("mode", "id_res");

	mocks = new RpBingingMock(new RpConfService("rp_application.properties"));
	authVerification = mocks.getAuthenticationVerificationService();
	assoc = mocks.getAssociation();
    }

    @After
    public void tearDown() {
	mocks.verify();
	mocks = null;
	authVerification = null;
	assoc = null;
    }

}
