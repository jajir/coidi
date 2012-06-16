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
}
