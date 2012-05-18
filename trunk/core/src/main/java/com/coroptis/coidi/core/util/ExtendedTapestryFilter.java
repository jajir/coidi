package com.coroptis.coidi.core.util;

import javax.servlet.ServletContext;

import org.apache.tapestry5.TapestryFilter;
import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.def.ModuleDef;
import org.apache.tapestry5.ioc.internal.DefaultModuleDefImpl;
import org.apache.tapestry5.ioc.internal.services.PlasticProxyFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.coidi.core.services.ConfModule;

/**
 * This filter allows us to jump into startup process and put in hand
 * {@link Registry}.
 * 
 * @author jan
 * 
 */
public class ExtendedTapestryFilter extends TapestryFilter {

	private Logger logger = LoggerFactory
			.getLogger(ExtendedTapestryFilter.class);

	/**
	 * Overridden in subclasses to provide additional module definitions beyond
	 * those normally located. This implementation returns an empty array.
	 */
	protected ModuleDef[] provideExtraModuleDefs(ServletContext context) {
		logger.debug("modules force loading");
		return new ModuleDef[] { new DefaultModuleDefImpl(ConfModule.class,
				logger, new PlasticProxyFactoryImpl(
						ExtendedTapestryFilter.class.getClassLoader(), logger)) };
	}

}
