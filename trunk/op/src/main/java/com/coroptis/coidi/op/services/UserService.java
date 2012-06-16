package com.coroptis.coidi.op.services;

import com.coroptis.coidi.op.entities.User;

public interface UserService {

	User login(String name, String password);

	User register(String name, String password, String identityId);

	User getUserByName(String userName);

}
