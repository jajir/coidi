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
