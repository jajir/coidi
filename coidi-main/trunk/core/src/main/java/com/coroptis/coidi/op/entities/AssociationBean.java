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

import com.google.common.base.Objects;

public class AssociationBean implements Association {

	private String assocHandle;

	private AssociationType associationType;

	private SessionType sessionType;

	private String macKey;

	private Date expiredIn;

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

	@Override
	public String toString() {
		return Objects.toStringHelper(AssociationBean.class)
				.add("assocHandle", assocHandle)
				.add("associationType", associationType)
				.add("expiredIn", expiredIn).add("macKey", macKey)
				.add("sessionType", sessionType).toString();
	}
}
