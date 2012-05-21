package com.coroptis.coidi.rp.view.services.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AllClientPNames;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AssociationRequest;
import com.coroptis.coidi.core.message.AssociationResponse;
import com.coroptis.coidi.core.services.CryptoSessionService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.entities.AssociationBean;
import com.coroptis.coidi.rp.view.services.AssociationServise;
import com.coroptis.coidi.rp.view.services.HttpService;
import com.coroptis.coidi.rp.view.util.Convert;
import com.google.common.base.Preconditions;

public class AssociationServiseImpl implements AssociationServise {

	@Inject
	private Logger logger;

	@Inject
	private HttpService httpService;

	@Inject
	private CryptoSessionService cryptoSessionService;

	@Override
	public Association generateAssociation(final String opEndpoint) {
		Preconditions.checkNotNull(opEndpoint, "opEndpoint");
		logger.debug("Creating associatio at '" + opEndpoint + "'");
		try {

			KeyPair keyPair = cryptoSessionService.generateCryptoSession(
					CryptographyService.DEFAULT_MODULUS,
					CryptographyService.DEFAULT_GENERATOR);
			AssociationRequest associationRequest = new AssociationRequest();
			associationRequest.setAssociationType(AssociationType.HMAC_SHA1);
			associationRequest.setSessionType(SessionType.DH_SHA1);
			associationRequest.setDhConsumerPublic(keyPair.getPublicKey());
			associationRequest.setDhGen(keyPair.getGenerator());
			associationRequest.setDhModulo(keyPair.getModulus());

			logger.debug("creating association request: " + associationRequest);

			HttpPost post = new HttpPost(opEndpoint);
			post.getParams().setBooleanParameter(
					AllClientPNames.HANDLE_REDIRECTS, false);
			post.setEntity(new UrlEncodedFormEntity(toList(associationRequest
					.getMap()), "UTF-8"));
			HttpResponse httpResponse = httpService.getHttpClient().execute(
					post);
			String resp = EntityUtils.toString(httpResponse.getEntity());
			logger.debug("response: " + resp);

			AssociationResponse associationResponse = new AssociationResponse(
					convertToMap(resp));
			AssociationBean association = new AssociationBean();
			association.setAssocHandle(associationResponse.getAssocHandle());
			association.setAssociationType(associationResponse
					.getAssociationType());
			association.setSessionType(associationResponse.getSessionType());
			association.setExpiredIn(getExpireIn(associationResponse
					.getExpiresIn()));
			byte[] macKey = cryptoSessionService.xorSecret(keyPair,
					associationResponse.getDhServerPublic(),
					associationResponse.getEncMacKey());
			association.setMacKey(Convert.convertToString(macKey));
			return association;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}

	// MATCH with OP code, should be at same place
	private Date getExpireIn(String seconds) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND,
				Integer.valueOf(Preconditions.checkNotNull(seconds, "seconds")));
		return cal.getTime();
	}

	private Map<String, String> convertToMap(String body) {
		Map<String, String> out = new HashMap<String, String>();
		for (String line : body.split("\n")) {
			int pos = line.indexOf(":");
			if (pos > 0) {
				out.put(line.substring(0, pos), line.substring(pos + 1));
			}
		}
		return out;
	}

	private List<NameValuePair> toList(Map<String, String> parameters) {
		List<NameValuePair> list = new ArrayList<NameValuePair>(
				parameters.size());
		for (Entry<String, String> entry : parameters.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return list;
	}

}
