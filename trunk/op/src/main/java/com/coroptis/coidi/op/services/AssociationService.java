package com.coroptis.coidi.op.services;

import java.util.Date;

public interface AssociationService {

	/**
	 * Generate time to live for currently creates association handle.
	 * 
	 * @return
	 */
	Date getTimeToLive();

}
