package com.coroptis.coidi.core.message;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.google.common.base.Objects;

public class AssociationRequest extends AbstractOpenIdRequest {

	public final static String ASSOCIATION_TYPE = "assoc_type";
	public final static String SESSION_TYPE = "session_type";
	public final static String DH_MODULUS = "dh_modulus";
	public final static String DH_GENERATOR = "dh_gen";
	public final static String DH_CONSUMER_PUBLIC = "dh_consumer_public";

	public AssociationRequest() {
		super();
		setUrl(false);
		setMode(MODE_ASSOCIATE);
		setNameSpace(OPENID_NS_20);
	}

	public AssociationRequest(final Map<String, String> map) {
		super(map);
		setUrl(false);
		setMode(MODE_ASSOCIATE);
		setNameSpace(OPENID_NS_20);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(AssociationRequest.class)
				.add(MODE, getMode())
				.add(ASSOCIATION_TYPE, getAssociationType())
				.add(SESSION_TYPE, getSessionType())
				.add(DH_MODULUS, getDhModulo())
				.add(DH_CONSUMER_PUBLIC, getDhConsumerPublic())
				.add(DH_GENERATOR, getDhGen()).add(OPENID_NS, getNameSpace())
				.toString();
	}

	/**
	 * This provide map, because it have to be written in to HTTP post with
	 * http-client which is not part of this module.
	 */
	public Map<String, String> getMap() {
		Map<String, String> out = new HashMap<String, String>();
		for (Entry<String, String> entry : super.getMap().entrySet()) {
			out.put(OPENID + entry.getKey(), entry.getValue());
		}
		return out;
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
	 * @return the dhConsumerPublic
	 */
	public BigInteger getDhConsumerPublic() {
		return convertToBigIntegerFromString(get(DH_CONSUMER_PUBLIC));
	}

	/**
	 * @param dhConsumerPublic
	 *            the dhConsumerPublic to set
	 */
	public void setDhConsumerPublic(final BigInteger dhConsumerPublic) {
		put(DH_CONSUMER_PUBLIC, convertToString(dhConsumerPublic));
	}

	/**
	 * @return the dhGen
	 */
	public BigInteger getDhGen() {
		return convertToBigIntegerFromString(get(DH_GENERATOR));
	}

	/**
	 * @param dhGen
	 *            the dhGen to set
	 */
	public void setDhGen(BigInteger dhGen) {
		put(DH_GENERATOR, convertToString(dhGen));
	}

	/**
	 * @return the dhModulo
	 */
	public BigInteger getDhModulo() {
		return convertToBigIntegerFromString(get(DH_MODULUS));
	}

	/**
	 * @param dhModulo
	 *            the dhModulo to set
	 */
	public void setDhModulo(BigInteger dhModulo) {
		put(DH_MODULUS, convertToString(dhModulo));
	}

}
