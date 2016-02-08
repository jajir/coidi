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
package com.coroptis.coidi.op.util;

import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Nonce;

public class NonceBean implements Nonce {

    private String nonce;

    private Association association;

    /**
     * @return the nonce
     */
    @Override
    public String getNonce() {
	return nonce;
    }

    /**
     * @param nonce
     *            the nonce to set
     */
    @Override
    public void setNonce(String nonce) {
	this.nonce = nonce;
    }

    /**
     * @return the association
     */
    @Override
    public Association getAssociation() {
	return association;
    }

    /**
     * @param association
     *            the association to set
     */
    @Override
    public void setAssociation(Association association) {
	this.association = association;
    }
}
