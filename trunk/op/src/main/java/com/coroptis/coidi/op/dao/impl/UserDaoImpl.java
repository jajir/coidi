package com.coroptis.coidi.op.dao.impl;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.coroptis.coidi.op.dao.UserDao;
import com.coroptis.coidi.op.entities.Identity;
import com.coroptis.coidi.op.entities.User;

public class UserDaoImpl implements UserDao {

	@Inject
	private Session session;

	@Override
	public User login(final String name, final String password) {
		return (User) session
				.createCriteria(User.class)
				.add(Restrictions.and(Restrictions.eq("name", name),
						Restrictions.eq("password", password))).uniqueResult();
	}

	@Override
	@CommitAfter
	public User register(final String name, final String password,
			final String identityId) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		Identity identity = new Identity();
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
