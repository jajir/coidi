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
package com.coroptis.coidi.op.junit.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.ErrorResponse;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.impl.NegativeResponseGeneratorImpl;

public class NegativeResponseGeneratorTest {

    private NegativeResponseGenerator service;

    private OpConfigurationService conf;

    @Test
    public void test_buildError() throws Exception {
	ErrorResponse ret = service.buildError("some error");

	assertNotNull(ret);
	assertEquals("some error", ret.getError());
	assertEquals(false, ret.isUrl());
	assertEquals("john@gmail.com", ret.getContact());
	assertEquals(AbstractOpenIdResponse.OPENID_NS_20, ret.getNameSpace());
    }

    @Test(expected = NullPointerException.class)
    public void test_buildError_messageIsNull() throws Exception {
	 service.buildError((String[])null);
    }

    @Before
    public void setUp() throws Exception {
	conf = EasyMock.createMock(OpConfigurationService.class);
	EasyMock.expect(conf.getErrorContact()).andReturn("john@gmail.com");
	EasyMock.replay(conf);
	service = new NegativeResponseGeneratorImpl(conf);
    }

    @After
    public void tearDown() throws Exception {
	EasyMock.verify(conf);
	conf = null;
	service = null;
    }
}
