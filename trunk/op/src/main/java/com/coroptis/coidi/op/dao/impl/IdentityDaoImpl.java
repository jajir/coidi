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
package com.coroptis.coidi.op.dao.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.coroptis.coidi.op.dao.IdentityDao;
import com.coroptis.coidi.op.entities.Identity;

public class IdentityDaoImpl implements IdentityDao {

	@Inject
	private Session session;

	@Override
	public Identity getIdentityByName(final String idIdentity) {
		return (Identity) session.createCriteria(Identity.class)
				.add(Restrictions.eq("idIdentity", idIdentity)).uniqueResult();
	}

}
