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
package com.coroptis.coidi.core.services;

import java.util.Map;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.util.AbstractIntegrationTest;

public class IntegrationTest extends AbstractIntegrationTest {

    public void testClient() throws Exception {
	ConfigurationService configurationService = getService(ConfigurationService.class);

	Map<String, String> conf = configurationService.loadDefaultConfiguration("test");
	assertEquals("George", conf.get("name"));
    }

    public void testSymbolProvider_name() throws Exception {
	TestService testService = getService(TestService.class);

	assertEquals("George", testService.getName());
    }

    public void testSymbolProvider_surname() throws Exception {
	TestService testService = getService(TestService.class);

	assertEquals("Valentine", testService.getSurname());
    }

    public void testSymbolProvider_favoriteColor() throws Exception {
	TestService testService = getService(TestService.class);

	assertEquals("black", testService.getFavoriteColor());
    }
}
