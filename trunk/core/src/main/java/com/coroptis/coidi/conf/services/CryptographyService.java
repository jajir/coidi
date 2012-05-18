package com.coroptis.coidi.conf.services;

public interface CryptographyService {

	byte[] hmacSha1(byte[] key, byte[] text);

}
