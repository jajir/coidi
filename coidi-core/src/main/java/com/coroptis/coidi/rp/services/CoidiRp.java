package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.core.services.MessageService;

/**
 * Main class that will user end user.
 * @author jiroutj
 *
 */
public interface CoidiRp {
	
	//TODO move here facade methods
	AssociationFactory getAssociationFactory();
	
	//TODO move here facade methods
	DiscoveryProcessor getDiscoveryProcessor();
	
	//TODO move here facade methods
	AuthenticationVerificationService getAuthenticationVerificationService();
	
	//TODO move here facade methods
	MessageService getMessageService();
	
	//TODO move here facade methods
	RpService getRpService();
	
}
