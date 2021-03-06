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

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.coroptis.coidi.op.dao.BaseNonceDao;
import com.coroptis.coidi.op.entities.Nonce;
import com.coroptis.coidi.op.view.entities.NonceImpl;

public class BaseNonceDaoImpl implements BaseNonceDao {

	@Inject
	private Session session;

	@Override
	public void save(Nonce nonce) {
		session.save(nonce);
	}

	@Override
	public NonceImpl getByNonce(String noce) {
		return (NonceImpl) session.createCriteria(NonceImpl.class).add(Restrictions.eq("nonce", noce)).uniqueResult();
	}

	@Override
	public Nonce createNewInstance() {
		return new NonceImpl();
	}

	@Override
	public void delete(Nonce nonce) {
		session.delete(nonce);
	}

}
