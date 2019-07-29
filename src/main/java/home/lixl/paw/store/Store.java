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

import javax.xml.bind.JAXBException;

import home.lixl.paw.store.domain.SecureDocument;
import org.w3c.dom.Document;

import home.lixl.paw.store.xml.XmlSecureStoreException;

/**
 *
 * 
 */
public interface Store {

   /**
    * @return 
    * @throws XmlSecureStoreException 
    * 
    * 
    */
   Document open() throws XmlSecureStoreException;
   
   void close();

   /**
    * @param p_data
    * @throws JAXBException
    * @throws XmlSecureStoreException 
    * 
    */
   void createNew(SecureDocument p_data) throws XmlSecureStoreException;

}
