package com.coroptis.coidi.op.view.pages;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.view.services.AssociationService;
import com.coroptis.coidi.op.view.services.NonceService;
import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.utils.AccessOnlyForUnsigned;
import com.coroptis.coidi.op.view.utils.UserSession;

@AccessOnlyForUnsigned
public class Login {

	@Inject
	private Logger logger;

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
	private NonceService nonceService;

	@Inject
	private SigningService signingService;

	@Inject
	private AssociationService associationService;

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
			// authentication request have to be finished
			Association association = associationService
					.getByAssocHandle(authenticationRequest.getAssocHandle());
			AuthenticationResponse response = new AuthenticationResponse();
			response.setAssocHandle(authenticationRequest.getAssocHandle());
			response.setIdentity(authenticationRequest.getIdentity());
			response.setNonce(nonceService.createNonce());
			response.setReturnTo(authenticationRequest.getReturnTo());
			response.setSigned("assoc_handle,identity,nonce,return_to");
			response.setSignature(signingService.sign(response, association));
			response.put("go_to", authenticationRequest.getReturnTo());
			logger.debug("redirect to: " + response.getMessage());
			return new URL(response.getMessage());
		}
		return UserProfile.class;
	}

}
