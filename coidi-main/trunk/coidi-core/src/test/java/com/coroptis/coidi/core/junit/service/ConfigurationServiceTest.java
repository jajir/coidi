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

import java.util.Map;

import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.services.impl.ConfigurationServiceImpl;
import com.coroptis.coidi.core.util.BaseJunitTest;

public class ConfigurationServiceTest extends BaseJunitTest {

    private final static String SERVICE_NAME = "realService";

    private ConfigurationService configurationService;

    public void testGetConfigurationDirectory() throws Exception {
	services.replay();
	String ret = configurationService.getConfigurationDirectory();

	assertNotNull(ret);
	assertEquals("./META-INF/", ret);
    }

    public void testGetDefaultConfiguration() throws Exception {
	services.replay();
	Resource ret = configurationService.getDefaultConfiguration();

	assertNotNull(ret);
	assertEquals("configuration-junit.xml", ret.getFile());
	assertEquals("./META-INF/configuration-junit.xml", ret.getPath());
    }

    public void testGetProperty() throws Exception {
	services.replay();
	String ret = configurationService.getProperty("test.surname");

	assertNotNull(ret);
	assertEquals("Valentine", ret);
    }

    public void testGetProperty_notFound() throws Exception {
	services.replay();
	String ret = configurationService.getProperty("wtest.birthday");

	assertNull(ret);
    }

    public void testGetPropertyInt() throws Exception {
	services.replay();
	Integer ret = configurationService.getPropertyInt("test.age");

	assertNotNull(ret);
	assertEquals(Integer.valueOf(32), ret);
    }

    public void testGetPropertyInt_notFound() throws Exception {
	services.replay();
	Integer ret = configurationService.getPropertyInt("test.height");

	assertNull(ret);
    }

    public void testGetServerRole() throws Exception {
	services.replay();
	String ret = configurationService.getServerRole();

	assertEquals("junit", ret);
    }

    public void testLoadDefaultConfiguration() throws Exception {
	services.replay();
	Map<String, String> ret = configurationService.loadDefaultConfiguration("test");

	assertEquals("32", ret.get("age"));
    }

    @Override
    public void bind(ServiceBinder binder) {
	binder.bind(ConfigurationService.class, ConfigurationServiceImpl.class)
		.withId(SERVICE_NAME);
    }

    @Override
    protected void setUp() throws Exception {
	System.setProperty("system.property.configuration.directory", "./some-conf-dir");
	super.setUp();
	configurationService = getService(SERVICE_NAME, ConfigurationService.class);
    }

    @Override
    protected void tearDown() throws Exception {
	services.verify();
	configurationService = null;
	super.tearDown();
    }
}
