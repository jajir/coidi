package com.coroptis.coidi.op.view.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

class AttachmentStreamResponse implements StreamResponse {
	private InputStream is = null;

	/**
	 * This can be changed to something obscure, so that it is more likely to
	 * trigger a "Save As" dialog, although there is no guarantee.
	 * 
	 * ex: application/x-download
	 * 
	 * See http://www.onjava.com/pub/a/onjava/excerpt/jebp_3/index3.html
	 */
	private String contentType = "text/plain";

	public AttachmentStreamResponse(InputStream is) {
		this.is = is;
	}

	public String getContentType() {
		return contentType;
	}

	public InputStream getStream() throws IOException {
		return is;
	}

	public void prepareResponse(Response arg0) {
		arg0.setHeader("Expires", "0");
		arg0.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		arg0.setHeader("Pragma", "public");
	}

}
