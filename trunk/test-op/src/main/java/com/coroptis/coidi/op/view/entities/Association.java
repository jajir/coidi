package com.coroptis.coidi.op.view.entities;

import java.util.Date;

import com.google.common.base.Objects;

public class Association extends AbstractEntity<Association> {

	public static enum AssociationType {
		HMAC_SHA1("HMAC-SHA1", 20), HMAC_SHA256("HMAC-SHA256", 32);

		private final String name;

		private final Integer sectetLength;

		private AssociationType(final String name, final Integer sectetLength) {
			this.name = name;
			this.sectetLength = sectetLength;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		public static final AssociationType convert(String str) {
			if (HMAC_SHA1.getName().equals(str)) {
				return HMAC_SHA1;
			} else if (HMAC_SHA256.getName().equals(str)) {
				return HMAC_SHA256;
			}
			return null;
		}

		/**
		 * @return the sectetLength
		 */
		public Integer getSectetLength() {
			return sectetLength;
		}

	}

	public static enum SessionType {
		no_encription("no-encription", 0), DH_SHA1("DH-SHA1", 20), DH_SHA256(
				"DH-SHA256", 32);

		private final String name;

		private final Integer sectetLength;

		private SessionType(final String name, final Integer sectetLength) {
			this.name = name;
			this.sectetLength = sectetLength;
		}

		public static final SessionType convert(String str) {
			if (DH_SHA1.getName().equals(str)) {
				return DH_SHA1;
			} else if (DH_SHA256.getName().equals(str)) {
				return DH_SHA256;
			} else if (no_encription.getName().equals(str)) {
				return no_encription;
			}
			return null;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the sectetLength
		 */
		public Integer getSectetLength() {
			return sectetLength;
		}
	}

	private String assocHandle;

	private AssociationType associationType;

	private SessionType sessionType;

	private String macKey;

	private Date expiredIn;

	@Override
	public String toString() {
		return Objects.toStringHelper(Association.class)
				.add("assocHandle", assocHandle).toString();
		// TODO
	}

	@Override
	protected Object[] getHashCodeData() {
		// TODO
		return null;
	}

	@Override
	protected Association getThis() {
		// TODO
		return this;
	}

	@Override
	protected boolean dataEquals(Association other) {
		return false;
	}

	/**
	 * @return the assocHandle
	 */
	public String getAssocHandle() {
		return assocHandle;
	}

	/**
	 * @param assocHandle
	 *            the assocHandle to set
	 */
	public void setAssocHandle(String assocHandle) {
		this.assocHandle = assocHandle;
	}

	/**
	 * @return the associationType
	 */
	public AssociationType getAssociationType() {
		return associationType;
	}

	/**
	 * @param associationType
	 *            the associationType to set
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
	 * @param sessionType
	 *            the sessionType to set
	 */
	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}

	/**
	 * @return the macKey
	 */
	public String getMacKey() {
		return macKey;
	}

	/**
	 * @param macKey
	 *            the macKey to set
	 */
	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}

	/**
	 * @return the expiredIn
	 */
	public Date getExpiredIn() {
		return expiredIn;
	}

	/**
	 * @param expiredIn
	 *            the expiredIn to set
	 */
	public void setExpiredIn(Date expiredIn) {
		this.expiredIn = expiredIn;
	}

	// private byte[] encryptedMacKey;
	//
	// private BigInteger publicKey;

}
