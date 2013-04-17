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
package com.coroptis.coidi.core.util;

import org.easymock.EasyMock;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.CryptographyService;
import com.coroptis.coidi.core.services.MessageService;

public class Services {

    private final ConfigurationService configurationService = EasyMock
	    .createMock(ConfigurationService.class);
    private final ConvertorService convertorService = EasyMock.createMock(ConvertorService.class);
    private final CryptographyService cryptographyService = EasyMock
	    .createMock(CryptographyService.class);
    private final MessageService messageService = EasyMock.createMock(MessageService.class);

    private final Object[] mocks = new Object[] { getConfigurationService(), getConvertorService(),
	    getCryptographyService(), getMessageService() };

    private static Services services;

    public static Services getServices() {
	if (services == null) {
	    services = new Services();
	}
	return services;
    }

    private Services() {
    }

    public void reset() {
	for (Object object : mocks) {
	    EasyMock.reset(object);
	}
    }

    public void verify() {
	for (Object object : mocks) {
	    EasyMock.verify(object);
	}
    }

    public void replay() {
	for (Object object : mocks) {
	    EasyMock.replay(object);
	}
    }

    /**
     * @return the configurationService
     */
    public ConfigurationService getConfigurationService() {
	return configurationService;
    }

    /**
     * @return the convertorService
     */
    public ConvertorService getConvertorService() {
	return convertorService;
    }

    /**
     * @return the cryptographyService
     */
    public CryptographyService getCryptographyService() {
	return cryptographyService;
    }

    /**
     * @return the messageService
     */
    public MessageService getMessageService() {
	return messageService;
    }
}