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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

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
	   
	   @Override
	   public void putKey(char[] p_password) {
		//		
	   }
	   
	   @Override
	   public char[] getKey() {
		return "hello world".toCharArray();
	   }
	}, 
	new StoreSourceProvider() {
	   @Override
	   public void newFileSource(String p_fileLocation) {
		//
	   }
	   
	   @Override
	   public Result getOutput() {
		try {
		   return new StreamResult(new FileOutputStream("target/enc.xml"));
		} catch (FileNotFoundException ex) {
		   // TODO Auto-generated catch block
		   ex.printStackTrace();
		   return null;
		}
	   }
	   
	   @Override
	   public InputSource getInput() {
		return null;
	   }
	});
	
	DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	Document l_doc = dbuilder.parse(TestXmlSecureStoreImpl.class.getResourceAsStream("/plainstore.xml"));
	SecureStore l_store = JaxbHandler.loadJAXB(l_doc);
	System.out.println(l_store);
	
	l_secStore.createNew(l_store);
   }
   
   @Test
   public void testLoad1() throws Exception {
	XmlSecureStoreImpl l_secStore = new XmlSecureStoreImpl();
	
	l_secStore.init(new StorageKeyKeeper() {
	   
	   @Override
	   public void putKey(char[] p_password) {
		//		
	   }
	   
	   @Override
	   public char[] getKey() {
		return "hello world".toCharArray();
	   }
	}, 
	new StoreSourceProvider() {
	   @Override
	   public void newFileSource(String p_fileLocation) {
		//
	   }
	   
	   @Override
	   public Result getOutput() {
		return null;
	   }
	   
	   @Override
	   public InputSource getInput() {
		try {
		   return new InputSource(new FileInputStream("target/enc.xml"));
		} catch (FileNotFoundException ex) {
		   ex.printStackTrace();
		   return null;
		}
	   }
	});
	
	DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	Document l_doc = dbuilder.parse(TestXmlSecureStoreImpl.class.getResourceAsStream("/plainstore.xml"));
	SecureStore l_store = JaxbHandler.loadJAXB(l_doc);
	System.out.println(l_store);
	
	SecureStore l_sstore = l_secStore.list();
	System.out.println(l_sstore);	
   }
   
}
