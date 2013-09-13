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

   private StorageKeyKeeper storeKeyKeeper;
   private StoreSourceProvider storeSourceProvider;
   
   private Document l_encXmlDocument;

   public void init(StorageKeyKeeper p_storeKeyKeeper, StoreSourceProvider p_storeSourceProvider) {
	storeKeyKeeper = p_storeKeyKeeper;
	storeSourceProvider = p_storeSourceProvider;
   }

   /* (non-Javadoc)
    * @see home.lixl.paw.sstore.SecStore#createNew(home.lixl.paw.sstore.xml.plain.SecureStore, byte[], java.lang.String)
    */
   @Override
   public void createNew(SecureStore p_data, char[] p_password,
	   String p_fileLocation) throws XmlSecureStoreException {
	Encrypter l_encrypter = new Encrypter();
	storeKeyKeeper.putKey(p_password);
	
	try {
	Document l_decXmlDocument = JaxbHandler.writeJAXB(p_data);
	l_encrypter.encryptAllWithNewKey(p_password, l_decXmlDocument);
	l_encXmlDocument = l_decXmlDocument;
	} catch(JAXBException ex) {
	   throw new XmlSecureStoreException(ex);
	}
	
	storeSourceProvider.newFileSource(p_fileLocation);
	XMLManager.write(l_encXmlDocument, storeSourceProvider.getOutput(), null);
   }
   
   @Override
   public void open() throws XmlSecureStoreException {
	InputSource l_input = storeSourceProvider.getInput();
	
	l_encXmlDocument = XMLManager.load(l_input, null);
   }
   
   public SecureStore list() throws JAXBException {
	char[] l_key = storeKeyKeeper.getKey();
	
	Decrypter l_decrypter = new Decrypter();
	Document l_decXmlDocument = l_decrypter.decryptAll(l_key, l_encXmlDocument);
	
	return JaxbHandler.loadJAXB(l_decXmlDocument);
   }
   
   public void write(SecureStore p_secStore) throws JAXBException, XmlSecureStoreException {
	char[] l_key = storeKeyKeeper.getKey();
	
	Encrypter l_decrypter = new Encrypter();
	Document l_decXmlDocument = JaxbHandler.writeJAXB(p_secStore);
	
	l_encXmlDocument = l_decrypter.encrypt(l_key, l_decXmlDocument);
	
	XMLManager.write(l_encXmlDocument, storeSourceProvider.getOutput(), null);
   }
   
   public void UpdateFolder(ReadWriteAccessAction p_action) throws XmlSecureStoreException {
	char[] l_key = storeKeyKeeper.getKey();
	Decrypter l_decrypter = new Decrypter();
	Document l_decXmlDocument = l_decrypter.decrypt(l_key, l_encXmlDocument);
	
	//find the folder using xpath
	
	l_encXmlDocument = null;
	
	XMLManager.write(l_encXmlDocument, storeSourceProvider.getOutput(), null);
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
