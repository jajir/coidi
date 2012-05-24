package com.coroptis.coidi.op.view.pages;

import org.apache.tapestry5.annotations.SessionState;

import com.coroptis.coidi.op.view.entities.User;
import com.coroptis.coidi.op.view.utils.AccessOnlyForSigned;
import com.coroptis.coidi.op.view.utils.UserSession;

/**
 * Main public profile page.
 * 
 * @author jan
 * 
 */
@AccessOnlyForSigned
public class UserProfile { // NO_UCD

	@SessionState
	private UserSession userSession;

	public User getUser() {
		return userSession.getUser();
	}

}
