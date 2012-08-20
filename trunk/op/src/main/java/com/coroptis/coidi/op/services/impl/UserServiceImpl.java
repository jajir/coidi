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

import com.coroptis.coidi.op.dao.UserDao;
import com.coroptis.coidi.op.entities.User;
import com.coroptis.coidi.op.services.UserService;

public class UserServiceImpl implements UserService {

	@Inject
	private UserDao userDao;

	@Override
	public User login(final String name, final String password) {
		return userDao.login(name, password);
	}

	@Override
	public User register(final String name, final String password,
			final String identityId) {
		return userDao.register(name, password, identityId);
	}

	@Override
	public User getUserByName(final String userName) {
		return userDao.getUserByName(userName);
	}

	@Override
	public User getById(Integer idUser) {
		return userDao.getById(idUser);
	}
}
