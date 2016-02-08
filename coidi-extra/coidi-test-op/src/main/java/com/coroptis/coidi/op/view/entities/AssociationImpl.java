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

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;
import com.google.common.base.Objects;

@Entity
@Table(name = "association")
public class AssociationImpl extends AbstractEntity<AssociationImpl> implements Association {

    @Id
    @Column(nullable = false, length = 50, name = "assoc_handle")
    private String assocHandle;

    @Column(nullable = false, length = 50, name = "assoc_type")
    private AssociationType associationType;

    @Column(nullable = true, length = 50, name = "session_type")
    private SessionType sessionType;

    @Column(nullable = false, length = 50, name = "mac_key")
    private String macKey;

    @Column(nullable = false, name = "expired_in")
    private Date expiredIn;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "association")
    private Set<NonceImpl> nonces;

    @Override
    public String toString() {
	return Objects.toStringHelper(AssociationImpl.class).add("assocHandle", assocHandle)
		.add("associationType", associationType).add("expiredIn", expiredIn)
		.add("macKey", macKey).add("sessionType", sessionType).toString();
    }

    @Override
    protected Object[] getHashCodeData() {
	return new Object[] { assocHandle, associationType, expiredIn, macKey, sessionType };
    }

    @Override
    protected AssociationImpl getThis() {
	return this;
    }

    @Override
    protected boolean dataEquals(AssociationImpl other) {
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
    @Override
    public String getAssocHandle() {
	return assocHandle;
    }

    /**
     * @param assocHandle
     *            the assocHandle to set
     */
    @Override
    public void setAssocHandle(String assocHandle) {
	this.assocHandle = assocHandle;
    }

    /**
     * @return the associationType
     */
    @Override
    public AssociationType getAssociationType() {
	return associationType;
    }

    /**
     * @param associationType
     *            the associationType to set
     */
    @Override
    public void setAssociationType(AssociationType associationType) {
	this.associationType = associationType;
    }

    /**
     * @return the sessionType
     */
    @Override
    public SessionType getSessionType() {
	return sessionType;
    }

    /**
     * @param sessionType
     *            the sessionType to set
     */
    @Override
    public void setSessionType(SessionType sessionType) {
	this.sessionType = sessionType;
    }

    /**
     * @return the macKey
     */
    @Override
    public String getMacKey() {
	return macKey;
    }

    /**
     * @param macKey
     *            the macKey to set
     */
    @Override
    public void setMacKey(String macKey) {
	this.macKey = macKey;
    }

    /**
     * @return the expiredIn
     */
    @Override
    public Date getExpiredIn() {
	return expiredIn;
    }

    /**
     * @param expiredIn
     *            the expiredIn to set
     */
    @Override
    public void setExpiredIn(Date expiredIn) {
	this.expiredIn = expiredIn;
    }

    /**
     * @return the nonces
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Set<Nonce> getNonces() {
	return (Set) nonces;
    }

    /**
     * @param nonces
     *            the nonces to set
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setNonces(Set<Nonce> nonces) {
	this.nonces = (Set) nonces;
    }

}
