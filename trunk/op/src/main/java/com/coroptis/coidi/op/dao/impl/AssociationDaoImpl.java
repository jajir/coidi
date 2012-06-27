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
import org.slf4j.Logger;

import com.coroptis.coidi.op.dao.AssociationDao;
import com.coroptis.coidi.op.entities.AssociationImpl;

public class AssociationDaoImpl implements AssociationDao {

	@Inject
	private Logger logger;

	@Inject
	private Session session;

	@Override
	public void create(AssociationImpl association) {
		logger.debug("creating: " + association);
		session.save(association);
	}

	@Override
	public AssociationImpl getByAssocHandle(String assoc_handle) {
		return (AssociationImpl) session.createCriteria(AssociationImpl.class)
				.add(Restrictions.eq("assocHandle", assoc_handle))
				.uniqueResult();
	}

}
