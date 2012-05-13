package com.coroptis.coidi.op.view.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = "association")
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

	@Id
	@Column(nullable = false, length = 50, name = "assoc_handle")
	private String assocHandle;

	@Column(nullable = false, length = 50, name = "assoc_type")
	private AssociationType associationType;

	@Column(nullable = false, length = 50, name = "session_type")
	private SessionType sessionType;

	@Column(nullable = false, length = 50, name = "mac_key")
	private String macKey;

	@Column(nullable = false, name = "expired_in")
	private Date expiredIn;

	@Override
	public String toString() {
		return Objects.toStringHelper(Association.class)
				.add("assocHandle", assocHandle)
				.add("associationType", associationType)
				.add("expiredIn", expiredIn).add("macKey", macKey)
				.add("sessionType", sessionType).toString();
	}

	@Override
	protected Object[] getHashCodeData() {
		return new Object[] { assocHandle, associationType, expiredIn, macKey,
				sessionType };
	}

	@Override
	protected Association getThis() {
		return this;
	}

	@Override
	protected boolean dataEquals(Association other) {
		if (!areEqual(assocHandle, other.getAssocHandle()))
			return false;
		if (!areEqual(associationType, other.getAssociationType()))
			return false;
		if (!areEqual(expiredIn, other.getExpiredIn()))
			return false;
		if (!areEqual(macKey, other.getMacKey()))
			return false;
		if (!areEqual(sessionType, other.getSessionType()))
			return false;
		return true;
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

}
