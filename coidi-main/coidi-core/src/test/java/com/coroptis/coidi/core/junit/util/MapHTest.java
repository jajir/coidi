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
package com.coroptis.coidi.core.junit.util;

import java.util.Map;

import com.coroptis.coidi.core.util.MapH;

import junit.framework.TestCase;

public class MapHTest extends TestCase {

    public void test_make() throws Exception {
	Map<String, String> m1 = MapH.make("sa", "ds");
	assertEquals(1, m1.size());
	assertEquals("ds", m1.get("sa"));
    }

    public void test_make_complex() throws Exception {
	Map<String, String> m1 = MapH.make("sa", "ds", "so", "iu", "rr", "hh");
	assertEquals(3, m1.size());
	assertEquals("ds", m1.get("sa"));
	assertEquals("iu", m1.get("so"));
	assertEquals("hh", m1.get("rr"));
    }

    public void test_make_invalidParams() throws Exception {
	try {
	    MapH.make("sa", "ds", "sd");
	    fail();
	} catch (IndexOutOfBoundsException e) {
	    assertTrue(true);
	}
    }

}
