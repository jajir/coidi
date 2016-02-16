package com.coroptis.coidi.integration.rp;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.integration.op.AuthenticationTest;
import com.coroptis.coidi.integration.op.util.OpModule;
import com.coroptis.coidi.integration.op.util.Services;
import com.coroptis.coidi.integration.rp.util.RpModule;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.coroptis.coidi.util.PropertyModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Verify Association response processing.
 * 
 * @author jiroutj
 *
 */
public class AssociationResponseTest {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationTest.class);

	private OpenIdRequestProcessor openIdRequestProcessor;

	private Services services;

	private Map<String, String> params;

	private Association assoc;

	private Nonce nonce;

	@Before
	public void setUp() {
		params = new HashMap<String, String>();
		params.put(OpenIdDispatcher.OPENID_MODE, CheckAuthenticationRequest.MODE_CHECKID_SETUP);
		params.put("openid.ns", "http://specs.openid.net/auth/2.0");
		params.put("openid.identity", "http://www.coidi.com/identity/qwe");
		params.put("openid.claimed_id", "http://www.coidi.com/identity/qwe");
		params.put("openid.assoc_handle", "cc5b843b-e375-4640-8f71-38e40b2950a6");
		params.put("openid.return_to", "https://sourceforge.net/account/openid_verify.php");
		params.put("openid.realm", "https://sourceforge.net");

		Injector injector = Guice.createInjector(new RpModule(), new PropertyModule());
		openIdRequestProcessor = injector.getInstance(OpenIdRequestProcessor.class);
		services = injector.getInstance(Services.class);
		services.reset();
		assoc = services.getAssociation();
		nonce = services.getNonce();
	}

	@After
	public void tearDown() {
		services.verify();
		services = null;
		openIdRequestProcessor = null;
		assoc = null;
		nonce = null;
	}

}
