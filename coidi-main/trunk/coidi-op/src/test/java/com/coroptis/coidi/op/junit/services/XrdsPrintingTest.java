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
package com.coroptis.coidi.op.junit.services;

import junit.framework.TestCase;

import com.coroptis.coidi.OpenIdNs;
import com.coroptis.coidi.op.util.Xrd;
import com.coroptis.coidi.op.util.XrdService;
import com.coroptis.coidi.op.util.Xrds;

public class XrdsPrintingTest extends TestCase {

    public void testPrint() throws Exception {
	Xrds xrds = new Xrds();
	xrds.getXrds().add(new Xrd());
	xrds.getXrds().get(0).setVersion("2.0");

	XrdService service = new XrdService();
	service.getTypes().add(OpenIdNs.TYPE_OPENID_2_0);
	service.getTypes().add(OpenIdNs.TYPE_SREG_1_1);
	service.setPriority(4);
	service.setUri("http://coidi.com/openid");
	service.setOpenidDelegate("http://coidi.com/identity/qwe");
	xrds.getXrds().get(0).getServices().add(service);

	service = new XrdService();
	service.getTypes().add(OpenIdNs.TYPE_OPENID_2_0);
	service.getTypes().add(OpenIdNs.TYPE_SREG_1_1);
	service.setUri("http://coidi.com/openid");

	xrds.getXrds().get(0).getServices().add(service);
	System.out.println(xrds.print(new StringBuffer(), ""));
    }

}
