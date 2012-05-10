package com.coroptis.coidi.op.view.pages;

import org.apache.tapestry5.annotations.SessionState;

import com.coroptis.coidi.op.view.entities.User;
import com.coroptis.coidi.op.view.utils.SignedUserRequired;
import com.coroptis.coidi.op.view.utils.UserSession;

/**
 * Main public profile page.
 * 
 * @author jan
 * 
 */
@SignedUserRequired
public class Profile {

	@SessionState
	UserSession userSession;

	public User getUser() {
		return userSession.getUser();
	}

}
