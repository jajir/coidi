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

    @Override
    public String getContentType() {
	return contentType;
    }

    @Override
    public InputStream getStream() throws IOException {
	return is;
    }

    @Override
    public void prepareResponse(Response arg0) {
	arg0.setHeader("Expires", "0");
	arg0.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	arg0.setHeader("Pragma", "public");
    }

}
