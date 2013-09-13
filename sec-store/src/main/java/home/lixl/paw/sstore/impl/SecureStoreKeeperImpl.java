// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.sstore.impl;

import home.lixl.paw.sstore.SecStore;
import home.lixl.paw.sstore.SecureStoreKeeper;
import home.lixl.paw.sstore.xml.XmlSecureStoreException;
import home.lixl.paw.sstore.xml.plain.SecureStore;

/**
 *
 * 
 */
public class SecureStoreKeeperImpl implements SecureStoreKeeper {

   private SecStore secStorePersistManager;

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
   private SecStore createSecStorePersistence() {
	// TODO Auto-generated method stub
	return null;
   }
   
   public void createNew(SecureStore p_data, char[] password, String fileLocation) {
	secStorePersistManager.createNew(p_data, password, fileLocation);
   }

   /*
    * (non-Javadoc)
    * 
    * @see home.lixl.paw.sstore.SecureStoreKeeper#persist()
    */
   @Override
   public void persist() {
	// TODO Auto-generated method stub

   }

   /*
    * (non-Javadoc)
    * 
    * @see home.lixl.paw.sstore.SecureStoreKeeper#update()
    */
   @Override
   public void update() {
	// TODO Auto-generated method stub

   }

   /*
    * (non-Javadoc)
    * 
    * @see home.lixl.paw.sstore.SecureStoreKeeper#find()
    */
   @Override
   public void find() {
	// TODO Auto-generated method stub

   }

   /*
    * (non-Javadoc)
    * 
    * @see home.lixl.paw.sstore.SecureStoreKeeper#remove()
    */
   @Override
   public void remove() {
	// TODO Auto-generated method stub

   }

}
