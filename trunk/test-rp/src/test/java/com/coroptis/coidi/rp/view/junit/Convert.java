//
// (C) Copyright 2007 VeriSign, Inc.  All Rights Reserved.
//
// VeriSign, Inc. shall have no responsibility, financial or
// otherwise, for any consequences arising out of the use of
// this material. The program material is provided on an "AS IS"
// BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
// express or implied.
//
// Distributed under an Apache License
// http://www.apache.org/licenses/LICENSE-2.0
//

package com.coroptis.coidi.rp.view.junit;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;

/**
 * Implements the cryptography needed for OpenID.
 */
public class Convert {

	/**
	 * Converts bytes to string.
	 * 
	 * @param b
	 *            the bytes to encode.
	 * @return the base 64 encoded string.
	 */
	public static String convertToString(byte[] b) {
		return new String(Base64.encodeBase64(b));
	}

	/**
	 * Converts a big integer to string.
	 * 
	 * @param b
	 *            the big integer to convert.
	 * @return the base 64 encoded string.
	 */
	public static String convertToString(BigInteger b) {
		return convertToString(b.toByteArray());
	}

}
