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

import javax.xml.bind.JAXBException;

import home.lixl.paw.sstore.xml.XmlSecureStoreException;
import home.lixl.paw.sstore.xml.plain.SecureStore;

/**
 *
 * 
 */
public interface SecStore {

   /**
    * @throws XmlSecureStoreException 
    * 
    * 
    */
   void open() throws XmlSecureStoreException;
   
   void close();

   /**
    * @param p_data
    * @param p_password
    * @param p_fileLocation
    * @throws JAXBException 
    * @throws XmlSecureStoreException 
    * 
    */
   void createNew(SecureStore p_data, char[] p_password, String p_fileLocation) throws XmlSecureStoreException;

}
