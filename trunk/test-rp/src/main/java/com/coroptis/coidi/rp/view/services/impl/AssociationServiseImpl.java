package com.coroptis.coidi.rp.view.services.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
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
import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.entities.AssociationBean;
import com.coroptis.coidi.rp.view.services.AssociationServise;
import com.coroptis.coidi.rp.view.services.HttpService;
import com.coroptis.coidi.rp.view.util.Convert;
import com.coroptis.coidi.rp.view.util.DiffieHellman;
import com.google.common.base.Preconditions;

public class AssociationServiseImpl implements AssociationServise {

	private final Logger logger = Logger
			.getLogger(AssociationServiseImpl.class);

	@Inject
	private HttpService httpService;

	@Override
	public Association generateAssociation(final String opEndpoint) {
		Preconditions.checkNotNull(opEndpoint, "opEndpoint");
		logger.debug("Creating associatio at '" + opEndpoint + "'");
		try {

			DiffieHellman dh = DiffieHellman.getDefault();

			Map<String, String> authRequest = new HashMap<String, String>();
			authRequest.put("openid.ns", "http://specs.openid.net/auth/2.0");
			authRequest.put("openid.mode", "associate");
			authRequest.put("openid.assoc_type", "HMAC-SHA1");
			authRequest.put("openid.session_type", "DH-SHA1");
			authRequest.put("openid.dh_consumer_public",
					Convert.convertToString(dh.getPublicKey()));
			authRequest.put("openid.dh_gen",
					Convert.convertToString(dh.getGenerator()));
			authRequest.put("openid.dh_modulus",
					Convert.convertToString(dh.getModulus()));
			authRequest.put("openid.mode", "associate");

			HttpPost post = new HttpPost(opEndpoint);
			post.getParams().setBooleanParameter(
					AllClientPNames.HANDLE_REDIRECTS, false);
			post.setEntity(new UrlEncodedFormEntity(toList(authRequest),
					"UTF-8"));
			HttpResponse httpResponse = httpService.getHttpClient().execute(
					post);
			String resp = EntityUtils.toString(httpResponse.getEntity());
			String statusLine = httpResponse.getStatusLine().getReasonPhrase();
			System.out.println(statusLine);
			System.out.println(resp);

			Map<String, String> map = convertToMap(resp);
			AssociationBean association = new AssociationBean();
			association.setAssocHandle(map.get("assoc_handle"));
			association.setAssociationType(AssociationType.convert(map
					.get("assoc_type")));
			association.setSessionType(SessionType.convert(map
					.get("session_type")));
			association.setExpiredIn(getExpireIn(map.get("expires_in")));
			// TODO
			BigInteger serverPublic = Convert.convertToBigInteger(map
					.get("dh_server_public"));
			byte[] encMacKey = Convert.convertToBytes(map.get("enc_mac_key"));
			byte[] macKey = null;
			try {
				macKey = dh.xorSecret(serverPublic, encMacKey);
			} catch (NoSuchAlgorithmException e) {
				logger.error(e.getMessage(), e);
				throw new CoidiException(e.getMessage(), e);
			}
			association.setMacKey(Convert.convertToString(macKey));
			association.setAssocHandle(map.get("assoc_handle"));
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
