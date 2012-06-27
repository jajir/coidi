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
package com.coroptis.coidi.op.view.pages;

import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.op.services.XrdsService;
import com.coroptis.coidi.op.view.utils.XrdsStreamResponse;

/**
 * Main public profile page.
 * 
 * @author jan
 * 
 */
public class User { // NO_UCD

	@Inject
	private XrdsService xrdsService;

	@Property
	private String userName;

	StreamResponse onActivate(final String userName) {
		this.userName = userName;
		if (userName == null) {
			new CoidiException("user not found");
		}
		return new XrdsStreamResponse(xrdsService.getDocument(userName));
	}

	String onPassivate() {
		return userName;
	}

}
