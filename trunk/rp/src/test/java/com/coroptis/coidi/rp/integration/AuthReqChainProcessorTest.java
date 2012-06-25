package com.coroptis.coidi.rp.integration;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthReq;
import com.coroptis.coidi.rp.util.AbstractIntegrationTest;

public class AuthReqChainProcessorTest extends AbstractIntegrationTest {

	private Logger logger = Logger.getLogger(AuthReqChainProcessorTest.class);

	private AuthReq authReq;

	public void testBasic() throws Exception {
		assertNotNull(authReq);
	}

	public void testProcess() throws Exception {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		DiscoveryResult discoveryResult = new DiscoveryResult();
		XrdService service = new XrdService();
		service.setPriority(2);
		service.setUrl("http://localhost:8080/");
		service.getTypes().add(XrdService.TYPE_ATTRIBUTE_EXCHANGE_2_0);
		service.getTypes().add(XrdService.TYPE_OPENID_2_0);
		service.getTypes().add(XrdService.TYPE_PAPE_1_0);
		service.getTypes().add(XrdService.TYPE_SREG_1_0);
		service.getTypes().add(XrdService.TYPE_UI_ICON_1_0);
		service.getTypes().add(XrdService.TYPE_UI_POPUP_1_0);
		discoveryResult.getServices().add(service);
		authReq.applyExtension(authenticationRequest, discoveryResult,
				new HashMap<String, String>());

		logger.debug(authenticationRequest.getMessage());
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		authReq = getService(AuthReq.class, "AuthReqChainProcessor");
	}

	@Override
	protected void tearDown() throws Exception {
		authReq = null;
		super.tearDown();
	}

}
