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
package com.coroptis.coidi.op.view.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.op.services.IdentityService;
import com.coroptis.coidi.op.view.entities.User;
import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.utils.AccessOnlyForUnsigned;

/**
 * User registration page.
 * 
 * @author jan
 * 
 */
@AccessOnlyForUnsigned
public class Registration { // NO_UCD

	@Inject
	private UserService userService;

	@Inject
	private IdentityService identityService;

	@SessionState
	private User userSession;

	@Component
	private Form registrationForm;

	@Component(id = "password2")
	private PasswordField passwordField;

	@Component(id = "identityId")
	private PasswordField identityIdField;

	@Component(id = "userName")
	private TextField userNameField;

	@Property
	private String userName;

	@Property
	private String password;

	@Property
	private String password2;

	@Property
	private String identityId;

	void onValidateFromRegistrationForm() {
		if (userService.getUserByName(userName) != null) {
			registrationForm.recordError(userNameField, "user name is already registered.");
		}
		if (identityService.getByOpLocalIdentifier(identityId) != null) {
			registrationForm.recordError(identityIdField, "identity id is already registered.");
		}
		if (password != null && !password.equals(password2)) {
			registrationForm.recordError(passwordField, "Password have to be same.");
		}
	}

	Object onSuccess() {
		userSession = userService.register(userName, password, identityId);
		return UserProfile.class;
	}
}
