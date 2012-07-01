package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.core.message.AuthenticationResponse;
import com.coroptis.coidi.rp.base.AuthRespExtension;

/**
 * Object hold information from one extension. Each OpenID extension have one
 * implementation. When implementation detect and decode concrete extension than
 * resolved object is returned.
 * 
 * @author jan
 * 
 */
public interface AuthRespDecoder {

	/**
	 * 
	 * @param authenticationResponse
	 * @param key
	 * @param nameSpace
	 * @return
	 */
	AuthRespExtension decode(AuthenticationResponse authenticationResponse,
			String key, String nameSpace);

}
