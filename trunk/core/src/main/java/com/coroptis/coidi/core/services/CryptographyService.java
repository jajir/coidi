package com.coroptis.coidi.core.services;

public interface CryptographyService {

	byte[] hmacSha1(byte[] key, byte[] text);

}
