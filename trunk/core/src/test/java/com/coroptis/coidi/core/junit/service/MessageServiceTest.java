package com.coroptis.coidi.core.junit.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.MessageService;
import com.coroptis.coidi.core.services.impl.MessageServiceImpl;
import com.coroptis.coidi.core.util.BaseJunitTest;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.AssociationBean;

public class MessageServiceTest extends BaseJunitTest {

	private final Logger logger = Logger.getLogger(MessageServiceTest.class);

	private final static String SERVICE_NAME = "realService";

	private MessageService messageService;

	public void testSign() throws Exception {
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("openid.ns", "http://specs.openid.net/auth/2.0");
		msg.put("openid.op_endpoint", "http://server.myid.net/server");
		msg.put("openid.claimed_id", "http://jajir.myid.net/");
		msg.put("openid.response_nonce", "2012-06-21T19:04:45Z0620");
		msg.put("openid.mode", "id_res");
		msg.put("openid.identity", "http://jajir.myid.net/");
		msg.put("openid.return_to", "http://rp.coroptis.com/");
		msg.put("openid.assoc_handle", "f12de3676a78671d");
		msg.put("openid.signed",
				"op_endpoint,claimed_id,identity,return_to,response_nonce,assoc_handle,mode");
		msg.put("openid.sig", "QEdlMNuTjckbVxqh25Mf90nBCe0=");
		AuthenticationResponse response = new AuthenticationResponse(msg);
		AssociationBean association = new AssociationBean();
		association.setMacKey("3uAPFsOq3UYQ3v3SeujCZaNclIg=");
		association.setAssociationType(AssociationType.HMAC_SHA1);
		services.replay();
		String sign = messageService.extractStringForSign(response, null);

		logger.debug("computed: '" + sign + "'");
	}

	@Override
	public void bind(ServiceBinder binder) {
		binder.bind(MessageService.class, MessageServiceImpl.class).withId(
				SERVICE_NAME);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		messageService = getService(SERVICE_NAME, MessageService.class);
	}

	@Override
	protected void tearDown() throws Exception {
		services.verify();
		messageService = null;
		super.tearDown();
	}
}
