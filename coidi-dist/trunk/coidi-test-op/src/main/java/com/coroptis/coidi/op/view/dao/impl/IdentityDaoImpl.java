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
package com.coroptis.coidi.op.view.dao.impl;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.view.dao.IdentityDao;
import com.coroptis.coidi.op.view.entities.IdentityImpl;

public class IdentityDaoImpl implements IdentityDao {

    @Inject
    private Session session;

    @Override
    public Integer getCount() {
	return ((Long) session.createCriteria(IdentityImpl.class)
		.setProjection(Projections.rowCount()).uniqueResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Identity> getChunk(Integer startIndex, Integer endIndex) {
	Criteria criteria = session.createCriteria(IdentityImpl.class).addOrder(
		Order.asc("idIdentity"));
	if (startIndex != null) {
	    criteria.setFirstResult(startIndex);
	}
	if (endIndex != null) {
	    if (startIndex == null) {
		criteria.setMaxResults(endIndex);
	    } else {
		criteria.setMaxResults(endIndex - startIndex + 1);
	    }
	}
	return criteria.list();
    }
}
