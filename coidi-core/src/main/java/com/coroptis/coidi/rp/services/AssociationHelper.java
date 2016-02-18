package com.coroptis.coidi.rp.services;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AssociationResponse;

public interface AssociationHelper {

	/**
	 * Verify that association response is OpenID 2.0 name space.
	 * 
	 * @param associationResponse
	 *            required message
	 * @throws CoidiException
	 *             when it's not valid OpenID 2.0 message
	 */
	void verifyNameSpace20(AssociationResponse associationResponse);

	/**
	 * Verify that required field are filled. When one of fields is not filled
	 * than {@link CoidiException} is thrown.
	 * 
	 * @param associationResponse
	 *            required message
	 * @throws CoidiException
	 *             when it's not valid OpenID 2.0 message
	 */
	void verify(AssociationResponse associationResponse);

}
