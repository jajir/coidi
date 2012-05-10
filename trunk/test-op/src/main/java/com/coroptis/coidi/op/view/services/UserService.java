package com.coroptis.coidi.op.view.services;

import com.coroptis.coidi.op.view.entities.User;

public interface UserService {

	User login(String name, String password);

	User register(String name, String password, String identityId);

	User getUserByName(String userName);

}
