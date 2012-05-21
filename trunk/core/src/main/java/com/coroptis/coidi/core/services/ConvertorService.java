package com.coroptis.coidi.core.services;

import java.math.BigInteger;

public interface ConvertorService {

	Integer getInt(String val);

	byte[] convertToBytes(String s);

	String convertToString(byte[] b);

	String convertToString(BigInteger b);

	BigInteger convertToBigIntegerFromString(String s);
}
