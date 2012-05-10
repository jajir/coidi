package com.coroptis.coidi.rp.view.services;



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
