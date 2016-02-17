package com.coroptis.coidi.rp.services.impl;

import com.coroptis.coidi.CoidiException;
import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.core.message.AssociationResponse;
import com.coroptis.coidi.rp.services.AssociationHelper;
import com.google.common.base.Strings;

public class AssociationHelperImpl implements AssociationHelper {

	@Override
	public void verify(final AssociationResponse associationResponse) {
		checkMandatoryField(associationResponse, "assoc_handle");
		checkMandatoryField(associationResponse, "dh_server_public");
		checkMandatoryField(associationResponse, "session_type");
		checkMandatoryField(associationResponse, "expires_in");
		checkMandatoryField(associationResponse, "enc_mac_key");
		checkMandatoryField(associationResponse, "assoc_type");
	}

	public void verifyNameSpace20(final AssociationResponse message) {
		if (Strings.isNullOrEmpty(message.getNameSpace())) {
			throw new CoidiException("OpenID namespace was not filled.");
		}
		if (!AbstractMessage.OPENID_NS_20.equals(message.getNameSpace())) {
			throw new CoidiException("OpenID namespace contains invalid value '" + message.getNameSpace() + "'.");
		}
	}

	private void checkMandatoryField(final AssociationResponse associationResponse, final String field) {
		if (Strings.isNullOrEmpty(associationResponse.get(field))) {
			new CoidiException("Mandatory field '" + field + "' is empty");
		}
	}

}
