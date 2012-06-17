package com.coroptis.coidi.op.services;

import com.coroptis.coidi.op.entities.Identity;


/**
 * 
 * @author jan
 * 
 */
public interface IdentityService {

	/**
	 * Get identity by it's name. Name is unique within OP.
	 * 
	 * @param idIdentity
	 *            required idenitity's name (id)
	 * @return {@link Identity} object if there is any otherwise
	 *         <code>null</code>
	 */
	Identity getIdentityByName(String idIdentity);

}
