package com.coroptis.coidi.op.view.pages;

import org.apache.log4j.Logger;
import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * This class helps T5 obtain instance of additional binding steps during T5
 * startup procedure.
 * 
 * @author jan
 * 
 */
public class AdditionalBinderProvider {

	private final static Logger logger = Logger
			.getLogger(AdditionalBinderProvider.class);

	private static AdditionalBinder additionalBinder;

	/**
	 * @return the additionalBinder
	 */
	public static AdditionalBinder getAdditionalBinder() {
		return additionalBinder;
	}

	/**
	 * @param additionalBinder
	 *            the additionalBinder to set
	 */
	public static void setAdditionalBinder(AdditionalBinder additionalBinder) {
		AdditionalBinderProvider.additionalBinder = additionalBinder;
	}

	public static void bind(ServiceBinder binder) {
		if (getAdditionalBinder() == null) {
			logger.debug("There is no defined additional binder");
		} else {
			getAdditionalBinder().bind(binder);
		}
	}
}