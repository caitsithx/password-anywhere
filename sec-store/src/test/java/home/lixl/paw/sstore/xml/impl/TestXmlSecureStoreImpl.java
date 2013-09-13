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

import home.lixl.paw.sstore.xml.JaxbHandler;
import home.lixl.paw.sstore.xml.StorageKeyKeeper;
import home.lixl.paw.sstore.xml.StoreSourceProvider;
import home.lixl.paw.sstore.xml.plain.SecureStore;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * 
 */
public class TestXmlSecureStoreImpl {

   @Test
   public void testLoad() throws Exception {
	XmlSecureStoreImpl l_secStore = new XmlSecureStoreImpl();
	
	l_secStore.init(new StorageKeyKeeper() {
	   private char[] password;
	   @Override
	   public void putKey(char[] p_password) {
		password = p_password;		
	   }
	   
	   @Override
	   public char[] getKey() {
		return password;
	   }
	}, 
	new StoreSourceProvider() {
	   private String fileLocation;
	   
	   @Override
	   public void newFileSource(String p_fileLocation) {
		fileLocation = p_fileLocation;
	   }
	   
	   @Override
	   public Result getOutput() {
		return new StreamResult(System.out);
	   }
	   
	   @Override
	   public InputSource getInput() {
		return new InputSource(
			TestXmlSecureStoreImpl.class.getResourceAsStream(fileLocation)
			);
	   }
	});
	
	DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	Document l_doc = dbuilder.parse(TestXmlSecureStoreImpl.class.getResourceAsStream("/plainstore.xml"));
	SecureStore l_store = JaxbHandler.loadJAXB(l_doc);
	System.out.println(l_store);
	
	l_secStore.createNew(l_store, "hello world".toCharArray(), "/plainstore.xml");
	
//	Document l_xmlNode = JaxbHandler.writeJAXB(l_store);
//	
//	DOMSource domSource = new DOMSource(l_xmlNode);
//	StreamResult streamResult = new StreamResult(System.out);
//	TransformerFactory tf = TransformerFactory.newInstance();
//	Transformer serializer = tf.newTransformer();
//	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
//	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
//	serializer.transform(domSource, streamResult); 
   }
   
}
