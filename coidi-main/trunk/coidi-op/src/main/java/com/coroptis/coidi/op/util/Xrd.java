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

public class Xrd implements Printable {

    private String version;

    private final List<XrdService> services;

    public Xrd() {
	services = new ArrayList<XrdService>();
    }

    @Override
    public StringBuffer print(final StringBuffer buffer, final String currentIntend) {
	buffer.append(currentIntend);
	buffer.append("<XRD");
	if (version != null) {
	    buffer.append(" version=\"");
	    buffer.append(version);
	    buffer.append("\"");
	}
	buffer.append(">\n");

	for (XrdService service : services) {
	    service.print(buffer, currentIntend + LINE_INTEND);
	}
	buffer.append(currentIntend);
	buffer.append("</XRD>\n");
	return buffer;
    }

    /**
     * @return the version
     */
    public String getVersion() {
	return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
	this.version = version;
    }

    /**
     * @return the services
     */
    public List<XrdService> getServices() {
	return services;
    }

}
