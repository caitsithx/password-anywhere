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
package home.lixl.paw.sstore.xml.sec;

import home.lixl.paw.sstore.xml.XmlSecureStoreException;
import home.lixl.paw.sstore.xml.util.XMLManager;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

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

   /** {@link org.apache.commons.logging} logging facility */
   static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
	   .getLog(Encrypter.class.getName());

   static {
	org.apache.xml.security.Init.init();
   }

   private SecretKey GenerateDataEncryptionKey() throws NoSuchAlgorithmException {
	String jceAlgorithmName = "AES";
	KeyGenerator keyGenerator = KeyGenerator.getInstance(jceAlgorithmName);
	keyGenerator.init(128);
	return keyGenerator.generateKey();
   }

   /**
    * @param p_key
    * @param p_decXmlDocument
    * 
    */
   public Document encrypt(byte[] p_key, Document p_decXmlDocument) {
	return null;
   }

   private SecretKey getKey(char[] p_key) throws InvalidKeyException,
	   NoSuchAlgorithmException, InvalidKeySpecException {
	Charset utf8 = Charset.forName("UTF8");
	byte[] l_passBytes = utf8.encode(new String(p_key)).array();

	byte[] l_keyBytes = new byte[24];

	if (l_passBytes.length > l_keyBytes.length) {
	   System.arraycopy(l_passBytes, 0, l_keyBytes, 0, l_keyBytes.length);
	} else {
	   System.arraycopy(l_passBytes, 0, l_keyBytes, 0, l_passBytes.length);
	   if (l_passBytes.length < l_keyBytes.length) {
		Arrays.fill(l_keyBytes, l_passBytes.length, l_keyBytes.length, (byte) 0x0F);		
	   }
	}

	DESedeKeySpec keySpec = new DESedeKeySpec(l_keyBytes);
	SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
	SecretKey key = skf.generateSecret(keySpec);

	return key;
   }

   /**
    * @param p_key
    * @param p_decXmlDocument
    * @return
    * @throws XmlSecureStoreException 
    * 
    */
   public void encryptAllWithNewKey(char[] p_key, final Document p_decXmlDocument) throws XmlSecureStoreException {
	try {
	   
	/*
	 * Get a key to be used for encrypting the element. Here we are generating
	 * an AES key.
	 */
	Key symmetricKey = GenerateDataEncryptionKey();

	/*
	 * Get a key to be used for encrypting the symmetric key. Here we are
	 * generating a DESede key.
	 */
	Key kek = getKey(p_key);

	EncryptedKey encryptedKey = encryptDataKey(kek, symmetricKey, p_decXmlDocument);
	

	/*
	 * Let us encrypt the contents of the document element.
	 */
	String algorithmURI = XMLCipher.AES_128;
	final XMLCipher xmlCipher = XMLCipher.getInstance(algorithmURI);
	xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);

	/*
	 * Setting keyinfo inside the encrypted data being prepared.
	 */
	Element rootElement = p_decXmlDocument.getDocumentElement();
	EncryptedData encryptedData = xmlCipher.getEncryptedData();
	KeyInfo keyInfo = new KeyInfo(p_decXmlDocument);
	keyInfo.add(encryptedKey);
	encryptedData.setKeyInfo(keyInfo);
	

	searchAndEncPairs(rootElement, new XMLManager.Action(){

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
	} catch (Exception l_ex) {
	   throw new XmlSecureStoreException(l_ex);
	}
   }

   /**
    * @param p_symmetricKey 
    * @param p_kek 
    * @param p_decXmlDocument 
    * @return
    * @throws XMLEncryptionException 
    * 
    */
   private EncryptedKey encryptDataKey(Key p_kek, Key p_symmetricKey, Document p_decXmlDocument) throws XMLEncryptionException {
	String algorithmURI = XMLCipher.TRIPLEDES_KeyWrap;
	XMLCipher keyCipher = XMLCipher.getInstance(algorithmURI);
	keyCipher.init(XMLCipher.WRAP_MODE, p_kek);
	return keyCipher.encryptKey(p_decXmlDocument,
		p_symmetricKey);
   }

   /**
    * @param p_decXmlDocument 
    * @param p_xmlCipher 
    * @param p_rootElement
    * @throws Exception 
    * 
    */
   private void searchAndEncPairs(Element p_rootElement, XMLManager.Action p_encAction) throws Exception {
	
	List<Element> l_folderElements = XMLManager.searchChildren(p_rootElement, "folder");
	
	for (Element l_folderElement : l_folderElements) {
	   int l_count = XMLManager.iterateChildren(l_folderElement, "pair", p_encAction);
	   
	   if(l_count == 0) {
		List<Element> l_subfolderElements = XMLManager.searchChildren(l_folderElement, "folder");
		
		for (Element l_subFolderElement : l_subfolderElements) {
		   searchAndEncPairs(l_subFolderElement, p_encAction);
		}
	   } 
	}
	
   }
}
