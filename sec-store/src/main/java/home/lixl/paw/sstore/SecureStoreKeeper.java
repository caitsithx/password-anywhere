// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.sstore;

/**
 *
 * 
 */
public interface SecureStoreKeeper {

   public void persist();
   public void update();
   public void find();
   public void remove();
   
}
