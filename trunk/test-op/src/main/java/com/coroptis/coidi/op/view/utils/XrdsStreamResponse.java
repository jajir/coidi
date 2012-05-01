package com.coroptis.coidi.op.view.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

import com.google.common.base.Preconditions;

public class XrdsStreamResponse implements StreamResponse {

	private final String xml;

	public XrdsStreamResponse(final String xml) {
		this.xml = Preconditions.checkNotNull(xml, "xrds xml");
	}

	@Override
	public void prepareResponse(Response response) {
	}

	@Override
	public InputStream getStream() throws IOException {
		return new ByteArrayInputStream(xml.getBytes());
	}

	@Override
	public String getContentType() {
		return "application/xrds+xml";
	}
}
