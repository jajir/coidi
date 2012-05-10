package com.coroptis.coidi.conf.util;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;

import com.coroptis.coidi.conf.services.TestService;

public class TestServiceImpl implements TestService {

	@Inject
	@Symbol("test.name")
	private String name;


	@Inject
	@Symbol("test.favorite.color")
	private String favoriteColor;
	
	private final String surname;

	public TestServiceImpl(final @Inject @Symbol("test.surname") String surname) {
		this.surname = surname;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSurname() {
		return surname;
	}
	@Override
	public String getFavoriteColor() {
		return favoriteColor;
	}
}
