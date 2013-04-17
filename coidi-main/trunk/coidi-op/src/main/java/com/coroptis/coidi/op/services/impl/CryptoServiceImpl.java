/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.op.services.impl;

import java.util.Random;

import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.op.services.CryptoService;

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

    private String padHex(final long l, final int n) {
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

	return padHex(((msb & 0xFFFFFFFF00000000L) >> 32) & 0xFFFFFFFFL, 8) + "-"
		+ padHex(((msb & 0xFFFF0000L) >> 16), 4) + "-"
		+ padHex((msb & 0x0000000000000000FFFFL), 4) + "-"
		+ padHex((((lsb & 0xFFFF000000000000L) >> 48) & 0xFFFF), 4) + "-"
		+ padHex(lsb & 0xFFFFFFFFFFFFL, 12);
    }

}
