package com.coroptis.coidi.core.services;

import java.math.BigInteger;

import com.coroptis.coidi.core.util.KeyPair;
import com.coroptis.coidi.op.entities.Association.SessionType;

public interface CryptographyService {

	public static final BigInteger DEFAULT_MODULUS = new BigInteger(
			"1551728981814736974712322577637155399157248019669"
					+ "154044797077953140576293785419175806512274236981"
					+ "889937278161526466314385615958256881888899512721"
					+ "588426754199503412587065565498035801048705376814"
					+ "767265132557470407658574792912915723345106432450"
					+ "947150072296210941943497839259847603755949858482"
					+ "53359305585439638443");

	public static final BigInteger DEFAULT_GENERATOR = BigInteger.valueOf(2);

	byte[] hmacSha1(byte[] key, byte[] text);

	byte[] encryptSecret(KeyPair keyPair, BigInteger dhConsumerPublic,
			byte[] macKey,
			SessionType sessionType);

	byte[] computeDigest(final byte[] text, final SessionType sessionType);

}
