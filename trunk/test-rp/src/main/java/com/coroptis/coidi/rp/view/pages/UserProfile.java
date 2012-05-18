package com.coroptis.coidi.rp.view.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import com.coroptis.coidi.rp.view.util.AccessOnlyForSigned;
import com.coroptis.coidi.rp.view.util.UserSession;

@AccessOnlyForSigned
public class UserProfile {

	@SessionState
	@Property
	UserSession userSession;

}
