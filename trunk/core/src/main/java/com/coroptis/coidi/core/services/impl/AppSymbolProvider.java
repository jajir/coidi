package com.coroptis.coidi.core.services.impl;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.SymbolProvider;

import com.coroptis.coidi.core.services.ConfigurationService;

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
	private ConfigurationService configurationService;

	@Override
	public String valueForSymbol(String symbolName) {
		return configurationService.getProperty(symbolName);
	}
}
