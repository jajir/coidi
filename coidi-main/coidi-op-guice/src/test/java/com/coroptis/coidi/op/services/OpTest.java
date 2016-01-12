package com.coroptis.coidi.op.services;

 import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class OpTest  {

    /**
     * Guice context.
     */
    private Injector injector;

    private OpenIdRequestProcessor service;
    private AuthenticationRequest request;
    private AuthenticationResponse response;

    @Test
    public void test_initialization() throws Exception {
	assertNotNull(injector);
	assertNotNull(service);
	assertNotNull(request);
	assertNotNull(response);
    }

    @Before
    public void setup() {
	injector = Guice.createInjector(new Module() {

	    @Override
	    public void configure(Binder binder) {

	    }
	}, new OpModule());

	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE, CheckAuthenticationRequest.MODE_CHECKID_SETUP);
	params.put("openid.identity", "http://www.coidi.com/identity/qwe");
	params.put("openid.assoc_handle", "cc5b843b-e375-4640-8f71-38e40b2950a6");
	params.put("openid.return_to", "https://sourceforge.net/account/openid_verify.php");
	service = injector.getInstance(OpenIdRequestProcessor.class);
	request = new AuthenticationRequest(params);
	response = new AuthenticationResponse();

    }

    @After
    public void tearDown() {
	response = null;
	request = null;
	service = null;
	injector = null;
    }

    public void testSimple() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put(OpenIdDispatcher.OPENID_MODE,
		CheckAuthenticationRequest.MODE_CHECK_AUTHENTICATION);
	params.put("openid.assoc_handle", "e12ccf51-2484-442c-ba08-61b05be6546f");
	params.put("openid.op_endpoint", "http://www.coidi.com/openid");
	params.put("openid.identity", "http://www.coidi.com/identity/qwe");
	params.put("openid.ns", "http://specs.openid.net/auth/2.0");
	params.put("openid.return_to",
		"https://login.janrain.com/openid/finish?token=6546ccbfa92b4f533c397eff74f8cce1c5aa2f4b");
	params.put("openid.signed",
		"assoc_handle,op_endpoint,identity,return_to,response_nonce,claimed_id");
	params.put("openid.ns.sreg", "http://openid.net/sreg/1.1");
	params.put("openid.response_nonce", "2013-04-14T00:31:31ZivR4+/GQEHI6sw==");
	params.put("openid.claimed_id", "http://specs.openid.net/auth/2.0/identifier_select");
	params.put("openid.sig", "tPjev37UGi1As2UdcA9T/dMOMZref9ND4dBHwa4gwT4=");

	CheckAuthenticationRequest request = new CheckAuthenticationRequest(params);
	System.out.println(request.getSignature());
	// TODO should be used in test
    }
}
