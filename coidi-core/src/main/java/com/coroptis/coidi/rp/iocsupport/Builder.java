package com.coroptis.coidi.rp.iocsupport;

import com.coroptis.coidi.rp.services.CoidiRp;

public class Builder {

	private RpBinding classProvider;

	public static Builder getBuilder() {
		return new Builder();
	}

	public Builder setInitializer(final RpBinding rpBingings) {
		this.classProvider = rpBingings;
		return this;
	}

	public CoidiRp build() {
		return classProvider.getCoidiRp();
	}

}
