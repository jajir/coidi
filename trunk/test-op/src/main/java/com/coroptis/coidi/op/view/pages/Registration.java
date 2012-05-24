package com.coroptis.coidi.op.view.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.op.view.services.IdentityService;
import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.utils.AccessOnlyForUnsigned;
import com.coroptis.coidi.op.view.utils.UserSession;

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
	private UserSession userSession;

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
			registrationForm.recordError(userNameField,
					"user name is already registered.");
		}
		if (identityService.getIdentityByName(identityId) != null) {
			registrationForm.recordError(identityIdField,
					"identity id is already registered.");
		}
		if (password != null && !password.equals(password2)) {
			registrationForm.recordError(passwordField,
					"Password have to be same.");
		}
	}

	Object onSuccess() {
		userSession.setUser(userService
				.register(userName, password, identityId));
		return UserProfile.class;
	}
}
