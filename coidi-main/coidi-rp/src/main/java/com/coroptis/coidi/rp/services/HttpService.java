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
package com.coroptis.coidi.rp.services;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.coroptis.coidi.CoidiException;

public interface HttpService {

    /**
     * Provide access to configured {@link HttpClient}.
     * 
     * @return {@link HttpClient} object
     * @throws CoidiException
     */
    DefaultHttpClient getHttpClient() throws CoidiException;

    List<NameValuePair> toList(Map<String, String> parameters);

    Map<String, String> convertToMap(String body);
}
