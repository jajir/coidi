package com.coroptis.coidi.rp.view.services.impl;

import com.coroptis.coidi.rp.view.services.ConvertorService;

public class ConvertorServiceImpl implements ConvertorService {

	@Override
	public Integer getInt(String val) {
		try {
			return Integer.valueOf(val);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
}
