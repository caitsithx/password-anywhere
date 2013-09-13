// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.sstore.xml.impl;

import home.lixl.paw.sstore.SecStore;
import home.lixl.paw.sstore.xml.JaxbHandler;
import home.lixl.paw.sstore.xml.ReadWriteAccessAction;
import home.lixl.paw.sstore.xml.StorageKeyKeeper;
import home.lixl.paw.sstore.xml.StoreSourceProvider;
import home.lixl.paw.sstore.xml.XmlSecureStoreException;
import home.lixl.paw.sstore.xml.plain.SecureStore;
import home.lixl.paw.sstore.xml.sec.Decrypter;
import home.lixl.paw.sstore.xml.sec.Encrypter;
import home.lixl.paw.sstore.xml.util.XMLManager;

import javax.xml.bind.JAXBException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * 
 */
public class XmlSecureStoreImpl implements SecStore{

   private StorageKeyKeeper storeKeyKeeper = null;
   private StoreSourceProvider storeSourceProvider = null;

   /**
    * @param p_storeKeyKeeper
    * @param p_storeSourceProvider
    * 
    */
   public void init(StorageKeyKeeper p_storeKeyKeeper, StoreSourceProvider p_storeSourceProvider) {
	storeKeyKeeper = p_storeKeyKeeper;
	storeSourceProvider = p_storeSourceProvider;
   }

   /* (non-Javadoc)
    * @see home.lixl.paw.sstore.SecStore#createNew(home.lixl.paw.sstore.xml.plain.SecureStore, byte[], java.lang.String)
    */
   @Override
   public void createNew(SecureStore p_data) throws XmlSecureStoreException {
	Encrypter l_encrypter = new Encrypter();
	Document l_decXmlDocument = null;
	try {
	   l_decXmlDocument = JaxbHandler.writeJAXB(p_data);
	   l_encrypter.encryptAllWithNewKey(storeKeyKeeper.getKey(), l_decXmlDocument);
	} catch(JAXBException ex) {
	   throw new XmlSecureStoreException(ex);
	}

	XMLManager.write(l_decXmlDocument, storeSourceProvider.getOutput(), null);
   }

   @Override
   public Document open() throws XmlSecureStoreException {
	InputSource l_input = storeSourceProvider.getInput();
	return XMLManager.load(l_input, null);
   }

   public SecureStore list() throws XmlSecureStoreException {
	Document l_document = open();
	char[] l_key = storeKeyKeeper.getKey();

	Decrypter l_decrypter = new Decrypter();
	try {
	   l_decrypter.decryptAll(l_key, l_document);
	   return JaxbHandler.loadJAXB(l_document);
	} catch(JAXBException ex) {
	   throw new XmlSecureStoreException(ex);
	} catch (Exception ex) {
	   throw new XmlSecureStoreException(ex);
	}
   }

   public void UpdateFolder(ReadWriteAccessAction p_action) throws XmlSecureStoreException {
	Document l_document = open();
	char[] l_key = storeKeyKeeper.getKey();
	Decrypter l_decrypter = new Decrypter();
	Document l_decXmlDocument = l_decrypter.decrypt(l_key, l_document);

	//find the folder using xpath


	XMLManager.write(l_document, storeSourceProvider.getOutput(), null);
   }

   public void update() {

   }

   /* (non-Javadoc)
    * @see home.lixl.paw.sstore.SecureStore#close()
    */
   @Override
   public void close() {

   }
}
