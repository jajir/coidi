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

/**
 * Nonce is random string that prevent re-using messages. In some cases should
 * be persisted on OP and on RP.
 * 
 * @author jirout
 * 
 */
public interface Nonce {

    /**
     * @return the nonce
     */
    String getNonce();

    /**
     * @param nonce
     *            the nonce to set
     */
    void setNonce(String nonce);

    /**
     * @return the association
     */
    Association getAssociation();

    /**
     * @param association
     *            the association to set
     */
    void setAssociation(Association association);

}