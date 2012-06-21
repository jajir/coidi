package com.coroptis.coidi.core.junit.service;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.easymock.EasyMock;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.core.services.impl.SigningServiceImpl;
import com.coroptis.coidi.core.util.BaseJunitTest;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.AssociationBean;

public class SigningServiceTest extends BaseJunitTest {

	private final static String SERVICE_NAME = "realService";

	private SigningService signingService;

	public void testSign() throws Exception {
		byte[] bytes = "2012-06-21T19:04:45Z0620".getBytes();
		byte[] signature = "signature".getBytes();
		AuthenticationResponse response = new AuthenticationResponse();
		AssociationBean association = new AssociationBean();
		association.setMacKey("3uAPFsOq3UYQ3v3SeujCZaNclIg=");
		association.setAssociationType(AssociationType.HMAC_SHA1);
		EasyMock.expect(
				services.getMessageService().extractStringForSign(response,
						null)).andReturn("toSign");
		EasyMock.expect(
				services.getConvertorService().convertToBytes(
						"3uAPFsOq3UYQ3v3SeujCZaNclIg=")).andReturn(bytes);
		EasyMock.expect(
				services.getCryptographyService().hmacSha1(EasyMock.eq(bytes),
						(byte[]) EasyMock.anyObject(),
						EasyMock.eq(AssociationType.HMAC_SHA1))).andReturn(
				signature);
		EasyMock.expect(
				services.getConvertorService().convertToString(signature))
				.andReturn("signature");
		services.replay();
		String sig = signingService.sign(response, association);

		assertEquals("signature", sig);
		services.verify();
	}

	@Override
	public void bind(ServiceBinder binder) {
		binder.bind(SigningService.class, SigningServiceImpl.class).withId(
				SERVICE_NAME);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		signingService = getService(SERVICE_NAME, SigningService.class);
	}

	@Override
	protected void tearDown() throws Exception {
		signingService = null;
		super.tearDown();
	}
}
