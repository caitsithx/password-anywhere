//// -------------------------------------------------------------------------
////
//// Project name: secstore
////
//// Platform : Java virtual machine
//// Language : JAVA 6.0
////
//// Original author: lixl
//// -------------------------------------------------------------------------
//package home.lixl.paw.sstore.xml.impl;
//
//import home.lixl.paw.sstore.CipherAlgorithm;
//import home.lixl.paw.sstore.XmlCipherWrapper;
//
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//import org.apache.xml.security.encryption.EncryptedData;
//import org.apache.xml.security.encryption.EncryptedKey;
//import org.apache.xml.security.encryption.XMLCipher;
//import org.apache.xml.security.keys.KeyInfo;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
///**
// *
// * 
// */
//public class DesAesXmlCipherWrapper implements XmlCipherWrapper{
//
//   /* (non-Javadoc)
//    * @see home.lixl.paw.sstore.XmlCipherWrapper#getKeyAlgorithm()
//    */
//   @Override
//   public CipherAlgorithm getKeyAlgorithm() {
//	// TODO Auto-generated method stub
//	return null;
//   }
//
//   /* (non-Javadoc)
//    * @see home.lixl.paw.sstore.XmlCipherWrapper#getXmlAlgorithm()
//    */
//   @Override
//   public CipherAlgorithm getDataAlgorithm() {
//	// TODO Auto-generated method stub
//	return null;
//   }
//
//   /* (non-Javadoc)
//    * @see home.lixl.paw.sstore.XmlCipherWrapper#transformEncryptKey(char[])
//    */
//   @Override
//   public Key transformtoKeyKey(char[] p_password) {
//	// TODO Auto-generated method stub
//	return null;
//   }
//
//   public SecretKey generateDataKey() throws NoSuchAlgorithmException {
//	KeyGenerator keyGenerator = KeyGenerator.getInstance(getDataAlgorithm().getJCEName());
//	keyGenerator.init(getDataAlgorithm().getKeySize());
//	return keyGenerator.generateKey();
//   }
//
//   public void encrypt(Document p_decXmlDocument) throws NoSuchAlgorithmException {
//	SecretKey symmetricKey = generateDataKey();
//	
//	EncryptedKey encryptedKey = getXmlCipher().encryptKey(p_decXmlDocument, symmetricKey);
//
//
//	/*
//	 * Let us encrypt the contents of the document element.
//	 */
//	String algorithmURI = XMLCipher.AES_128;
//	final XMLCipher xmlCipher = XMLCipher.getInstance(algorithmURI);
//	xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);
//
//	/*
//	 * Setting keyinfo inside the encrypted data being prepared.
//	 */
//	Element rootElement = p_decXmlDocument.getDocumentElement();
//	EncryptedData encryptedData = xmlCipher.getEncryptedData();
//	KeyInfo keyInfo = new KeyInfo(p_decXmlDocument);
//	keyInfo.add(encryptedKey);
//	encryptedData.setKeyInfo(keyInfo);
//   }
//
//   /* (non-Javadoc)
//    * @see home.lixl.paw.sstore.XmlCipherWrapper#getDataEncryptionCipher()
//    */
//   @Override
//   public XMLCipher getXmlCipher() {
//	// TODO Auto-generated method stub
//	return null;
//   }
//
//}
