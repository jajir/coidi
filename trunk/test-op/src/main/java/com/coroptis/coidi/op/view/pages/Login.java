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
import com.coroptis.coidi.core.services.NonceService;
import com.coroptis.coidi.core.services.SigningService;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.view.entities.StatelessModeNonce;
import com.coroptis.coidi.op.view.services.AssociationService;
import com.coroptis.coidi.op.view.services.StatelessModeNonceService;
import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.utils.AccessOnlyForUnsigned;
import com.coroptis.coidi.op.view.utils.UserSession;

@AccessOnlyForUnsigned
public class Login { // NO_UCD

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

	@Inject
	private StatelessModeNonceService statelessModeNonceService;

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

			AuthenticationResponse response = new AuthenticationResponse();
			response.setIdentity(authenticationRequest.getIdentity());
			response.setNonce(nonceService.createNonce());
			response.setReturnTo(authenticationRequest.getReturnTo());
			response.put("go_to", authenticationRequest.getReturnTo());
			//TODO correct constant
			response.setOpEndpoint("http://localhost:8080/openid");
			if (authenticationRequest.getAssocHandle() == null) {
				// state less mode
				StatelessModeNonce statelessModeNonce = statelessModeNonceService
						.createStatelessModeNonce(response.getNonce());
				response.setSigned("identity,nonce,return_to");
				response.setSignature(signingService.sign(response,
						statelessModeNonce.getMacKey()));
				response.setSigned("identity,nonce,return_to");
				response.setSignature(signingService.sign(response,
						statelessModeNonce.getMacKey()));
			} else {
				Association association = associationService
						.getByAssocHandle(authenticationRequest
								.getAssocHandle());
				response.setAssocHandle(authenticationRequest.getAssocHandle());
				response.setSigned("assoc_handle,identity,nonce,return_to");
				response.setSignature(signingService
						.sign(response, association));
			}
			logger.debug("redirect to: " + response.getMessage());
			return new URL(response.getMessage());
		}
		return UserProfile.class;
	}

}
