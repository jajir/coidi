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

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.coroptis.coidi.op.view.dao.UserDao;
import com.coroptis.coidi.op.view.entities.IdentityImpl;
import com.coroptis.coidi.op.view.entities.User;

public class UserDaoImpl implements UserDao {

    @Inject
    private Session session;

    @Override
    public User getById(Integer idUser) {
	return (User) session.get(User.class, idUser);
    }

    @Override
    public User login(final String name, final String password) {
	return (User) session
		.createCriteria(User.class)
		.add(Restrictions.and(Restrictions.eq("name", name),
			Restrictions.eq("password", password))).uniqueResult();
    }

    @CommitAfter
    @Override
    public User register(final String name, final String password, final String identityId) {
	User user = new User();
	user.setName(name);
	user.setPassword(password);
	IdentityImpl identity = new IdentityImpl();
	identity.setUser(user);
	identity.setIdIdentity(identityId);
	session.save(user);
	session.save(identity);
	return user;
    }

    @Override
    public User getUserByName(final String userName) {
	return (User) session.createCriteria(User.class)
		.add(Restrictions.eq("name", userName)).uniqueResult();
    }
}
