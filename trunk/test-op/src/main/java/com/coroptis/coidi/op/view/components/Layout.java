package com.coroptis.coidi.op.view.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import com.coroptis.coidi.op.util.UserSession;
import com.coroptis.coidi.op.view.pages.Index;

@Import(stylesheet = "context:css/layout.css")
public class Layout { // NO_UCD

	@SuppressWarnings("unused")
	@Property
	@Parameter(required = true, defaultPrefix = BindingConstants.MESSAGE)
	private String title;

	@SuppressWarnings("unused")
	@Property
	@SessionState
	private UserSession userSession;

	Object onActionFromLogout() {
		userSession = null;
		return Index.class;
	}

}
