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
package com.coroptis.coidi.core.message;

import java.util.Map;

public class ErrorResponse extends AbstractOpenIdResponse {

	public final static String ERROR = "error";

	public final static String CONTACT = "contact";

	public final static String REFERENCE = "reference";

	public ErrorResponse(final Boolean isUrl) {
		super();
		setUrl(isUrl);
		setNameSpace(OPENID_NS_20);
	}

	public ErrorResponse(final Boolean isUrl, final String error) {
		super();
		setUrl(isUrl);
		setError(error);
		setNameSpace(OPENID_NS_20);
	}

	public ErrorResponse(final Boolean isUrl, final String error,
			final String contact) {
		super();
		setUrl(isUrl);
		setNameSpace(OPENID_NS_20);
		setError(error);
		setContact(contact);
	}

	public ErrorResponse(final Map<String, String> map, final Boolean isUrl) {
		super(map);
		setUrl(isUrl);
		setNameSpace(OPENID_NS_20);
	}

	public String getError() {
		return get(ERROR);
	}

	public void setError(String error) {
		put(ERROR, error);
	}

	public String getContact() {
		return get(CONTACT);
	}

	public void setContact(String contact) {
		put(CONTACT, contact);
	}

	public String getReference() {
		return get(REFERENCE);
	}

	public void setReference(String refrence) {
		put(REFERENCE, refrence);
	}

}
