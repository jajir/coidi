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
package com.coroptis.coidi.op.services;

import com.coroptis.coidi.CoidiException;

/**
 * In OpenID specification there are distinguished OP-Local identifier and OP
 * Identifier. For example:
 * <ul>
 * <li>'karel' is OP-Local identifier</li>
 * <li>http://karel.server.com is OP identifier</li>
 * </ul>
 * This service allows to translate between these two forms.
 * <p>
 * For converting is used configuration property 'op.idenity.pattern'. This
 * property should contain OP identifier with identity place holder e.g.
 * 'http://localhost:8080/user/${identity}'.
 * </p>
 * 
 * @author jirout
 * 
 */
public interface IdentityNamesConvertor {

	/**
	 * Convert from identifier to user's OP local identifier.
	 * 
	 * @param opLocalIdentifier
	 *            required identifier
	 * @return OP local identifier
	 * @throws CoidiException
	 *             when it's not possible to parse opIdentifier
	 */
	String convertToOpLocalIdentifier(String identifier);

	/**
	 * Convert OP local identifier identifier.
	 * 
	 * @param identityId
	 *            required OP local identifier
	 * @return identifier
	 */
	String convertToIdentifier(String identityId);

	/**
	 * This inform if given string is in form of local identifier.
	 * 
	 * 
	 * @param someIdentifier
	 *            required some OP identifier
	 * @return true when it is OP local identifier, false when it's OP
	 *         identifier
	 * @throws CoidiException
	 *             when it's not possible to parse opIdentifier
	 */
	Boolean isOpLocalIdentifier(String someIdentifier);

}
