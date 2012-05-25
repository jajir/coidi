package com.coroptis.coidi.op.view.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.core.message.AbstractOpenIdResponse;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.view.services.AuthenticationService;
import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.utils.AccessOnlyForUnsigned;
import com.coroptis.coidi.op.view.utils.UserSession;

@AccessOnlyForUnsigned
public class Login { // NO_UCD

	@Inject
	private UserService userService;

	@SessionState
	private UserSession userSession;

	@Component
	private Form loginForm;

	@Property
	private String userName;

	@Property
	private String password;

	@Component(id = "password")
	private PasswordField passwordField;

	@Inject
	private AuthenticationService authenticationService;

	void onValidateFromLoginForm() {
		if (userService.login(userName, password) == null) {
			loginForm.recordError(passwordField,
					"Invalid user name or password.");
		}
	}

	Object onSuccess() throws MalformedURLException {
		userSession.setUser(userService.login(userName, password));
		if (userSession.getAuthenticationRequest() != null) {
			AuthenticationRequest authenticationRequest = userSession
					.getAuthenticationRequest();
			AbstractOpenIdResponse response = authenticationService
					.process(authenticationRequest);
			return new URL(response.getMessage());
		}
		return UserProfile.class;
	}

}
