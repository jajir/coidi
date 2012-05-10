package com.coroptis.coidi.op.view.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.tapestry5.beaneditor.NonVisual;

import com.google.common.base.Objects;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NonVisual
	@Column(name="id_user")
	private Integer idUser;

	@Column(unique = true, nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 50)
	private String password;

	 @OneToMany(cascade = CascadeType.ALL)
	 @JoinTable()
	private Set<Identity> identities;

	@Override
	public String toString() {
		return Objects.toStringHelper(User.class).add("idUser", idUser)
				.add("name", name).toString();
	}

	/**
	 * @return the idUser
	 */
	public Integer getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser
	 *            the idUser to set
	 */
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the identities
	 */
	public Set<Identity> getIdentities() {
		return identities;
	}

	/**
	 * @param identities
	 *            the identities to set
	 */
	public void setIdentities(Set<Identity> identities) {
		this.identities = identities;
	}

}