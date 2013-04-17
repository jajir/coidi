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

import java.util.HashMap;
import java.util.Map;

import com.coroptis.coidi.op.entities.Association.SessionType;
import com.coroptis.coidi.rp.services.AuthReq;

/**
 * Object hold parameters of authentication request. Based on information from
 * this class is created authentication request and also applied extensions.
 * 
 * <p>
 * Creating of authentication request id done in chain of commands of
 * {@link AuthReq} implementations.
 * </p>
 * 
 * @author jan
 * @see AuthReq
 */
public class AuthenticationParameters {

    private SessionType sessionType;
    private String mode;
    private String userSuppliedId;
    private String returnTo;
    private final Map<String, String> parameters;

    public AuthenticationParameters() {
	parameters = new HashMap<String, String>();
    }

    /**
     * @return the sessionType
     */
    public SessionType getSessionType() {
	return sessionType;
    }

    /**
     * @param sessionType
     *            the sessionType to set
     */
    public void setSessionType(SessionType sessionType) {
	this.sessionType = sessionType;
    }

    /**
     * @return the mode
     */
    public String getMode() {
	return mode;
    }

    /**
     * @param mode
     *            the mode to set
     */
    public void setMode(String mode) {
	this.mode = mode;
    }

    /**
     * @return the userSuppliedId
     */
    public String getUserSuppliedId() {
	return userSuppliedId;
    }

    /**
     * @param userSuppliedId
     *            the userSuppliedId to set
     */
    public void setUserSuppliedId(String userSuppliedId) {
	this.userSuppliedId = userSuppliedId;
    }

    /**
     * @return the returnTo
     */
    public String getReturnTo() {
	return returnTo;
    }

    /**
     * @param returnTo
     *            the returnTo to set
     */
    public void setReturnTo(String returnTo) {
	this.returnTo = returnTo;
    }

    /**
     * @return the parameters
     */
    public Map<String, String> getParameters() {
	return parameters;
    }

}
