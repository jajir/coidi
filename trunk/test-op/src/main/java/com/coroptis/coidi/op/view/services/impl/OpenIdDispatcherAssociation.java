package com.coroptis.coidi.op.view.services.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.op.view.entities.Association;
import com.coroptis.coidi.op.view.services.AbstractOpenIdResponse;
import com.coroptis.coidi.op.view.services.AssociationService;
import com.coroptis.coidi.op.view.services.CryptoService;
import com.coroptis.coidi.op.view.services.OpenIdDispatcher;
import com.coroptis.coidi.op.view.services.OpenIdRequestAssociation;
import com.coroptis.coidi.op.view.services.OpenIdResponseAssociation;
import com.coroptis.coidi.op.view.utils.Crypto;
import com.coroptis.coidi.op.view.utils.CryptoSession;
import com.coroptis.coidi.op.view.utils.KeyPair;

public class OpenIdDispatcherAssociation implements OpenIdDispatcher {

	@Inject
	private CryptoService cryptoService;

	private final static Logger logger = Logger
			.getLogger(OpenIdDispatcherAssociation.class);

	private final static String OPENID_ASSOCIATION_TYPE = "openid.assoc_type";
	private final static String OPENID_SESSION_TYPE = "openid.session_type";
	private final static String OPENID_DH_MODULUS = "openid.dh_modulus";
	private final static String OPENID_DH_GENERATOR = "openid.dh_gen";
	private final static String OPENID_DH_CONSUMER_PUBLIC = "openid.dh_consumer_public";

	@Inject
	private AssociationService associationService;

	@Override
	public AbstractOpenIdResponse process(Map<String, String> requestParams) {
		if (requestParams.get(MODE).equals(MODE_ASSOCIATE)) {

			OpenIdRequestAssociation request = new OpenIdRequestAssociation();
			request.setAssociationType(Association.AssociationType
					.convert(requestParams.get(OPENID_ASSOCIATION_TYPE)));
			request.setSessionType(Association.SessionType
					.convert(requestParams.get(OPENID_SESSION_TYPE)));

			request.setDhModulo(AssociationService.DEFAULT_MODULUS);
			if (requestParams.get(OPENID_DH_MODULUS) != null) {
				request.setDhModulo(Crypto
						.convertToBigIntegerFromString(requestParams
								.get(OPENID_DH_MODULUS)));
			}

			request.setDhGen(AssociationService.DEFAULT_GENERATOR);
			if (requestParams.get(OPENID_DH_GENERATOR) != null) {
				request.setDhGen(Crypto
						.convertToBigIntegerFromString(requestParams
								.get(OPENID_DH_GENERATOR)));
			}

			request.setDhConsumerPublic(Crypto
					.convertToBigIntegerFromString(requestParams
							.get(OPENID_DH_CONSUMER_PUBLIC)));

			// generate association

			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MINUTE, 30);

			Association a = new Association();
			a.setAssocHandle(cryptoService.generateUUID());
			a.setSessionType(request.getSessionType());
			a.setExpiredIn(cal.getTime());

			OpenIdResponseAssociation out = new OpenIdResponseAssociation();
			if (request.getSessionType().equals(
					Association.SessionType.no_encription)) {
				a.setMacKey(Crypto.convertToString(cryptoService
						.generateAssociationRandom(a.getAssociationType())));
				logger.info("No encription was setup during association request/response.");
			} else {
				a.setMacKey(Crypto.convertToString(cryptoService
						.generateSessionRandom(a.getSessionType())));
				CryptoSession diffieHellman = new CryptoSession(
						request.getDhModulo(), request.getDhGen());
				// TODO refactor it
				Crypto crypto = new Crypto();
				KeyPair keyPair = cryptoService.generateCryptoSession(request);
				crypto.setDiffieHellman(diffieHellman);
				byte[] encryptedPublicKey = crypto.encryptSecret(
						request.getDhConsumerPublic(),
						Crypto.convertToBytes(a.getMacKey()));
				out.put("enc_mac_key",
						Crypto.convertToString(encryptedPublicKey));
				out.put("dh_server_public",
						Crypto.convertToString(crypto.getPublicKey()));
			}

			a.setAssociationType(request.getAssociationType());
			associationService.create(a);

			Long seconds = (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000L;

			out.put("ns", OPENID_NS_20);
			out.put("session_type", a.getSessionType().getName());
			out.put("assoc_type", a.getAssociationType().getName());
			out.put("assoc_handle", a.getAssocHandle());
			out.put("expires_in", seconds.toString());
			return out;
		} else {
			return null;
		}
	}

}
