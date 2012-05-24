package com.coroptis.coidi.core.message;

import java.math.BigInteger;
import java.util.Map;

import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;

public class AssociationResponse extends AbstractOpenIdResponse {

	public final static String ENC_MAC_KEY = "enc_mac_key";
	public final static String MAC_KEY = "mac_key";
	public final static String DH_SERVER_PUBLIC = "dh_server_public";
	public final static String SESSION_TYPE = "session_type";
	public final static String ASSOCIATION_TYPE = "assoc_type";
	public final static String ASSOC_HANDLE = "assoc_handle";
	public final static String EXPIRES_IN = "expires_in";

	public AssociationResponse() {
		super();
		setUrl(false);
		setNameSpace(OPENID_NS_20);
	}

	public AssociationResponse(final Map<String, String> map) {
		super(map);
		setUrl(false);
		setNameSpace(OPENID_NS_20);
	}

	/**
	 * @return the associationType
	 */
	public AssociationType getAssociationType() {
		return AssociationType.convert(get(ASSOCIATION_TYPE));
	}

	/**
	 * @param associationType
	 *            the associationType to set
	 */
	public void setAssociationType(final AssociationType associationType) {
		put(ASSOCIATION_TYPE, associationType.getName());
	}

	/**
	 * @return the sessionType
	 */
	public SessionType getSessionType() {
		return SessionType.convert(get(SESSION_TYPE));
	}

	/**
	 * @param sessionType
	 *            the sessionType to set
	 */
	public void setSessionType(final SessionType sessionType) {
		put(SESSION_TYPE, sessionType.getName());
	}

	/**
	 * @return the dhServerPublic
	 */
	public BigInteger getDhServerPublic() {
		return convertToBigIntegerFromString(get(DH_SERVER_PUBLIC));
	}

	/**
	 * @param dhServerPublic
	 *            the dhServerPublic to set
	 */
	public void setDhServerPublic(final BigInteger dhServerPublic) {
		put(DH_SERVER_PUBLIC, convertToString(dhServerPublic));
	}

	/**
	 * @return the assocHandle
	 */
	public String getAssocHandle() {
		return get(ASSOC_HANDLE);
	}

	/**
	 * @param assocHandle
	 *            the assocHandle to set
	 */
	public void setAssocHandle(final String assocHandle) {
		put(ASSOC_HANDLE, assocHandle);
	}

	/**
	 * @return the expiresIn
	 */
	public String getExpiresIn() {
		return get(EXPIRES_IN);
	}

	/**
	 * @param expiresIn
	 *            the expiresIn to set
	 */
	public void setExpiresIn(final String expiresIn) {
		put(EXPIRES_IN, expiresIn);
	}

	/**
	 * @return the encMacKey
	 */
	public byte[] getEncMacKey() {
		return convertToBytes(get(ENC_MAC_KEY));
	}

	/**
	 * @param encMacKey
	 *            the encMacKey to set
	 */
	public void setEncMacKey(final byte[] encMacKey) {
		put(ENC_MAC_KEY, convertToString(encMacKey));
	}

	/**
	 * @return the macKey
	 */
	public byte[] getMacKey() {
		return convertToBytes(get(MAC_KEY));
	}

	/**
	 * @param macKey
	 *            the macKey to set
	 */
	public void setMacKey(final byte[] macKey) {
		put(MAC_KEY, convertToString(macKey));
	}
}
