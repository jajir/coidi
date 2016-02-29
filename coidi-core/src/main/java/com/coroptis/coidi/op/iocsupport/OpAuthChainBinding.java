package com.coroptis.coidi.op.iocsupport;

import com.coroptis.coidi.op.services.OpConfigurationService;

public abstract class OpAuthChainBinding extends OpOpenIdDispatcherChainBinding {

	public OpAuthChainBinding(OpConfigurationService conf) {
		super(conf);
	}

}
