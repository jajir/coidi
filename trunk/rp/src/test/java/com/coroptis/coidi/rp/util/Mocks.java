package com.coroptis.coidi.rp.util;

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
