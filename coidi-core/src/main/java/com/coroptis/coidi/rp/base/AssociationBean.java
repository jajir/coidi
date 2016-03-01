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
package com.coroptis.coidi.rp.base;

import java.util.Date;
import java.util.Set;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;
import com.google.common.base.MoreObjects;

/**
 * Simple {@link Association} implementation. Contains just setter and getters
 * for private fields containing values.
 * <p>
 * Class in used at RP and could be stored in session.
 * </p>
 * 
 * @author jirout
 * 
 */
public class AssociationBean implements Association {

    private String assocHandle;

    private AssociationType associationType;

    private SessionType sessionType;

    private String macKey;

    private Date expiredIn;

    private Set<Nonce> nonces;

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

    @Override
    public String toString() {
	return MoreObjects.toStringHelper(AssociationBean.class).add("assocHandle", assocHandle)
		.add("associationType", associationType).add("expiredIn", expiredIn)
		.add("macKey", macKey).add("sessionType", sessionType).toString();
    }

    /**
     * @return the nonces
     */
    @Override
    public Set<Nonce> getNonces() {
	return nonces;
    }

    /**
     * @param nonces
     *            the nonces to set
     */
    @Override
    public void setNonces(Set<Nonce> nonces) {
	this.nonces = nonces;
    }

}
