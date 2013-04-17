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

import org.w3c.dom.Node;

public interface XmlProcessing {

    boolean isThereTag(Node node, String tagName, String value);

    String getTagContent(Node node, String tagName);

    /**
     * Extract from HTML document meta from header which have value of attribute
     * 'http-equiv' equal name and return value of attribute 'content'.
     * 
     * @param htmlDocument
     * @param name
     * @return
     */
    String getMetaContent(String htmlDocument, String name);

    /**
     * Verify if given document is XRDS.
     * <p>
     * This method just check requirement for valid XRDS document. Full XML
     * validation could be done during parsing.
     * </p>
     * 
     * @param document
     *            required tested document
     * @return return <code>true</code> when document id XML XRDS document,
     *         otherwise return false.
     */
    Boolean isXrdsDocument(String document);
}
