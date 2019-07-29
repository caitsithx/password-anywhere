// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.store.impl;

import home.lixl.paw.store.Store;
import home.lixl.paw.store.SecureStoreKeeper;
import home.lixl.paw.store.domain.SecureDocument;
import home.lixl.paw.store.xml.XmlSecureStoreException;

/**
 *
 * 
 */
public class SecureStoreKeeperImpl implements SecureStoreKeeper {

   private Store secStorePersistManager;

   protected void open() {
	if (secStorePersistManager == null) {
	   secStorePersistManager = createSecStorePersistence();

	   try {
		secStorePersistManager.open();
	   } catch (XmlSecureStoreException ex) {
		// TODO Auto-generated catch block
		ex.printStackTrace();
	   }
	}
   }

   /**
    * @return
    * 
    */
   private Store createSecStorePersistence() {
	// TODO Auto-generated method stub
	return null;
   }
   
   public void createNew(SecureDocument p_data, char[] password, String fileLocation) throws XmlSecureStoreException {
	secStorePersistManager.createNew(p_data);
   }


   public void persist() {
	// TODO Auto-generated method stub

   }


   public void update() {
	// TODO Auto-generated method stub

   }

   public void find() {
	// TODO Auto-generated method stub

   }

   public void remove() {
	// TODO Auto-generated method stub

   }

}
