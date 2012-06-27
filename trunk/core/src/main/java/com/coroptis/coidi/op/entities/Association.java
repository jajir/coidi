/**
 * Copyright 2012 coroptis.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.coroptis.coidi.op.entities;

import java.util.Date;

public interface Association {

	/**
	 * This define algorithm used for signing message with secret mac key.
	 * 
	 * @author jan
	 * 
	 */
	public static enum AssociationType {
		HMAC_SHA1("HMAC-SHA1", "HmacSHA1", 20), HMAC_SHA256("HMAC-SHA256",
				"HmacSHA256", 32);

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

		private AssociationType(final String name, final String algorithmName,
				final Integer sectetLength) {
			this.name = name;
			this.algorithmName = algorithmName;
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

		/**
		 * @return the algorithmName
		 */
		public String getAlgorithmName() {
			return algorithmName;
		}

	}

	/**
	 * This define algorithm used for generating secret mac key.
	 * 
	 * @author jan
	 * 
	 */
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
