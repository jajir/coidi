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
package com.coroptis.coidi.core.message;

import java.math.BigInteger;
import java.util.Map;

import com.coroptis.coidi.op.entities.Association.AssociationType;
import com.coroptis.coidi.op.entities.Association.SessionType;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;

public class AssociationRequest extends AbstractOpenIdRequest {

    public final static String ASSOCIATION_TYPE = "assoc_type";
    public final static String SESSION_TYPE = "session_type";
    public final static String DH_MODULUS = "dh_modulus";
    public final static String DH_GENERATOR = "dh_gen";
    public final static String DH_CONSUMER_PUBLIC = "dh_consumer_public";

    public AssociationRequest() {
	super();
	setUrl(false);
	setMode(MODE_ASSOCIATE);
	setNameSpace(OPENID_NS_20);
    }

    public AssociationRequest(final Map<String, String> map) {
	super(map);
	setUrl(false);
	setMode(MODE_ASSOCIATE);
	setNameSpace(OPENID_NS_20);
    }

    @Override
    public String toString() {
	if (SessionType.NO_ENCRYPTION.equals(getSessionType())) {
	    return MoreObjects.toStringHelper(AssociationRequest.class).add(MODE, getMode())
		    .add(ASSOCIATION_TYPE, getAssociationType())
		    .add(SESSION_TYPE, getSessionType()).add(OPENID_NS, getNameSpace()).toString();
	} else {
	    return MoreObjects.toStringHelper(AssociationRequest.class).add(MODE, getMode())
		    .add(ASSOCIATION_TYPE, getAssociationType())
		    .add(SESSION_TYPE, getSessionType()).add(DH_MODULUS, getDhModulo())
		    .add(DH_CONSUMER_PUBLIC, getDhConsumerPublic()).add(DH_GENERATOR, getDhGen())
		    .add(OPENID_NS, getNameSpace()).toString();
	}
    }

    /**
     * This provide map, because it have to be written in to HTTP post with
     * http-client which is not part of this module.
     */
    @Override
    public Map<String, String> getMap() {
	return super.getMap();
    }

    /**
     * @return the associationType
     */
    public AssociationType getAssociationType() {
	return AssociationType.convert(get(ASSOCIATION_TYPE));
    }

    /**
     * @param associationType
     *            the associationType to set
     */
    public void setAssociationType(final AssociationType associationType) {
	put(ASSOCIATION_TYPE, associationType.getName());
    }

    /**
     * @return the sessionType
     */
    public SessionType getSessionType() {
	return SessionType.convert(get(SESSION_TYPE));
    }

    /**
     * @param sessionType
     *            the sessionType to set
     */
    public void setSessionType(final SessionType sessionType) {
	put(SESSION_TYPE, sessionType.getName());
    }

    /**
     * @return the dhConsumerPublic
     */
    public BigInteger getDhConsumerPublic() {
	return convertToBigIntegerFromString(get(DH_CONSUMER_PUBLIC));
    }

    /**
     * @param dhConsumerPublic
     *            the dhConsumerPublic to set
     */
    public void setDhConsumerPublic(final BigInteger dhConsumerPublic) {
	put(DH_CONSUMER_PUBLIC, convertToString(dhConsumerPublic));
    }

    /**
     * @return the dhGen
     */
    public BigInteger getDhGen() {
	if (Strings.isNullOrEmpty(get(DH_GENERATOR))) {
	    return null;
	} else {
	    return convertToBigIntegerFromString(get(DH_GENERATOR));
	}
    }

    /**
     * @param dhGen
     *            the dhGen to set
     */
    public void setDhGen(final BigInteger dhGen) {
	put(DH_GENERATOR, convertToString(dhGen));
    }

    /**
     * @return the dhModulo
     */
    public BigInteger getDhModulo() {
	if (Strings.isNullOrEmpty(get(DH_MODULUS))) {
	    return null;
	} else {
	    return convertToBigIntegerFromString(get(DH_MODULUS));
	}
    }

    /**
     * @param dhModulo
     *            the dhModulo to set
     */
    public void setDhModulo(final BigInteger dhModulo) {
	put(DH_MODULUS, convertToString(dhModulo));
    }

}
