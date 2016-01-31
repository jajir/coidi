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

import org.easymock.classextension.EasyMock;

import com.coroptis.coidi.core.services.ConfService;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.rp.base.DiscoveryResult;
import com.coroptis.coidi.rp.services.HttpService;
import com.coroptis.coidi.rp.services.XrdsService;

public class Services {

    /**
     * Services defined in Coidi
     */
    private final ConfService configurationService = EasyMock
	    .createMock(ConfService.class);
    private final HttpService httpService = EasyMock.createMock(HttpService.class);
    private final XrdsService xrdsService = EasyMock.createMock(XrdsService.class);
    private final ConvertorService convertorService = EasyMock.createMock(ConvertorService.class);

    /**
     * Other services
     */
    private final DiscoveryResult discoveryResult = EasyMock.createMock(DiscoveryResult.class);

    private final Object[] mocks = new Object[] { getConfigurationService(), getHttpService(),
	    getXrdsService(), getDiscoveryResult(), getConvertorService() };

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
    public ConfService getConfigurationService() {
	return configurationService;
    }

    /**
     * @return the httpService
     */
    public HttpService getHttpService() {
	return httpService;
    }

    /**
     * @return the xrdsService
     */
    public XrdsService getXrdsService() {
	return xrdsService;
    }

    /**
     * @return the discoveryResult
     */
    public DiscoveryResult getDiscoveryResult() {
	return discoveryResult;
    }

    /**
     * @return the convertorService
     */
    public ConvertorService getConvertorService() {
	return convertorService;
    }
}