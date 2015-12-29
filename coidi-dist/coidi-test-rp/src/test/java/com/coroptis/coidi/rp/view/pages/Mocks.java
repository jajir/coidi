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
package com.coroptis.coidi.rp.view.pages;

import org.easymock.EasyMock;

/**
 * This class provide all service mocks.
 * 
 * @author jan
 * 
 */
public class Mocks {

    private static Mocks mocks;

    private final Object[] objects = new Object[] {};

    public static Mocks getServices() {
	if (mocks == null) {
	    mocks = new Mocks();
	}
	return mocks;
    }

    private Mocks() {
    }

    public void replay() {
	EasyMock.replay(objects);
    }

    public void verify() {
	EasyMock.verify(objects);
    }

    public void reset() {
	EasyMock.reset(objects);
    }
}
