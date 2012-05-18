package com.coroptis.coidi.rp.view.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import com.coroptis.coidi.rp.view.pages.Index;
import com.coroptis.coidi.rp.view.util.UserSession;

@Import(stylesheet = "context:css/layout.css")
public class Layout {

	@SuppressWarnings("unused")
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.MESSAGE)
	private String title;

	@Property
	@SessionState
	private UserSession userSession;

	Object onActionFromLogout() {
		userSession.setSsoIdentity(null);
		return Index.class;
	}

}
