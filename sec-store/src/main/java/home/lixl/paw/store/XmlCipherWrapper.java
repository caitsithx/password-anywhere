// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.store;

import java.security.Key;

import org.apache.xml.security.encryption.XMLCipher;

/**
 *
 * 
 */
public interface XmlCipherWrapper {

   CipherAlgorithm getKeyAlgorithm();
   CipherAlgorithm getDataAlgorithm();
   
   Key transformtoKeyKey(char[] p_password);
   
   XMLCipher getXmlCipher();
   
}
