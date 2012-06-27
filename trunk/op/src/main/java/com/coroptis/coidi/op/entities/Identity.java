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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.tapestry5.beaneditor.NonVisual;

import com.google.common.base.Objects;

@Entity
@Table(name = "identity")
public class Identity extends AbstractEntity<Identity> {

	@Id
	@NonVisual
	@Column(nullable = false, length = 50, name = "id_identity")
	private String idIdentity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private User user;

	@Override
	public String toString() {
		return Objects.toStringHelper(Identity.class)
				.add("idIdentity", idIdentity).toString();
	}

	/**
	 * @return the idIdentity
	 */
	public String getIdIdentity() {
		return idIdentity;
	}

	/**
	 * @param idIdentity
	 *            the idIdentity to set
	 */
	public void setIdIdentity(String idIdentity) {
		this.idIdentity = idIdentity;
	}

	@Override
	protected Object[] getHashCodeData() {
		return new Object[] { idIdentity };
	}

	@Override
	protected Identity getThis() {
		return this;
	}

	@Override
	protected boolean dataEquals(Identity other) {
		if (!areEqual(idIdentity, other.getIdIdentity()))
			return false;
		return true;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
