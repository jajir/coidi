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
package com.coroptis.coidi.op.util;

import org.easymock.EasyMock;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.dao.StatelessModeNonceDao;
import com.coroptis.coidi.op.services.StatelessModeNonceService;

public class Services {

	private final StatelessModeNonceDao statelessModeNonceDao = EasyMock
			.createMock(StatelessModeNonceDao.class);
	private final ConfigurationService configurationService = EasyMock
			.createMock(ConfigurationService.class);
	private final NonceService nonceService = EasyMock
			.createMock(NonceService.class);
	private final AssociationDao associationDao = EasyMock
			.createMock(AssociationDao.class);
	private final SigningService signingService = EasyMock
			.createMock(SigningService.class);
	private final StatelessModeNonceService statelessModeNonceService = EasyMock
			.createMock(StatelessModeNonceService.class);

	private final Object[] mocks = new Object[] { getStatelessModeNonceDao(),
			getConfigurationService(), getNonceService(), getAssociationDao(),
			getSigningService(), getStatelessModeNonceService() };

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
	 * @return the statelessModeNonceDao
	 */
	public StatelessModeNonceDao getStatelessModeNonceDao() {
		return statelessModeNonceDao;
	}

	/**
	 * @return the nonceService
	 */
	public NonceService getNonceService() {
		return nonceService;
	}

	/**
	 * @return the associationDao
	 */
	public AssociationDao getAssociationDao() {
		return associationDao;
	}

	/**
	 * @return the signingService
	 */
	public SigningService getSigningService() {
		return signingService;
	}

	/**
	 * @return the statelessModeNonceService
	 */
	public StatelessModeNonceService getStatelessModeNonceService() {
		return statelessModeNonceService;
	}
}