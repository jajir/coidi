package com.coroptis.coidi.op.view.services;

import java.math.BigInteger;

import com.coroptis.coidi.op.view.entities.Association.AssociationType;
import com.coroptis.coidi.op.view.entities.Association.SessionType;

public class OpenIdRequestAssociation {

	private AssociationType associationType;

	private SessionType sessionType;
	
	private BigInteger dhConsumerPublic;
	
	private BigInteger dhGen;
	
	private BigInteger dhModulo;

	/**
	 * @return the associationType
	 */
	public AssociationType getAssociationType() {
		return associationType;
	}

	/**
	 * @param associationType the associationType to set
	 */
	public void setAssociationType(AssociationType associationType) {
		this.associationType = associationType;
	}

	/**
	 * @return the sessionType
	 */
	public SessionType getSessionType() {
		return sessionType;
	}

	/**
	 * @param sessionType the sessionType to set
	 */
	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}

	/**
	 * @return the dhConsumerPublic
	 */
	public BigInteger getDhConsumerPublic() {
		return dhConsumerPublic;
	}

	/**
	 * @param dhConsumerPublic the dhConsumerPublic to set
	 */
	public void setDhConsumerPublic(BigInteger dhConsumerPublic) {
		this.dhConsumerPublic = dhConsumerPublic;
	}

	/**
	 * @return the dhGen
	 */
	public BigInteger getDhGen() {
		return dhGen;
	}

	/**
	 * @param dhGen the dhGen to set
	 */
	public void setDhGen(BigInteger dhGen) {
		this.dhGen = dhGen;
	}

	/**
	 * @return the dhModulo
	 */
	public BigInteger getDhModulo() {
		return dhModulo;
	}

	/**
	 * @param dhModulo the dhModulo to set
	 */
	public void setDhModulo(BigInteger dhModulo) {
		this.dhModulo = dhModulo;
	}
	
}
