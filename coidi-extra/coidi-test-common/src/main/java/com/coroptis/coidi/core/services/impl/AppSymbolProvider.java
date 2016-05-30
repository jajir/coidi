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
package com.coroptis.coidi.core.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.coroptis.coidi.core.services.ConfService;

/**
 * Create {@link SymbolProvider} that allows to use constants loaded from
 * configuration. Usage should be
 * <code>@Symbol("misc.numberOfSetsOnSetsMainPage")</code>. Could be used before
 * class property or before constructor parameter.
 * 
 * @author jan
 * 
 */
public class AppSymbolProvider implements SymbolProvider {

	@Inject
	private ConfService configurationService;

	@Override
	public String valueForSymbol(String symbolName) {
		return configurationService.getProperty(symbolName);
	}
}
