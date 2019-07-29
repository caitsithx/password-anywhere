// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.store.xml;


import home.lixl.paw.store.domain.SecureDocument;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 *
 * 
 */
public class JaxbHandler {

   public final static SecureDocument loadJAXB(Document p_domDoc) throws JAXBException{
	   SecureDocument fl = null;

	JAXBContext jaxbContext = JAXBContext.newInstance(SecureDocument.class);
	DOMSource stremSource = new DOMSource(p_domDoc);
	// unmarshal
	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

//	try{
//	   SchemaFactory l_sf =
//		   SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//	   Schema l_schema = l_sf.newSchema(new StreamSource(
//		   JaxbHandler.class.getResourceAsStream("/sec-xml-store.xsd")));
//	   unmarshaller.setSchema(l_schema);
//	} catch (SAXException ex) {
//	   //TODO
//	   ex.printStackTrace();
//	}

	JAXBElement<SecureDocument> root = unmarshaller.unmarshal(stremSource,
			SecureDocument.class);
	// fl = (ColumnFamilies) unmarshaller.unmarshal(stremSource);

	fl = root.getValue();
	return fl;
   }

   public final static Document writeJAXB(SecureDocument p_secStore) throws JAXBException {
	JAXBContext jaxbContext = JAXBContext.newInstance(SecureDocument.class);
	
	Marshaller l_marshaller = jaxbContext.createMarshaller();

	   JAXBElement<SecureDocument> jaxbElement =
			   new JAXBElement<>(new QName("", "SecureDocument"),
					   SecureDocument.class,
					   p_secStore);
	
	DOMResult l_result = new DOMResult();
	l_marshaller.marshal(jaxbElement, l_result);
	return (Document)l_result.getNode();
   }
}
