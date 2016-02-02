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
package com.coroptis.coidi.junit.core.message;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.coroptis.coidi.core.message.AssociationRequest;

/**
 * Tests for {@link AssociationRequest}.
 * 
 * @author jirout
 * 
 */
public class AssociationRequestTest extends TestCase {

    public void testMode() throws Exception {
	AssociationRequest request = new AssociationRequest();

	assertEquals("associate", request.getMode());
	assertTrue(request.getMessage().contains("openid.mode"));
	assertTrue(request.getMap().containsKey("openid.mode"));
    }

    public void testGetDH_MODULUS_prevent_NPE() throws Exception {
	AssociationRequest request = new AssociationRequest();

	assertNull(request.getDhModulo());
    }

    public void testGetDH_GENERATOR_prevent_NPE() throws Exception {
	AssociationRequest request = new AssociationRequest();

	assertNull(request.getDhGen());
    }
    
    public void testSessionType_preventNullValues() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put("openid.session_type", null);
	AssociationRequest request = new AssociationRequest(params);
	
	assertNull(request.getSessionType());
    }
    
    public void testSessionType_preventInvalidValues() throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	params.put("openid.session_type", "blee");
	AssociationRequest request = new AssociationRequest(params);
	
	assertNull(request.getSessionType());
    }

}
