package com.coroptis.coidi.integration.op;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.services.NonceTool;
import com.coroptis.coidi.core.services.impl.NonceToolImpl;
import com.coroptis.coidi.integration.op.util.OpBindingMock;
import com.coroptis.coidi.integration.op.util.OpConfServiceImpl;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;

public class CheckAuthenticationTest {

	private final Logger logger = LoggerFactory.getLogger(CheckAuthenticationTest.class);

	private OpenIdRequestProcessor openIdRequestProcessor;

	private OpBindingMock mocks;

	private Map<String, String> params;

	private String now_nonce;

	@Test
	public void test_initialization() throws Exception {
		assertNotNull(openIdRequestProcessor);
		assertNotNull(mocks);
		assertNotNull(params);
		mocks.replay();
	}

	@Test
	public void test_missing_nonce() throws Exception {
		params.remove("openid.response_nonce");
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertEquals("false", ret.get("is_valid"));
	}

	@Test
	public void test_nonce_is_assigned_with_shared_association() throws Exception {
		params.put("openid.response_nonce", now_nonce);
		EasyMock.expect(mocks.getBaseNonceDao().getByNonce(now_nonce)).andReturn(mocks.getNonce());
		EasyMock.expect(mocks.getNonce().getAssociation()).andReturn(mocks.getAssociation()).times(2);
		EasyMock.expect(mocks.getAssociation().getSessionType()).andReturn(SessionType.DH_SHA256);
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertEquals("false", ret.get("is_valid"));
	}

	@Test
	public void test_shared_association() throws Exception {
		params.put("openid.response_nonce", now_nonce);
		EasyMock.expect(mocks.getBaseNonceDao().getByNonce(now_nonce)).andReturn(mocks.getNonce());
		EasyMock.expect(mocks.getNonce().getAssociation()).andReturn(mocks.getAssociation()).times(2);
		EasyMock.expect(mocks.getAssociation().getSessionType()).andReturn(null);
		EasyMock.expect(mocks.getBaseAssociationDao().getByAssocHandle("1efbd96c-6112-4ebb-ae79-d451e7a1f455"))
				.andReturn(mocks.getAssociation());
		EasyMock.expect(mocks.getAssociation().getSessionType()).andReturn(SessionType.DH_SHA1);
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertEquals("false", ret.get("is_valid"));
	}

	@Test
	public void test_invalid_signature() throws Exception {
		params.put("openid.response_nonce", now_nonce);
		EasyMock.expect(mocks.getBaseNonceDao().getByNonce(now_nonce)).andReturn(mocks.getNonce());
		EasyMock.expect(mocks.getNonce().getAssociation()).andReturn(mocks.getAssociation()).times(4);
		EasyMock.expect(mocks.getAssociation().getSessionType()).andReturn(null);
		EasyMock.expect(mocks.getBaseAssociationDao().getByAssocHandle("1efbd96c-6112-4ebb-ae79-d451e7a1f455"))
				.andReturn(mocks.getAssociation());
		EasyMock.expect(mocks.getAssociation().getSessionType()).andReturn(null);
		EasyMock.expect(mocks.getAssociation().getAssocHandle()).andReturn("hello").times(2);

		EasyMock.expect(mocks.getSigningService().sign((CheckAuthenticationRequest) EasyMock.anyObject(),
				EasyMock.eq(mocks.getAssociation()))).andReturn("A=");
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertEquals("false", ret.get("is_valid"));
	}

	@Test
	public void test_all_pass() throws Exception {
		params.put("openid.response_nonce", now_nonce);
		EasyMock.expect(mocks.getBaseNonceDao().getByNonce(now_nonce)).andReturn(mocks.getNonce());
		EasyMock.expect(mocks.getNonce().getAssociation()).andReturn(mocks.getAssociation()).times(4);
		EasyMock.expect(mocks.getAssociation().getSessionType()).andReturn(null);
		EasyMock.expect(mocks.getBaseAssociationDao().getByAssocHandle("1efbd96c-6112-4ebb-ae79-d451e7a1f455"))
				.andReturn(mocks.getAssociation()).times(2);
		EasyMock.expect(mocks.getAssociation().getSessionType()).andReturn(null);
		EasyMock.expect(mocks.getAssociation().getAssocHandle()).andReturn("hello").times(2);

		EasyMock.expect(mocks.getSigningService().sign((CheckAuthenticationRequest) EasyMock.anyObject(),
				EasyMock.eq(mocks.getAssociation()))).andReturn("WOMRLznYVB3dscSLF4APl9xUXck=");
		mocks.getBaseAssociationDao().delete(mocks.getAssociation());
		mocks.getBaseNonceDao().delete(mocks.getNonce());
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertEquals("true", ret.get("is_valid"));
	}

	@Test
	public void test_all_invalid_association() throws Exception {
	    //FIXME 
		params.put("openid.response_nonce", now_nonce);
		EasyMock.expect(mocks.getBaseNonceDao().getByNonce(now_nonce)).andReturn(mocks.getNonce());
		EasyMock.expect(mocks.getNonce().getAssociation()).andReturn(mocks.getAssociation()).times(4);
		EasyMock.expect(mocks.getAssociation().getSessionType()).andReturn(null);
		EasyMock.expect(mocks.getBaseAssociationDao().getByAssocHandle("1efbd96c-6112-4ebb-ae79-d451e7a1f455"))
				.andReturn(mocks.getAssociation()).times(2);
		EasyMock.expect(mocks.getAssociation().getSessionType()).andReturn(null);
		EasyMock.expect(mocks.getAssociation().getAssocHandle()).andReturn("hello").times(2);

		EasyMock.expect(mocks.getSigningService().sign((CheckAuthenticationRequest) EasyMock.anyObject(),
				EasyMock.eq(mocks.getAssociation()))).andReturn("WOMRLznYVB3dscSLF4APl9xUXck=");
		mocks.getBaseAssociationDao().delete(mocks.getAssociation());
		mocks.getBaseNonceDao().delete(mocks.getNonce());
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertEquals("true", ret.get("is_valid"));
	}

	@Before
	public void setUp() {
		mocks = new OpBindingMock(new OpConfServiceImpl("op_application.properties"));
		mocks.mockSigningService();
		openIdRequestProcessor = mocks.getOpenIdRequestProcessor();
		NonceTool nonceTool = new NonceToolImpl(mocks.getConvertorService());
		now_nonce = nonceTool.createNonce();

		params = new HashMap<String, String>();
		params.put("openid.ns", "http://specs.openid.net/auth/2.0");
		params.put("openid.mode", "check_authentication");
		params.put("openid.assoc_handle", "1efbd96c-6112-4ebb-ae79-d451e7a1f455");
		params.put("openid.signed", "op_endpoint,identity,return_to,response_nonce,claimed_id,assoc_handle");
		params.put("openid.sig", "WOMRLznYVB3dscSLF4APl9xUXck=");
		params.put("openid.op_endpoint", "http://sso.dev1.equabank.loc/openid");
		params.put("openid.identity", "broker1");
		params.put("openid.return_to", "http://brokerportal.dev1.equabank.loc/sso/examples/example.php");
		params.put("openid.response_nonce", "2016-03-02T15:19:20Z+pqi0lhyAsirsg==");
		params.put("openid.claimed_id", "broker1");
	}

	@After
	public void tearDown() {
		mocks.verify();
		mocks = null;
		now_nonce = null;
		openIdRequestProcessor = null;
	}

}
