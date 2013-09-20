// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.store.xml.crypto;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Document;

/**
 *
 * 
 */
class DESedeAESCiphers implements XmlCiphers {

   private SecretKey GenerateDataEncryptionKey() throws NoSuchAlgorithmException {
	String jceAlgorithmName = "AES";
	KeyGenerator keyGenerator = KeyGenerator.getInstance(jceAlgorithmName);
	keyGenerator.init(128);
	return keyGenerator.generateKey();
   }

   /* (non-Javadoc)
    * @see home.lixl.paw.store.xml.impl.XmlCiphers#forEncryption(char[], org.w3c.dom.Document)
    */
   @Override
   public EncryptionCiphers forEncryption(char[] p_password, Document p_decXmlDocument) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, XMLEncryptionException {
	/*
	 * Get a key to be used for encrypting the element. Here we are generating
	 * an AES key.
	 */
	Key symmetricKey = GenerateDataEncryptionKey();

	/*
	 * Get a key to be used for encrypting the symmetric key. Here we are
	 * generating a DESede key.
	 */
	Key kek = getKey(p_password);

	String algorithmURI = XMLCipher.TRIPLEDES_KeyWrap;
	final XMLCipher keyCipher = XMLCipher.getInstance(algorithmURI);
	keyCipher.init(XMLCipher.WRAP_MODE, kek);
	final EncryptedKey encryptedKey = keyCipher.encryptKey(p_decXmlDocument,
		symmetricKey);
	/*
	 * Let us encrypt the contents of the document element.
	 */
	algorithmURI = XMLCipher.AES_128;
	final XMLCipher xmlCipher = XMLCipher.getInstance(algorithmURI);
	xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);

	return new EncryptionCiphers() {

	   @Override
	   public XMLCipher getEncryptionCipher() {
		return xmlCipher;
	   }

	   @Override
	   public EncryptedKey getEncryptedKey() {
		return encryptedKey;
	   }
	};
   }

   /* (non-Javadoc)
    * @see home.lixl.paw.store.xml.impl.XmlCiphers#forDecryption(char[])
    */
   @Override
   public XMLCipher forDecryption(char[] p_password) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, XMLEncryptionException {
	Key kek = getKey(p_password);

	final XMLCipher xmlCipher = XMLCipher.getInstance();
	/*
	 * The key to be used for decrypting xml data would be obtained
	 * from the keyinfo of the EncrypteData using the kek.
	 */
	xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
	xmlCipher.setKEK(kek);

	return xmlCipher;
   }

   protected SecretKey getKey(char[] p_password) throws InvalidKeyException,
   NoSuchAlgorithmException, InvalidKeySpecException {
	Charset utf8 = Charset.forName("UTF8");
	byte[] l_passBytes = utf8.encode(new String(p_password)).array();

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
}
