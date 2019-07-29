/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
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

import java.security.Key;
import java.util.List;

import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.keys.KeyInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This sample demonstrates how to encrypt data inside an xml document.
 * 
 * @author Vishal Mahajan (Sun Microsystems)
 */
public class Encrypter {

	public static final String GROUP = "Group";
	public static final String PAIR = "Pair";
	/** {@link org.apache.commons.logging} logging facility */
   static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
	   .getLog(Encrypter.class.getName());

   static {
	org.apache.xml.security.Init.init();
   }



   /**
    * @param p_key
    * @param p_decXmlDocument
    * 
    */
   public Document encrypt(char[] p_key, Document p_decXmlDocument) {
	return null;
   }

   XmlCiphers cipherWrapper = new DESedeAESCiphers();


   /**
    * @param p_password
    * @param p_decXmlDocument
    * @throws XmlSecureStoreException 
    * 
    */
   public void encryptAllWithNewKey(char[] p_password, final Document p_decXmlDocument) throws XmlSecureStoreException {
	try {

	   EncryptionCiphers ciphers = cipherWrapper.forEncryption(p_password, p_decXmlDocument);

	   EncryptedKey encryptedKey = ciphers.getEncryptedKey();
	   final XMLCipher xmlCipher = ciphers.getEncryptionCipher();

	   /*
	    * Setting keyinfo inside the encrypted data being prepared.
	    */
	   Element rootElement = p_decXmlDocument.getDocumentElement();
	   EncryptedData encryptedData = xmlCipher.getEncryptedData();
	   KeyInfo keyInfo = new KeyInfo(p_decXmlDocument);
	   keyInfo.add(encryptedKey);
	   encryptedData.setKeyInfo(keyInfo);



	   List<Element> l_folderElements = XMLManager.searchChildren(rootElement, GROUP);
	   for (Element l_folderElement : l_folderElements) {
		searchAndEncPairs(l_folderElement, new XMLManager.Action(){

		   @Override
		   public void act(Element p_element) throws Exception {
			/*
			 * doFinal - "true" below indicates that we want to encrypt element's
			 * content and not the element itself. Also, the doFinal method would
			 * modify the document by replacing the EncrypteData element for the data
			 * to be encrypted.
			 */
			xmlCipher.doFinal(p_decXmlDocument, p_element, true);
		   }});
	   }
	} catch (Exception l_ex) {
	   throw new XmlSecureStoreException(l_ex);
	}
   }

   /**
    * @param p_decXmlDocument 
    * @param p_xmlCipher 
    * @param p_folderElement
    * @throws Exception 
    * 
    */
   private void searchAndEncPairs(Element p_folderElement, XMLManager.Action p_encAction) throws Exception {
	int l_pairCount = XMLManager.countChildren(p_folderElement, PAIR);

	if(l_pairCount > 0) {
	   p_encAction.act(p_folderElement);
	} else {
	   List<Element> l_folderElements = XMLManager.searchChildren(p_folderElement, GROUP);

	   for (Element l_folderElement : l_folderElements) {
		searchAndEncPairs(l_folderElement, p_encAction);
	   }
	}
   }
}
