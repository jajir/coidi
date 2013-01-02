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

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.ServiceBinder;

import com.coroptis.coidi.op.dao.StatelessModeNonceDao;
import com.coroptis.coidi.op.dao.UserDao;
import com.coroptis.coidi.op.dao.impl.StatelessModeNonceDaoImpl;
import com.coroptis.coidi.op.dao.impl.UserDaoImpl;
import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;
import com.coroptis.coidi.op.services.impl.IdentityServiceImpl;

/**
 * Testing application module. This Testing module load DAO application layer.
 * Database configuration and initialization isn't perform here. It have to be
 * done outside of this module.
 * <p>
 * Can be used for DAO testing.
 * </p>
 * 
 * @author jan
 * 
 */
public class DaoTestAppModule {

	private final static Logger logger = Logger
			.getLogger(DaoTestAppModule.class);

	public static void bind(ServiceBinder binder) {
		logger.debug("Starting test app module ....");
		binder.bind(UserDao.class, UserDaoImpl.class);
		binder.bind(IdentityService.class, IdentityServiceImpl.class);
		binder.bind(AssociationService.class, AssociationServiceImpl.class);
		binder.bind(StatelessModeNonceDao.class,
				StatelessModeNonceDaoImpl.class);
	}

}
