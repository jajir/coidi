package com.coroptis.coidi.conf.services.impl;

import java.io.UnsupportedEncodingException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.conf.services.CryptographyService;
import com.coroptis.coidi.conf.services.SigningService;
import com.coroptis.coidi.conf.util.Crypto;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.entities.Association;

public class SigningServiceImpl implements SigningService {

	@Inject
	private Logger logger;

	@Inject
	private CryptographyService cryptoService;

	public String sign(final AbstractMessage response, final String prefix) {
		StringBuilder buff = new StringBuilder();
		String signed = response.get(prefix + "signed");
		for (String item : signed.split(",")) {
			buff.append(response.get(getKey(prefix, item)));
			buff.append(",");
		}
		return buff.substring(0, buff.length() - 1).toString();
	}

	private String getKey(final String prefix, final String simpleKey) {
		if (prefix == null) {
			return simpleKey;
		} else {
			return prefix + simpleKey;
		}
	}

	public String sign(AbstractMessage response, Association association) {
		String toSign = sign(response, association);
		try {
			byte[] b = cryptoService.hmacSha1(
					Crypto.convertToBytes(association.getMacKey()),
					toSign.getBytes("UTF-8"));
			return Crypto.convertToString(b);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}
}
