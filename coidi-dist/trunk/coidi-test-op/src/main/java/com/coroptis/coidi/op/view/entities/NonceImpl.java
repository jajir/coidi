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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;
import com.google.common.base.Objects;

@Entity
@Table(name = "stateless_mode_nonce")
public class NonceImpl extends AbstractEntity<NonceImpl> implements Nonce {

    @Id
    @Column(nullable = false, length = 50, name = "nonce")
    private String nonce;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "assocHandle")
    private AssociationImpl association;

    @Override
    protected Object[] getHashCodeData() {
	return new Object[] { nonce };
    }

    @Override
    protected NonceImpl getThis() {
	return this;
    }

    @Override
    protected boolean dataEquals(final NonceImpl other) {
	if (!areEqual(nonce, other.getNonce()))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return Objects.toStringHelper(NonceImpl.class).add(nonce, "nonce").toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.coroptis.coidi.op.entities.StatelessModeNonce#getNonce()
     */
    @Override
    public String getNonce() {
	return nonce;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.coroptis.coidi.op.entities.StatelessModeNonce#setNonce(java.lang.
     * String)
     */
    @Override
    public void setNonce(String nonce) {
	this.nonce = nonce;
    }

    /**
     * @return the association
     */
    @Override
    public AssociationImpl getAssociation() {
	return association;
    }

    /**
     * @param association
     *            the association to set
     */
    @Override
    public void setAssociation(Association association) {
	this.association = (AssociationImpl) association;
    }

}
