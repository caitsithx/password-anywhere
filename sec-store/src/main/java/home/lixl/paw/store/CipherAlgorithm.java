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

/**
 *
 * 
 */
public interface CipherAlgorithm {

   String getJCEName();
   String getXmlCipherURI();
   int getKeySize();
}
