package com.coroptis.coidi.integration.op;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.CheckAuthenticationRequest;
import com.coroptis.coidi.core.message.SetupNeededResponse;
import com.coroptis.coidi.integration.op.util.OpBindingMock;
import com.coroptis.coidi.integration.op.util.OpConfServiceImpl;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.OpenIdRequestProcessor;
import com.google.common.collect.Sets;

public class AuthenticationImmediateTest {

	private final Logger logger = LoggerFactory.getLogger(AuthenticationImmediateTest.class);

	private OpenIdRequestProcessor openIdRequestProcessor;

	private OpBindingMock mocks;

	private Map<String, String> params;

	private Association assoc;

	private Nonce nonce;

	@Test
	public void test_initialization() throws Exception {
		assertNotNull(openIdRequestProcessor);
		assertNotNull(mocks);
		assertNotNull(params);
		mocks.replay();
	}

	@Test
	public void test_user_is_not_logged() throws Exception {
		EasyMock.expect(mocks.getUserVerifier().isUserLogged(mocks.getHttpSession())).andReturn(false).times(1);
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertTrue(ret instanceof SetupNeededResponse);
		SetupNeededResponse err = (SetupNeededResponse) ret;
		assertEquals(AbstractMessage.OPENID_NS_20, err.getNameSpace());
		assertEquals(SetupNeededResponse.MODE_SETUP_NEEDED, err.get(AbstractMessage.MODE));
	}

	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void test_invalid_association() throws Exception {
		EasyMock.expect(mocks.getUserVerifier().isUserLogged(mocks.getHttpSession())).andReturn(true);
		EasyMock.expect(mocks.getUserVerifier().verify("qwe", mocks.getHttpSession())).andReturn(true);
		EasyMock.expect(mocks.getBaseAssociationDao().getByAssocHandle("cc5b843b-e375-4640-8f71-38e40b2950a6"))
				.andReturn(null);
		EasyMock.expect(mocks.getBaseAssociationDao().createNewInstance()).andReturn(assoc);
		assoc.setAssocHandle((String) EasyMock.anyObject());
		assoc.setExpiredIn((Date) EasyMock.anyObject());
		assoc.setAssociationType(AssociationType.HMAC_SHA1);
		assoc.setMacKey((String) EasyMock.anyObject());
		EasyMock.expect(mocks.getBaseNonceDao().createNewInstance()).andReturn(nonce);
		nonce.setNonce((String) EasyMock.anyObject());
		nonce.setAssociation(assoc);
		assoc.setNonces((Set<Nonce>) EasyMock.anyObject());
		EasyMock.expect(assoc.getNonces()).andReturn(new HashSet<Nonce>());
		mocks.getBaseAssociationDao().create(assoc);
		EasyMock.expect(assoc.getAssocHandle()).andReturn("cc5b843b-e375-4640-8f71-new-one");

		EasyMock.expect(mocks.getBaseAssociationDao().getByAssocHandle("cc5b843b-e375-4640-8f71-new-one"))
				.andReturn(assoc);
		EasyMock.expect(assoc.getMacKey()).andReturn("1234567890");
		EasyMock.expect(assoc.getAssociationType()).andReturn(AssociationType.HMAC_SHA1);

		EasyMock.expect(assoc.getAssociationType()).andReturn(AssociationType.HMAC_SHA1);
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertEquals(AbstractMessage.OPENID_NS_20, ret.getNameSpace());
		assertEquals(AuthenticationResponse.MODE_ID_RES, ret.getMode());
		assertNotNull(ret.get("sig"));
		assertEquals("cc5b843b-e375-4640-8f71-new-one", ret.get("assoc_handle"));
		assertEquals("cc5b843b-e375-4640-8f71-38e40b2950a6", ret.get("invalidate_handle"));
		assertNotNull(ret.get("signed"));
		Set<String> signed = Sets.newHashSet(ret.get("signed").split(","));
		assertTrue(signed.contains("assoc_handle"));
		assertTrue(signed.contains("op_endpoint"));
		assertTrue(signed.contains("identity"));
		assertTrue(signed.contains("return_to"));
		assertTrue(signed.contains("response_nonce"));
		assertTrue(signed.contains("claimed_id"));
		assertEquals("http://localhost:9090/openid", ret.get("op_endpoint"));
		assertEquals("qwe", ret.get("identity"));
		assertEquals("https://sourceforge.net/account/openid_verify.php", ret.get("return_to"));
		assertEquals("http://www.coidi.com/identity/qwe", ret.get("claimed_id"));
	}

