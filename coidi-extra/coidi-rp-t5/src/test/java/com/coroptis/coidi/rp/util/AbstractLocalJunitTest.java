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
package com.coroptis.coidi.rp.util;

import com.coroptis.coidi.test.AbstractJunitTest;

public abstract class AbstractLocalJunitTest extends AbstractJunitTest {

    protected Services services;

    public AbstractLocalJunitTest() {
	super(JunitAppModule.class);
    }

    @Override
    protected void setUp() throws Exception {
	System.setProperty("openid.realm", "http://localhost:8080/");
	System.setProperty("proxy.server", "");
	System.setProperty("proxy.port", "-1");
	System.setProperty("proxy.userName", "");
	System.setProperty("proxy.password", "");
	
	services = Services.getServices();
	services.reset();
	super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
	services = null;
	super.tearDown();
    }
}
