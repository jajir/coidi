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



public class HttpHelper {

	
	
    /**
     * Makes a HTTP call to the specified URL with the parameters specified
     * in the Message.
     *
     * @param url       URL endpoint for the HTTP call
     * @param request   Message containing the parameters
     * @param response  ParameterList that will hold the parameters received in
     *                  the HTTP response
     * @return          the status code of the HTTP call
     */
//    public int call(String url, Message request, ParameterList response)
//            throws MessageException
//    {
//        int responseCode = -1;
//        try
//        {
//            if (DEBUG) _log.debug("Performing HTTP POST on " + url);
//            HttpResponse resp = _httpFetcher.post(url, request.getParameterMap());
//            responseCode = resp.getStatusCode();
//
//            String postResponse = resp.getBody();
//            response.copyOf(ParameterList.createFromKeyValueForm(postResponse));
//
//            if (DEBUG) _log.debug("Retrived response:\n" + postResponse);
//        }
//        catch (IOException e)
//        {
//            _log.error("Error talking to " + url +
//                    " response code: " + responseCode, e);
//        }
//        return responseCode;
//    }
}
