package com.coroptis.coidi.op.view.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.view.services.AssociationService;
import com.coroptis.coidi.op.view.services.NonceService;
import com.coroptis.coidi.op.view.services.OpenIdDispatcher;
import com.coroptis.coidi.op.view.utils.Crypto;

public class OpenidDispatcherAuthentication implements OpenIdDispatcher {

	@Inject
	private Logger logger;

	@Inject
	private NonceService nonceService;

	@Inject
	private AssociationService associationService;

	@Override
	public AbstractOpenIdResponse process(Map<String, String> requestParams) {
		if (requestParams.get(MODE).equals(MODE_CHECKID_IMMEDIATE)) {
			AuthenticationRequest authenticationRequest = new AuthenticationRequest();
			try {
				authenticationRequest.setAssocHandle(URLDecoder.decode(
						requestParams.get("openid.assoc_handle"), "UTF-8"));
				authenticationRequest.setIdentity(URLDecoder.decode(
						requestParams.get("openid.identity"), "UTF-8"));
				authenticationRequest.setMode(URLDecoder.decode(
						requestParams.get("openid.mode"), "UTF-8"));
				authenticationRequest.setRealm(URLDecoder.decode(
						requestParams.get("openid.realm"), "UTF-8"));
				authenticationRequest.setReturnTo(URLDecoder.decode(
						requestParams.get("openid.return_to"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				throw new CoidiException(e.getMessage(), e);
			}

			Association association = associationService
					.getByAssocHandle(authenticationRequest.getAssocHandle());
			if (association == null) {
				ErrorResponse errorResponse = new ErrorResponse(false);
				String msg = "Unable to find association handle '"
						+ authenticationRequest.getAssocHandle() + "'";
				logger.warn(msg);
				errorResponse.setError(msg);
				return errorResponse;
			}

			AuthenticationResponse response = new AuthenticationResponse();
			response.setAssocHandle(authenticationRequest.getAssocHandle());
			response.setIdentity(authenticationRequest.getIdentity());
			response.setNonce(nonceService.createNonce());
			response.setReturnTo(authenticationRequest.getReturnTo());
			response.setSigned("assoc_handle,identity,nonce,return_to");
			response.setSignature(sign(response, association));
			response.put("go_to", authenticationRequest.getReturnTo());
			return response;
		}
		return null;
	}

	public String sign(AuthenticationResponse response, Association association) {

		StringBuilder buff = new StringBuilder();
		buff.append("openid.assoc_handle:");
		buff.append(response.getAssocHandle());
		buff.append("\n");
		buff.append("openid.identity:");
		buff.append(response.getIdentity());
		buff.append("\n");
		buff.append("openid.nonce:");
		buff.append(response.getNonce());
		buff.append("\n");
		buff.append("openid.return_to:");
		buff.append(response.getReturnTo());

		try {
			byte[] b = Crypto.hmacSha1(Crypto.convertToBytes(association
					.getMacKey()), buff.toString().getBytes("UTF-8"));
			String signature = Crypto.convertToString(b);
			return signature;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}
}
