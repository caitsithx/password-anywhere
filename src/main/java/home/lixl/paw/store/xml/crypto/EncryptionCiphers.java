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

import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;

/**
 *
 * 
 */
public interface EncryptionCiphers {

   XMLCipher getEncryptionCipher();
   EncryptedKey getEncryptedKey();
   
}
