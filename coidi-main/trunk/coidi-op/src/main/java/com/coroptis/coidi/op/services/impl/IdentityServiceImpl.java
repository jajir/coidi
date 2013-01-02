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
package com.coroptis.coidi.op.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.coroptis.coidi.op.dao.IdentityDao;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.services.IdentityService;

public class IdentityServiceImpl implements IdentityService {

	@Inject
	private IdentityDao identityDao;

	/**
	 * OpenID identity is stored in URL form. In database is stored just prefix
	 * part of it. For example prefix is http://www.myid.com/user/
	 */
	@Inject
	@Symbol("op.idenity.prefix")
	private String idenityPrefix;

	@Override
	public Identity getIdentityByName(final String idIdentity) {
		if (idIdentity.startsWith(idenityPrefix)) {
			return identityDao.getIdentityByName(idIdentity
					.substring(idenityPrefix.length()));
		} else {
			return identityDao.getIdentityByName(idIdentity);
		}
	}

	@Override
	public Identity getById(final String id) {
		return identityDao.getIdentityByName(idenityPrefix + id);
	}

}