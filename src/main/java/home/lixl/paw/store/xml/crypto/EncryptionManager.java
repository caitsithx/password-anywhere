/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package home.lixl.paw.store.xml.crypto;

import home.lixl.paw.store.xml.XmlSecureStoreException;
import home.lixl.paw.store.xml.util.XMLManager;
import org.apache.xml.security.utils.EncryptionConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This sample demonstrates how to encrypt data inside an xml document.
 *
 * @author Vishal Mahajan (Sun Microsystems)
 */
public class EncryptionManager {

    public static final String GROUP = "Group";
    public static final String PAIR = "Pair";
    /**
     * {@link org.apache.commons.logging} logging facility
     */
    static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
            .getLog(EncryptionManager.class.getName());

    static {
        org.apache.xml.security.Init.init();
    }

    XmlCiphers cipherWrapper = new DESedeAESCiphers();

    static List<Element> searchAndDec(Element pairElement) {
        NodeList nodeList = pairElement.getElementsByTagNameNS(
                EncryptionConstants.EncryptionSpecNS,
                EncryptionConstants._TAG_ENCRYPTEDDATA);

        if (nodeList.getLength() > 0) {
            List<Element> elements = new ArrayList<>(nodeList.getLength());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node child = nodeList.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    elements.add((Element) child);
                }
            }

            return elements;
        }

        return Collections.emptyList();
    }

    static List<Element> getPairs(Element groupElement) {
        return XMLManager.searchChildren(groupElement, PAIR);
    }

    public void decryptAll(char[] password, final Document decXmlDocument) {
        final ElementDecryption xmlCipher = cipherWrapper.forDecryption(password, decXmlDocument);

        List<Element> groupElements = XMLManager.searchChildren(decXmlDocument.getDocumentElement(), GROUP);
        groupElements.stream()
//                .map(EncryptionManager::getPairs)
//                .flatMap(List::stream)
                .map(EncryptionManager::searchAndDec)
                .flatMap(List::stream)
                .forEach(xmlCipher::decrypt);
    }

    /**
     * @param password
     * @param decXmlDocument
     * @throws XmlSecureStoreException
     */
    public void encryptAllWithNewKey(char[] password, final Document decXmlDocument) {
        ElementEncryption ciphers = cipherWrapper.forEncryption(password, decXmlDocument);
        Element rootElement = decXmlDocument.getDocumentElement();

        List<Element> folderElements = XMLManager.searchChildren(rootElement, GROUP);

        folderElements.forEach(ciphers::encrypt);
//                .map(EncryptionManager::getPairs)
//                .flatMap(List::stream)

    }
}
