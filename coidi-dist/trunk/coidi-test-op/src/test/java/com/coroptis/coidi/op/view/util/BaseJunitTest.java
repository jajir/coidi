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
package com.coroptis.coidi.op.view.util;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.op.services.AssociationTool;
import com.coroptis.coidi.test.AbstractJunitTest;

/**
 * 
 * @author jan
 * 
 */
public abstract class BaseJunitTest extends AbstractJunitTest {

    protected Registry registry;

    protected Services services;

    public BaseJunitTest() {
	super(JunitAppModule.class);
    }

    protected <T> T getService(Class<T> serviceInterface) {
	return registry.getService(serviceInterface);
    }

    @Override
    public void bind(ServiceBinder binder) {

    }

    @Override
    protected void setUp() throws Exception {
	System.setProperty(AssociationTool.DEFAULT_ASSOCITION_TYPE, "HMAC-SHA1");
	System.setProperty("op.server", "http://localhost:8080/");
	System.setProperty(AssociationTool.DEFAULT_TIME_TO_LIVE_IN_SECONDS, "1800");
	System.setProperty("op.err.contact", "john@gmail.com");
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
