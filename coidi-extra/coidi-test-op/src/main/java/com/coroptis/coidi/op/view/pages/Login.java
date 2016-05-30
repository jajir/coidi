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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.RequestGlobals;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AuthenticationRequest;
import com.coroptis.coidi.op.iocsupport.OpBinding;
import com.coroptis.coidi.op.view.entities.User;
import com.coroptis.coidi.op.view.services.UserService;
import com.coroptis.coidi.op.view.utils.AccessOnlyForUnsigned;

@AccessOnlyForUnsigned
public class Login { // NO_UCD

    @Inject
    private UserService userService;

    @SessionState
    private User userSession;

    @SessionState(create = false)
    private AuthenticationRequest authenticationRequest;

    @Component
    private Form loginForm;

    @Property
    private String userName;

    @Property
    private String password;

    @Component(id = "password")
    private PasswordField passwordField;

    @Inject
    private OpBinding opBinding;

    @Inject
    private RequestGlobals request;

    void onValidateFromLoginForm() {
	if (userService.login(userName, password) == null) {
	    loginForm.recordError(passwordField, "Invalid user name or password.");
	}
    }

    Object onSuccess() throws MalformedURLException {
	userSession = userService.login(userName, password);
	if (authenticationRequest == null) {
	    return UserProfile.class;
	} else {
	    AbstractMessage response = opBinding.getOpenIdRequestProcessor().process(
		    authenticationRequest.getMap(), request.getHTTPServletRequest().getSession());
	    return new URL(response.getMessage());
	}
    }

}
