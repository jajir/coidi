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
	public enum Gendre {
		M, F
	}

	@Id
	@NonVisual
	@Column(nullable = false, length = 50, name = "id_identity")
	private String idIdentity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private User user;

	@Column(length = 50)
	private String nickname;

	@Column(length = 250)
	private String email;

	@Column(length = 250)
	private String fullname;

	@Column()
	private Date dob;

	@Column()
	private Gendre gendre;

	@Column(length = 10)
	private String postcode;

	@Column(length = 50)
	private String country;

	@Column(length = 10)
	private String language;

	@Column(length = 50)
	private String timezone;

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

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fullname
	 */
	public String getFullname() {
		return fullname;
	}

	/**
	 * @param fullname
	 *            the fullname to set
	 */
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the gendre
	 */
	public Gendre getGendre() {
		return gendre;
	}

	/**
	 * @param gendre
	 *            the gendre to set
	 */
	public void setGendre(Gendre gendre) {
		this.gendre = gendre;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode
	 *            the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone
	 *            the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

}
