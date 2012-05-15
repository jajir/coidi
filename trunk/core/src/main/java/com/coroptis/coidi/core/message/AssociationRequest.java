package com.coroptis.coidi.core.message;

import java.math.BigInteger;

import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;

public class AssociationRequest extends AbstractOpenIdRequest {

	public final static String ASSOCIATION_TYPE = "assoc_type";
	public final static String SESSION_TYPE = "session_type";
	public final static String DH_MODULUS = "dh_modulus";
	public final static String DH_GENERATOR = "dh_gen";
	public final static String DH_CONSUMER_PUBLIC = "dh_consumer_public";

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
