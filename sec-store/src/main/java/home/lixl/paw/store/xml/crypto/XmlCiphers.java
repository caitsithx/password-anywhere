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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Document;

/**
 *
 * 
 */
public interface XmlCiphers {

   public abstract EncryptionCiphers forEncryption(char[] p_password,
	   Document p_decXmlDocument) throws NoSuchAlgorithmException,
	   InvalidKeyException, InvalidKeySpecException, XMLEncryptionException;

   public abstract XMLCipher forDecryption(char[] p_password)
	   throws InvalidKeyException, NoSuchAlgorithmException,
	   InvalidKeySpecException, XMLEncryptionException;

}