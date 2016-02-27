package com.coroptis.coidi.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract binding class. Provide environment for easy to rewrite binding.
 * 
 * @author jiroutj
 *
 */
public abstract class AbstractBinding {

	private final Map<String, Object> instances = new HashMap<String, Object>();

	protected final <T> void put(String identitfier, T instance) {
		instances.put(identitfier, instance);
	}

	protected final <T> void put(Class<T> clazz, T instance) {
		put(clazz.getName(), instance);
	}

	@SuppressWarnings("unchecked")
	protected final <T> T get(String identifier) {
		return (T) instances.get(identifier);
	}

	protected final <T> T get(Class<T> clazz) {
		return get(clazz.getName());
	}
}
