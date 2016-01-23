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
package com.coroptis.coidi.op.util;

import java.util.ArrayList;
import java.util.List;

import com.coroptis.coidi.CoidiException;

public class PrintableXrdService implements Printable {

    private Integer priority;

    private final List<String> types;

    private String Uri;

    private String openidDelegate;

    private String localID;

    public PrintableXrdService() {
	types = new ArrayList<String>();
    }

    @Override
    public StringBuffer print(final StringBuffer buffer, final String currentIntend) {
	buffer.append(currentIntend);
	buffer.append("<Service");
	if (priority != null) {
	    buffer.append(" priority=\"");
	    buffer.append(priority);
	    buffer.append("\"");
	}
	buffer.append(" xmlns=\"xri://$xrd*($v*2.0)\">\n");
	for (String type : types) {
	    buffer.append(currentIntend);
	    buffer.append(LINE_INTEND);
	    buffer.append("<Type>");
	    buffer.append(type);
	    buffer.append("</Type>\n");
	}
	if (Uri == null) {
	    throw new CoidiException("URI is mandatory.");
	} else {
	    buffer.append(currentIntend);
	    buffer.append(LINE_INTEND);
	    buffer.append("<URI>");
	    buffer.append(getUri());
	    buffer.append("</URI>\n");
	}
	if (openidDelegate != null) {
	    buffer.append(currentIntend);
	    buffer.append(LINE_INTEND);
	    buffer.append("<openid:Delegate>");
	    buffer.append(openidDelegate);
	    buffer.append("</openid:Delegate>\n");
	}
	if (localID != null) {
	    buffer.append(currentIntend);
	    buffer.append(LINE_INTEND);
	    buffer.append("<LocalID>");
	    buffer.append(localID);
	    buffer.append("</LocalID>\n");
	}
	buffer.append(currentIntend);
	buffer.append("</Service>\n");
	return buffer;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
	return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(Integer priority) {
	this.priority = priority;
    }

    /**
     * @return the uri
     */
    public String getUri() {
	return Uri;
    }

    /**
     * @param uri
     *            the uri to set
     */
    public void setUri(String uri) {
	Uri = uri;
    }

    /**
     * @return the openidDelegate
     */
    public String getOpenidDelegate() {
	return openidDelegate;
    }

    /**
     * @param openidDelegate
     *            the openidDelegate to set
     */
    public void setOpenidDelegate(String openidDelegate) {
	this.openidDelegate = openidDelegate;
    }

    /**
     * @return the types
     */
    public List<String> getTypes() {
	return types;
    }

    /**
     * @return the localID
     */
    public String getLocalID() {
	return localID;
    }

    /**
     * @param localID
     *            the localID to set
     */
    public void setLocalID(String localID) {
	this.localID = localID;
    }

}
