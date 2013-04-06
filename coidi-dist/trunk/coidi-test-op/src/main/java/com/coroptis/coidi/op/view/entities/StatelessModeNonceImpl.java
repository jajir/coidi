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
package com.coroptis.coidi.op.view.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.coroptis.coidi.op.entities.StatelessModeNonce;
import com.google.common.base.Objects;

@Entity
@Table(name = "stateless_mode_nonce")
public class StatelessModeNonceImpl extends AbstractEntity<StatelessModeNonceImpl> implements StatelessModeNonce {

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
	protected StatelessModeNonceImpl getThis() {
		return this;
	}

	@Override
	protected boolean dataEquals(final StatelessModeNonceImpl other) {
		if (!areEqual(nonce, other.getNonce()))
			return false;
		if (!areEqual(macKey, other.getMacKey()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(StatelessModeNonceImpl.class)
				.add(nonce, "nonce").add(macKey, "macKey").toString();
	}

	/* (non-Javadoc)
	 * @see com.coroptis.coidi.op.entities.StatelessModeNonce#getNonce()
	 */
	@Override
	public String getNonce() {
		return nonce;
	}

	/* (non-Javadoc)
	 * @see com.coroptis.coidi.op.entities.StatelessModeNonce#setNonce(java.lang.String)
	 */
	@Override
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	/* (non-Javadoc)
	 * @see com.coroptis.coidi.op.entities.StatelessModeNonce#getMacKey()
	 */
	@Override
	public String getMacKey() {
		return macKey;
	}

	/* (non-Javadoc)
	 * @see com.coroptis.coidi.op.entities.StatelessModeNonce#setMacKey(java.lang.String)
	 */
	@Override
	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}

}