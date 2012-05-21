package com.coroptis.coidi.core.services.impl;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

import com.coroptis.coidi.core.services.ConvertorService;

public class ConvertorServiceImpl implements ConvertorService {

	@Override
	public Integer getInt(String val) {
		try {
			return Integer.valueOf(val);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@Override
	public byte[] convertToBytes(String s) {
		return Base64.decodeBase64(s.getBytes());
	}

	@Override
	public String convertToString(byte[] b) {
		return new String(Base64.encodeBase64(b));
	}

	@Override
	public String convertToString(BigInteger b) {
		return convertToString(b.toByteArray());
	}

	@Override
	public BigInteger convertToBigIntegerFromString(String s) {
		return new BigInteger(convertToBytes(s));
	}

}
