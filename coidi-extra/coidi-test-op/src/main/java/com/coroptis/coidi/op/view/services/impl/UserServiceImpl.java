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
package com.coroptis.coidi.op.view.services.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.services.ConvertorService;
import com.coroptis.coidi.op.view.dao.UserDao;
import com.coroptis.coidi.op.view.entities.User;
import com.coroptis.coidi.op.view.services.UserService;

public class UserServiceImpl implements UserService {

	@Inject
	private Logger logger;

	@Inject
	private UserDao userDao;

	@Inject
	private ConvertorService convertorService;

	public final static String SALT = "23t4bcdjs@()&d431f#";

	@Override
	public User login(final String name, final String password) {
		return userDao.login(name, md5(password));
	}

	@Override
	public User register(final String name, final String password, final String identityId) {
		return userDao.register(name, md5(password), identityId);
	}

	@Override
	public User getUserByName(final String userName) {
		return userDao.getUserByName(userName);
	}

	@Override
	public User getById(final Integer idUser) {
		return userDao.getById(idUser);
	}

	/**
	 * Return Base64 encoded MD5 from input password.
	 * 
	 * @param plainPassword
	 * @return
	 */
	private String md5(String plainPassword) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainPassword.getBytes("UTF-8"));
			md.update(SALT.getBytes());
			return convertorService.convertToString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new CoidiException(e.getMessage(), e);
		}
	}
}
