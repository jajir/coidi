package com.coroptis.coidi.rp.integration;

import java.util.Map;

import org.apache.log4j.Logger;

import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.rp.base.AuthRespExtension;
import com.coroptis.coidi.rp.base.XrdService;
import com.coroptis.coidi.rp.services.AuthenticationService;
import com.coroptis.coidi.rp.util.AbstractIntegrationTest;

public class ProcessAuthResponseTest extends AbstractIntegrationTest {

	private final Logger logger = Logger
			.getLogger(ProcessAuthResponseTest.class);

	private String msg = "openid.ns=http://specs.openid.net/auth/2.0&openid.mode=id_res&openid.op_endpoint=https://www.google.com/accounts/o8/ud&openid.response_nonce=2012-07-01T22:51:51ZOtF5fXQpmVvSeQ&openid.return_to=http://www.trainindex.com/userProfile&openid.assoc_handle=AMlYA9XgrO-etZh-FMZ7tYvZk5EHe_pDVEEmWjyzi-AJy7m9FYDE1eMN&openid.signed=op_endpoint,claimed_id,identity,return_to,response_nonce,assoc_handle,ns.ext1,ext1.mode,ext1.type.firstname,ext1.value.firstname,ext1.type.email,ext1.value.email&openid.sig=VvM3WsuezKL5XJ1cQLpr9ERc7Os=&openid.identity=https://www.google.com/accounts/o8/id?id=AItOawlWAYnSZs5g1Lp6vMZSnK_0Giy7tyRttCI&openid.claimed_id=https://www.google.com/accounts/o8/id?id=AItOawlWAYnSZs5g1Lp6vMZSnK_0Giy7tyRttCI&openid.ns.ext1=http://openid.net/srv/ax/1.0&openid.ext1.mode=fetch_response&openid.ext1.type.firstname=http://axschema.org/namePerson/first&openid.ext1.value.firstname=Jan&openid.ext1.type.email=http://axschema.org/contact/email&openid.ext1.value.email=jirout@gmail.com";

	public void testProcess() throws Exception {
		MessageService messageService = getService(MessageService.class);
		AuthenticationService authenticationService = getService(AuthenticationService.class);
		Map<String, String> map = messageService.convertUrlToMap(msg);
		Map<String, AuthRespExtension> resp = authenticationService
				.generateResponse(map);
		logger.debug(resp.size());
		AuthRespExtension ex = resp.get(XrdService.TYPE_ATTRIBUTE_EXCHANGE_1_0);
		assertNotNull(ex);
		logger.debug(ex.getValueForName("email"));
	}

}
