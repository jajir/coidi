package com.coroptis.coidi.op.entities;

import java.util.Date;

public interface Association {

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
		no_encription("no-encryption", null, 0), DH_SHA1("DH-SHA1", "SHA-1", 20), DH_SHA256(
				"DH-SHA256", "SHA-256", 32);

		/**
		 * Open id name. It's identification from open id specification.
		 */
		private final String name;

		/**
		 * Name of algorithm in Java word in JCE.
		 */
		private final String algorithmName;

		/**
		 * Length of digest in bytes computed with defined algorithm from
		 * message.
		 */
		private final Integer sectetLength;

		private SessionType(final String name, final String algorithmName,
				final Integer sectetLength) {
			this.name = name;
			this.algorithmName = algorithmName;
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

		/**
		 * @return the algorithmName
		 */
		public String getAlgorithmName() {
			return algorithmName;
		}
	}

	String getAssocHandle();

	AssociationType getAssociationType();

	SessionType getSessionType();

	Date getExpiredIn();

	String getMacKey();

}
