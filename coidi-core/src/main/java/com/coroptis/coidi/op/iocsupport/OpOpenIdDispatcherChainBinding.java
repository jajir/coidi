package com.coroptis.coidi.op.iocsupport;

import com.coroptis.coidi.op.services.AssociationService;
import com.coroptis.coidi.op.services.OpConfigurationService;
import com.coroptis.coidi.op.services.OpenIdDispatcher;
import com.coroptis.coidi.op.services.impl.AssociationServiceImpl;

public abstract class OpOpenIdDispatcherChainBinding extends OpCoreBinding {

	private final static String OPEN_ID_DISPATCHER_20 = "openIdDispatcher20";

	private final static String OPEN_ID_DISPATCHER_11 = "openIdDispatcher11";
	
	public OpOpenIdDispatcherChainBinding(OpConfigurationService conf) {
		super(conf);
	}

	public OpenIdDispatcher getOpenIdDispatcher20(){
		OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_20);
		if (out == null) {
			out = new AssociationServiceImpl(getBaseAssociationDao(), getCryptoService(), getAssociationTool(),
					getConvertorService());
			put(OPEN_ID_DISPATCHER_20, out);
		}
		return out;
	}

	public OpenIdDispatcher getOpenIdDispatcher11(){
		OpenIdDispatcher out = get(OPEN_ID_DISPATCHER_11);
		if (out == null) {
			out = new AssociationServiceImpl(getBaseAssociationDao(), getCryptoService(), getAssociationTool(),
					getConvertorService());
			put(OPEN_ID_DISPATCHER_11, out);
		}
		return out;		
	}
	
}
