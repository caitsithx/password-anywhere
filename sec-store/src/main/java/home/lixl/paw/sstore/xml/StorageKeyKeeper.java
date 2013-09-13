// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.sstore.xml;

/**
 *
 * 
 */
public interface StorageKeyKeeper {

   char[] getKey();
   /**
    * @param p_password
    * 
    */
   void putKey(char[] p_password);
}
