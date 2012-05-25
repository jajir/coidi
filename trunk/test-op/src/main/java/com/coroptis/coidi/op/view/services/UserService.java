package com.coroptis.coidi.op.view.services;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.coroptis.coidi.op.view.entities.User;

public interface UserService {

	User login(String name, String password);

	@CommitAfter
	User register(String name, String password, String identityId);

	User getUserByName(String userName);

}
