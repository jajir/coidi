package com.coroptis.coidi.op.view.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

import com.coroptis.coidi.op.view.pages.Index;
import com.coroptis.coidi.op.view.utils.UserSession;

@Import(stylesheet = "context:css/layout.css")
public class Layout {

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
