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
package com.coroptis.coidi.core.junit.service;

import static org.easymock.EasyMock.expect;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.coroptis.coidi.core.services.impl.AppSymbolProvider;
import com.coroptis.coidi.core.util.BaseJunitTest;

public class AppSymbolProviderTest extends BaseJunitTest {

    private final static String SERVICE_NAME = "realService";

    private SymbolProvider symbolProvider;

    public void testValueForSymbol() throws Exception {
	expect(services.getConfigurationService().getProperty("op.ipAddress")).andReturn(
		"10.20.30.40");
	services.replay();
	String ret = symbolProvider.valueForSymbol("op.ipAddress");

	assertNotNull(ret);
	assertEquals("10.20.30.40", ret);
    }

    public void testValueForSymbol_notFound() throws Exception {
	expect(services.getConfigurationService().getProperty("op.ipAddress.notFound")).andReturn(
		null);
	services.replay();
	String ret = symbolProvider.valueForSymbol("op.ipAddress.notFound");

	assertNull(ret);
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(SymbolProvider.class, AppSymbolProvider.class).withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	symbolProvider = getService(SERVICE_NAME, SymbolProvider.class);
    }

    @Override
    protected void tearDown() throws Exception {
	services.verify();
	symbolProvider = null;
	super.tearDown();
    }
}
