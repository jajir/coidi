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
package com.coroptis.coidi;

/**
 * This interface contains string constants for YADIS protocol discovery
 * process.
 * 
 * @author jirout
 * 
 */
public interface OpenIdNs {

    /**
     * Type value for OpenID version 1.0.
     */
    public static final String TYPE_OPENID_1_0 = "http://openid.net/server/1.0";

    /**
     * Type value for OpenID version 1.1.
     */
    public static final String TYPE_OPENID_1_1 = "http://openid.net/server/1.1";

    /**
     * Type value for OpenID version 2.0.
     */
    public static final String TYPE_OPENID_2_0 = "http://specs.openid.net/auth/2.0/server";
    public static final String TYPE_ATTRIBUTE_EXCHANGE_1_0 = "http://openid.net/srv/ax/1.0";
    public static final String TYPE_UI_POPUP_1_0 = "http://specs.openid.net/extensions/ui/1.0/mode/popup";
    public static final String TYPE_UI_ICON_1_0 = "http://specs.openid.net/extensions/ui/1.0/icon";
    public static final String TYPE_PAPE_1_0 = "http://specs.openid.net/extensions/pape/1.0";
    public static final String TYPE_SREG_1_0 = "http://openid.net/sreg/1.0";
    /**
     * This is unofficial sreg discovery type.
     */
    public static final String ALTERNATE_DISCOVERY_TYPE_SREG_1_1 = "http://openid.net/sreg/1.1";
    public static final String TYPE_SREG_1_1 = "http://openid.net/extensions/sreg/1.1";
    public static final String TYPE_CLAIMED_IDENTIFIER_ELEMENT_1_0 = "http://openid.net/signon/1.0";
    public static final String TYPE_CLAIMED_IDENTIFIER_ELEMENT_2_0 = "http://specs.openid.net/auth/2.0/signon";

}