package com.coroptis.coidi.op.view.pages;

import javax.servlet.http.HttpServletResponse;

import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Response;

public class Error404 {

    @Inject
    private Response response;

    @SetupRender
    void setupRender() {
	response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

}
