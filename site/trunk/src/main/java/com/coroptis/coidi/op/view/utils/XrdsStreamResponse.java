/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
