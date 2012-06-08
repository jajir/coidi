package com.coroptis.coidi.op.dao;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.coroptis.coidi.op.entities.User;

public interface UserDao {

	User login(String name, String password);

	@CommitAfter
	User register(String name, String password, String identityId);

	User getUserByName(String userName);

}
