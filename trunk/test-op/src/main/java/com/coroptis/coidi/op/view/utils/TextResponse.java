package com.coroptis.coidi.op.view.utils;

import java.io.ByteArrayInputStream;

public class TextResponse extends AttachmentStreamResponse {

	public TextResponse(String text) {
		super(new ByteArrayInputStream(text.getBytes()));
	}
}
