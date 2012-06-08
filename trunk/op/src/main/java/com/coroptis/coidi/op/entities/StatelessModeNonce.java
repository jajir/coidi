package com.coroptis.coidi.op.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Table(name = "stateless_mode_nonce")
public class StatelessModeNonce extends AbstractEntity<StatelessModeNonce> {

	@Id
	@Column(nullable = false, length = 50, name = "nonce")
	private String nonce;

	@Column(nullable = false, length = 50, name = "mac_key")
	private String macKey;

	@Override
	protected Object[] getHashCodeData() {
		return new Object[] { nonce, macKey };
	}

	@Override
	protected StatelessModeNonce getThis() {
		return this;
	}

	@Override
	protected boolean dataEquals(final StatelessModeNonce other) {
		if (!areEqual(nonce, other.getNonce()))
			return false;
		if (!areEqual(macKey, other.getMacKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(StatelessModeNonce.class)
				.add(nonce, "nonce").add(macKey, "macKey").toString();
	}

	/**
	 * @return the nonce
	 */
	public String getNonce() {
		return nonce;
	}

	/**
	 * @param nonce
	 *            the nonce to set
	 */
	public void setNonce(String nonce) {
		this.nonce = nonce;
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

}
