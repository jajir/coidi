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
package com.coroptis.coidi.core.services;

import com.coroptis.coidi.core.message.AbstractMessage;
import com.coroptis.coidi.op.entities.Association;
import com.coroptis.coidi.op.entities.Association.AssociationType;

public interface SigningService {

    String sign(AbstractMessage response, Association association);

    /**
     * 
     * @param toSign
     *            required message to sign
     * @param macKey
     *            required signing macKey
     * @param associationType
     *            required association type
     * @return signed message
     */
    String plainSign(String toSign, String macKey, AssociationType associationType);
}
