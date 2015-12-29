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

import java.util.Map;

public class CheckAuthenticationResponse extends AbstractOpenIdResponse {

    public final static String IS_VALID = "is_valid";
    public final static String INVALIDATE_HANDLE = "invalidate_handle";

    public CheckAuthenticationResponse() {
	super();
	setNameSpace(OPENID_NS_20);
	setUrl(false);
    }

    public CheckAuthenticationResponse(final Map<String, String> map) {
	super(map);
	setNameSpace(OPENID_NS_20);
	setUrl(false);
    }

    /**
     * @return the isValid
     */
    public Boolean getIsValid() {
	return Boolean.valueOf(get(IS_VALID));
    }

    /**
     * @param isValid
     *            the isValid to set
     */
    public void setIsValid(final Boolean isValid) {
	put(IS_VALID, isValid.toString());
    }

    /**
     * @return the invalidateHandle
     */
    public String getInvalidateHandle() {
	return get(INVALIDATE_HANDLE);
    }

    /**
     * @param invalidateHandle
     *            the invalidateHandle to set
     */
    public void setInvalidateHandle(final String invalidateHandle) {
	put(INVALIDATE_HANDLE, invalidateHandle);
    }

}
