package com.coroptis.coidi.op.view.services.impl;

import java.math.BigInteger;
import java.util.Random;

import com.coroptis.coidi.op.view.entities.Association.AssociationType;
import com.coroptis.coidi.op.view.entities.Association.SessionType;
import com.coroptis.coidi.op.view.services.CryptoService;
import com.coroptis.coidi.op.view.services.OpenIdRequestAssociation;
import com.coroptis.coidi.op.view.utils.KeyPair;

public class CryptoServiceImpl implements CryptoService {

	private final Random random;

	public CryptoServiceImpl() {
		random = new Random();
	}

	@Override
	public byte[] generateSessionRandom(SessionType sessionType) {
		byte[] result = new byte[sessionType.getSectetLength()];
		random.nextBytes(result);
		return result;
	}

	@Override
	public byte[] generateAssociationRandom(AssociationType associationType) {
		byte[] result = new byte[associationType.getSectetLength()];
		random.nextBytes(result);
		return result;
	}

	private static String padHex(final long l, final int n) {
		String s = Long.toHexString(l);
		while (s.length() < n)
			s = "0" + s;
		return s;
	}

	@Override
	public String generateUUID() {
		Random rnd = new Random();

		long lsb = rnd.nextLong();
		long msb = rnd.nextLong();

		lsb &= 0x3FFFFFFFFFFFFFFFL;
		lsb |= 0x8000000000000000L;

		msb &= 0xFFFFFFFFFFFF0FFFL;
		msb |= 0x4000; // Version 4;

		return padHex(((msb & 0xFFFFFFFF00000000L) >> 32) & 0xFFFFFFFFL, 8)
				+ "-" + padHex(((msb & 0xFFFF0000L) >> 16), 4) + "-"
				+ padHex((msb & 0x0000000000000000FFFFL), 4) + "-"
				+ padHex((((lsb & 0xFFFF000000000000L) >> 48) & 0xFFFF), 4)
				+ "-" + padHex(lsb & 0xFFFFFFFFFFFFL, 12);
	}

	@Override
	public KeyPair generateCryptoSession(OpenIdRequestAssociation association) {
		int bits = association.getDhModulo().bitLength();
		BigInteger max = association.getDhModulo().subtract(BigInteger.ONE);
		while (true) {
			BigInteger pkey = new BigInteger(bits, random);
			if (pkey.compareTo(max) >= 0) { // too large
				continue;
			} else if (pkey.compareTo(BigInteger.ONE) <= 0) {// too small
				continue;
			}
			return new KeyPair(pkey, association.getDhGen().modPow(pkey,
					association.getDhModulo()));
		}
	}

}