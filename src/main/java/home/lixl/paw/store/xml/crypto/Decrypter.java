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

import static home.lixl.paw.store.xml.crypto.Encrypter.GROUP;

import home.lixl.paw.store.xml.util.XMLManager;

import java.security.Key;
import java.util.List;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.EncryptionConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This sample demonstrates how to decrypt data inside an xml document.
 *
 * @author Vishal Mahajan (Sun Microsystems)
 */
public class Decrypter {

   /** {@link org.apache.commons.logging} logging facility */
   static org.apache.commons.logging.Log log = 
	   org.apache.commons.logging.LogFactory.getLog(
		   Decrypter.class.getName());

   static {
	org.apache.xml.security.Init.init();
   }

   XmlCiphers cipherWrapper = new DESedeAESCiphers();

   public void decryptAll(char[] p_password, final Document p_decXmlDocument) throws Exception {
	final XMLCipher xmlCipher = cipherWrapper.forDecryption(p_password);

//	 List<Element> l_folderElements = XMLManager.searchChildren(p_decXmlDocument.getDocumentElement(), GROUP);
//	   for (Element l_folderElement : l_folderElements) {
//		searchAndDec(l_folderElement, new XMLManager.Action(){
//
//		   @Override
//		   public void act(Element p_element) throws Exception {
//			/*
//			 * doFinal - "true" below indicates that we want to encrypt element's
//			 * content and not the element itself. Also, the doFinal method would
//			 * modify the document by replacing the EncrypteData element for the data
//			 * to be encrypted.
//			 */
//			xmlCipher.doFinal(p_decXmlDocument, p_element);
//		   }});
//	   }
	
	 List<Element> l_folderElements = XMLManager.searchChildren(p_decXmlDocument.getDocumentElement(), GROUP);
	   for (Element l_folderElement : l_folderElements) {
		searchAndDec(l_folderElement, new XMLManager.Action(){
		   
		   @Override
		   public void act(Element p_element) throws Exception {
			/*
			 * doFinal - "true" below indicates that we want to encrypt element's
			 * content and not the element itself. Also, the doFinal method would
			 * modify the document by replacing the EncrypteData element for the data
			 * to be encrypted.
			 */
			xmlCipher.doFinal(p_decXmlDocument, p_element);
		   }});
	   }
	
   }

   /**
    * @param p_folderElement
    * @throws Exception 
    * 
    */
   private void searchAndDec(Element p_folderElement, XMLManager.Action p_action) throws Exception {
	NodeList l_encElementsList = p_folderElement.getElementsByTagNameNS(
		EncryptionConstants.EncryptionSpecNS, 
		EncryptionConstants._TAG_ENCRYPTEDDATA);

	if(l_encElementsList.getLength() == 0) {
	   List<Element> l_folderElements = XMLManager.searchChildren(p_folderElement, GROUP);
	   for (Element l_folderElement : l_folderElements) {
		searchAndDec(l_folderElement, p_action);
	   }
	} else {
	   p_action.act((Element)l_encElementsList.item(0));
	}
   }

   /**
    * @param p_key
    * @param p_l_encXmlDocument
    * @return
    * 
    */
   public Document decrypt(char[] p_key, Document p_l_encXmlDocument) {

	return null;
   }

}
