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

import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.core.services.ConfigurationService;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.dao.BaseAssociationDao;
import com.coroptis.coidi.op.dao.BaseUserDao;
import com.coroptis.coidi.op.services.AuthenticationProcessor;
import com.coroptis.coidi.op.services.AuthenticationService;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.NegativeResponseGenerator;
import com.coroptis.coidi.op.services.StatelessModeNonceService;
import com.coroptis.coidi.op.view.dao.UserDao;
import com.coroptis.coidi.test.AdditionalBinderProvider;
import com.coroptis.coidi.test.EasyMockServicebuilder;

public class JunitAppModule {

    public static void bind(ServiceBinder binder) {
	/**
	 * Additional bindings
	 */
	AdditionalBinderProvider.bind(binder);

	Services services = Services.getServices();

	binder.bind(ConfigurationService.class, new EasyMockServicebuilder<ConfigurationService>(
		services.getConfigurationService()));
	binder.bind(NonceService.class,
		new EasyMockServicebuilder<NonceService>(services.getNonceService()));
	binder.bind(BaseAssociationDao.class, new EasyMockServicebuilder<BaseAssociationDao>(
		services.getAssociationDao()));
	binder.bind(AuthenticationService.class, new EasyMockServicebuilder<AuthenticationService>(
		services.getAuthenticationService()));
	binder.bind(IdentityService.class,
		new EasyMockServicebuilder<IdentityService>(services.getIdentityService()));
	binder.bind(
		NegativeResponseGenerator.class,
		new EasyMockServicebuilder<NegativeResponseGenerator>(services
			.getNegativeResponseGenerator()));
	binder.bind(SigningService.class,
		new EasyMockServicebuilder<SigningService>(services.getSigningService()));
	binder.bind(
		StatelessModeNonceService.class,
		new EasyMockServicebuilder<StatelessModeNonceService>(services
			.getStatelessModeNonceService()));
	binder.bind(
		AuthenticationProcessor.class,
		new EasyMockServicebuilder<AuthenticationProcessor>(services
			.getAuthenticationProcessor()));
	binder.bind(BaseUserDao.class,
		new EasyMockServicebuilder<BaseUserDao>(services.getBaseUserDao()));
	binder.bind(UserDao.class, new EasyMockServicebuilder<UserDao>(services.getUserDao()));

	binder.bind(ConvertorService.class,
		new EasyMockServicebuilder<ConvertorService>(services.getConvertorService()));
    }
}