	@Test
	public void test_all_pass() throws Exception {
		EasyMock.expect(mocks.getUserVerifier().isUserLogged(mocks.getHttpSession())).andReturn(true);
		EasyMock.expect(mocks.getUserVerifier().verify("qwe", mocks.getHttpSession())).andReturn(true);
		EasyMock.expect(mocks.getBaseAssociationDao().getByAssocHandle("cc5b843b-e375-4640-8f71-38e40b2950a6"))
				.andReturn(assoc).times(2);
		EasyMock.expect(assoc.getExpiredIn()).andReturn(nowPlusMinutes(5));
		EasyMock.expect(assoc.getMacKey()).andReturn("1234567890");
		EasyMock.expect(assoc.getAssociationType()).andReturn(AssociationType.HMAC_SHA1);
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertEquals(AbstractMessage.OPENID_NS_20, ret.getNameSpace());
		assertEquals(AuthenticationResponse.MODE_ID_RES, ret.getMode());
		assertNotNull(ret.get("sig"));
		assertEquals("cc5b843b-e375-4640-8f71-38e40b2950a6", ret.get("assoc_handle"));
		assertNotNull(ret.get("signed"));
		Set<String> signed = Sets.newHashSet(ret.get("signed").split(","));
		assertTrue(signed.contains("assoc_handle"));
		assertTrue(signed.contains("op_endpoint"));
		assertTrue(signed.contains("identity"));
		assertTrue(signed.contains("return_to"));
		assertTrue(signed.contains("response_nonce"));
		assertTrue(signed.contains("claimed_id"));
		assertEquals("http://localhost:9090/openid", ret.get("op_endpoint"));
		assertEquals("qwe", ret.get("identity"));
		assertEquals("https://sourceforge.net/account/openid_verify.php", ret.get("return_to"));
		assertEquals("http://www.coidi.com/identity/qwe", ret.get("claimed_id"));
	}

	@Test
	public void test_all_pass_identity_select() throws Exception {
		params.put("openid.identity", AuthenticationRequest.IDENTITY_SELECT);
		params.put("openid.claimed_id", AuthenticationRequest.IDENTITY_SELECT);

		EasyMock.expect(mocks.getUserVerifier().isUserLogged(mocks.getHttpSession())).andReturn(true);
		EasyMock.expect(mocks.getUserVerifier().getSelectedOpLocalIdentifier(mocks.getHttpSession())).andReturn("qwe");
		EasyMock.expect(mocks.getBaseAssociationDao().getByAssocHandle("cc5b843b-e375-4640-8f71-38e40b2950a6"))
				.andReturn(assoc).times(2);
		EasyMock.expect(assoc.getExpiredIn()).andReturn(nowPlusMinutes(5));
		EasyMock.expect(assoc.getMacKey()).andReturn("1234567890");
		EasyMock.expect(assoc.getAssociationType()).andReturn(AssociationType.HMAC_SHA1);
		mocks.replay();
		AbstractMessage ret = openIdRequestProcessor.process(params, mocks.getHttpSession());

		logger.debug(ret.getMessage());
		assertEquals(AbstractMessage.OPENID_NS_20, ret.getNameSpace());
		assertEquals(AuthenticationResponse.MODE_ID_RES, ret.getMode());
		assertNotNull(ret.get("sig"));
		assertEquals("cc5b843b-e375-4640-8f71-38e40b2950a6", ret.get("assoc_handle"));
		assertNotNull(ret.get("signed"));
		Set<String> signed = Sets.newHashSet(ret.get("signed").split(","));
		assertTrue(signed.contains("assoc_handle"));
		assertTrue(signed.contains("op_endpoint"));
		assertTrue(signed.contains("identity"));
		assertTrue(signed.contains("return_to"));
		assertTrue(signed.contains("response_nonce"));
		assertTrue(signed.contains("claimed_id"));
		assertEquals("http://localhost:9090/openid", ret.get("op_endpoint"));
		assertEquals("qwe", ret.get("identity"));
		assertEquals("https://sourceforge.net/account/openid_verify.php", ret.get("return_to"));
		assertEquals("http://www.coidi.com/identity/qwe", ret.get("claimed_id"));
	}

	@Before
	public void setUp() {
		params = new HashMap<String, String>();
		params.put(OpenIdDispatcher.OPENID_MODE, CheckAuthenticationRequest.MODE_CHECKID_IMMEDIATE);
		params.put("openid.ns", "http://specs.openid.net/auth/2.0");
		params.put("openid.identity", "qwe");
		params.put("openid.claimed_id", "http://www.coidi.com/identity/qwe");
		params.put("openid.assoc_handle", "cc5b843b-e375-4640-8f71-38e40b2950a6");
		params.put("openid.return_to", "https://sourceforge.net/account/openid_verify.php");
		params.put("openid.realm", "https://sourceforge.net");

		mocks = new OpBindingMock(new OpConfServiceImpl("op_application.properties"));
		openIdRequestProcessor = mocks.getOpenIdRequestProcessor();

		assoc = mocks.getAssociation();
		nonce = mocks.getNonce();
	}

	@After
	public void tearDown() {
		mocks.verify();
		mocks = null;
		openIdRequestProcessor = null;
		assoc = null;
		nonce = null;
	}

	private Date nowPlusMinutes(int minutes) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, minutes);
		return cal.getTime();
	}

}
