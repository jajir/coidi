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

public class Xrds implements Printable {

    private final List<Xrd> xrds;

    @Override
    public StringBuffer print(final StringBuffer buffer, final String currentIntend) {
	buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	buffer.append("<xrds:XRDS xmlns:xrds=\"xri://$xrds\" xmlns=\"xri://$xrd*($v*2.0)\" xmlns:openid=\"http://openid.net/xmlns/1.0\">\n");
	for (Xrd xrd : xrds) {
	    xrd.print(buffer, LINE_INTEND);
	}
	buffer.append("</xrds:XRDS>");
	return buffer;
    }

    public Xrds() {
	xrds = new ArrayList<Xrd>();
    }

    /**
     * @return the xrds
     */
    public List<Xrd> getXrds() {
	return xrds;
    }
}
