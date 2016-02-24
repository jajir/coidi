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
package com.coroptis.coidi.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Helps with life with maps.
 * 
 * @author jirout
 * 
 */
public class MapH {

    /**
     * Create map where keys and values are same type.
     * 
     * @param <E>
     *            returning key value type
     * @param values
     *            required parameter
     * @return new hash map
     */
    public static <E> Map<E, E> make(final E... values) {
	final HashMap<E, E> out = new HashMap<E, E>();
	for (int i = 0; i < values.length; i += 2) {
	    out.put(values[i], values[i + 1]);
	}
	return out;
    }

}
