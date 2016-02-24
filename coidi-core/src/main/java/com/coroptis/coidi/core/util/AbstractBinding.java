package com.coroptis.coidi.core.util;

import com.google.common.base.Preconditions;

/**
 * Abstract binding class. Provide environment for easy to rewrite binding.
 * 
 * @author jiroutj
 *
 */
public abstract class AbstractBinding {

	/**
	 * Allows to define and user lazy initialize singleton service.
	 * 
	 * @author jiroutj
	 *
	 * @param <S>
	 *            lazy loaded singleton service
	 */
	protected class Lazy<S> {

		private S service;

		private final Init<S> init;

		/**
		 * Constructor with {@link Init} instance that allows to create instance
		 * later.
		 * 
		 * @param init
		 *            required service initialization class
		 */
		public Lazy(final Init<S> init) {
			this.init = Preconditions.checkNotNull(init, "initialization class can't be null.");
		}

		/**
		 * Get lazy load service. Service is initialized when is needed first
		 * time.
		 * 
		 * @return service instance
		 */
		public S get() {
			if (service == null) {
				service = Preconditions.checkNotNull(init.create(), "initialization failed, created null object");
			}
			return service;
		}
	}

	/**
	 * Allows to define creating procedure for specific service.
	 * 
	 * @author jiroutj
	 *
	 * @param <S>
	 *            lazy loaded singleton service
	 */
	public static interface Init<S> {
		S create();
	}
}
