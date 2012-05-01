package com.coroptis.coidi.op.view.junit;

import junit.framework.TestCase;

import com.coroptis.coidi.op.view.pages.Normalizer;

public class AssocTest extends TestCase {

	public void testGetAssociaon() throws Exception {

		String id = "http://jirout.myopenid.com/";

		Normalizer normalizer = new Normalizer();

		id = normalizer.normalize(id, false);

		System.out.println(id);

	}

}
