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

import home.lixl.paw.sstore.xml.plain.SecureStore;
import home.lixl.paw.store.xml.JaxbHandler;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;

/**
 *
 * 
 */
public class TestJaxbHandler {

   @Test
   public void testLoad() throws Exception {
	digest(java.nio.charset.Charset.defaultCharset().encode("1234"));
	
	DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	Document l_doc = dbuilder.parse(TestJaxbHandler.class.getResourceAsStream("/plainstore.xml"));
	SecureStore l_store = JaxbHandler.loadJAXB(l_doc);
	System.out.println(l_store);
	
	Document l_xmlNode = JaxbHandler.writeJAXB(l_store);
	
	DOMSource domSource = new DOMSource(l_xmlNode);
	StreamResult streamResult = new StreamResult(System.out);
	TransformerFactory tf = TransformerFactory.newInstance();
	Transformer serializer = tf.newTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	serializer.transform(domSource, streamResult); 
   }
   
   private static byte[] digest(ByteBuffer p_key) throws NoSuchAlgorithmException, NoSuchAlgorithmException {
      MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
      digest.update(p_key);
      return digest.digest();
  }
}
