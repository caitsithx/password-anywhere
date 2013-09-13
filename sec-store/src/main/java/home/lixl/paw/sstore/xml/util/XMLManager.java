/**
 * 
 */
package home.lixl.paw.sstore.xml.util;

import home.lixl.paw.sstore.xml.XmlSecureStoreException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Xiaoliang li(Stephen)
 *
 */
public final class XMLManager {

   /**
    * @param p_element parent element
    * @param p_name element name
    * @return if specific named element
    */
   public static Element searchChild(Element p_element, String p_name){
	Element l_appElement = null;
	NodeList l_childList = p_element.getChildNodes();
	for(int i = 0; i < l_childList.getLength(); i ++){
	   Node l_child = l_childList.item(i);
	   if(l_child.getNodeType() == Node.ELEMENT_NODE){
		if(l_child.getNodeName().equals(p_name)){
		   l_appElement = ((Element)l_child);
		   break;
		}
	   }
	}

	return l_appElement;
   }

   /**
    * @param p_element parent element
    * @param p_name element name
    * @return a list of found element
    */
   public static List<Element> searchChildren(Element p_element, String p_name){
	List<Element> l_elemList = new ArrayList<Element>();

	NodeList l_childList = p_element.getChildNodes();
	for(int i = 0; i < l_childList.getLength(); i ++){
	   Node l_child = l_childList.item(i);
	   if(l_child.getNodeType() == Node.ELEMENT_NODE){
		if(l_child.getNodeName().equals(p_name)){
		   l_elemList.add(((Element)l_child));
		}
	   }
	}

	return l_elemList; 
   }
   
   public static interface Action {
	public void act(Element p_element) throws Exception;
   }
   
   public static int iterateChildren(Element p_element, String p_name, Action p_elementAction) throws Exception{
	int l_count = 0;
	
	NodeList l_childList = p_element.getChildNodes();
	for(int i = 0; i < l_childList.getLength(); i ++){
	   Node l_child = l_childList.item(i);
	   if(l_child.getNodeType() == Node.ELEMENT_NODE){
		if(l_child.getNodeName().equals(p_name)){
		   l_count++;
		   p_elementAction.act((Element)l_child);
		}
	   }
	}

	return l_count; 
   }

   /**
    * @param p_element parent element
    * @param p_name element name
    * @return count of found element
    */
   public static int countChildren(Element p_element, String p_name){
	int l_count = 0;
	NodeList l_childList = p_element.getChildNodes();
	for(int i = 0; i < l_childList.getLength(); i ++){
	   Node l_child = l_childList.item(i);
	   if(l_child.getNodeType() == Node.ELEMENT_NODE){
		if(l_child.getNodeName().equals(p_name)){
		   l_count ++;
		}
	   }
	}

	return l_count;
   }

   /**
    * @param p_element element
    * @return element embraced text
    */
   public static String getText(Element p_element){
	NodeList l_childList = p_element.getChildNodes();
	for(int i = 0; i < l_childList.getLength(); i ++){
	   Node l_child = l_childList.item(i);
	   if(l_child.getNodeType() == Node.TEXT_NODE){
		return ((Text)l_child).getNodeValue();
	   }
	}

	return null;
   }

   public static String getCDATA(Element p_element){
	NodeList l_childList = p_element.getChildNodes();
	for(int i = 0; i < l_childList.getLength(); i ++){
	   Node l_child = l_childList.item(i);
	   if(l_child.getNodeType() == Node.CDATA_SECTION_NODE){
		return ((Text)l_child).getNodeValue();
	   }
	}

	return null;
   }

   /**
    * @param p_XMLFilePath XML FilePath
    * @param p_SAXHandler SAX event handler.
    * @throws XmlSecureStoreException any exception.
    */
   public static void parse(String p_XMLFilePath, DefaultHandler p_SAXHandler) throws XmlSecureStoreException{
	SAXParser saxparser = null;

	try {
	   SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	   saxparser = parserFactory.newSAXParser();
	   saxparser.parse(new File(p_XMLFilePath), p_SAXHandler);
	} catch (ParserConfigurationException ex) {
	   throw new XmlSecureStoreException(ex); 
	} catch (SAXException ex) {
	   throw new XmlSecureStoreException(ex); 
	} catch (IOException ex) {
	   throw new XmlSecureStoreException(ex); 
	}
   }

   public static void parse(InputStream p_XMLIS, DefaultHandler p_SAXHandler) throws XmlSecureStoreException{
	SAXParser saxparser = null;

	try {
	   SAXParserFactory parserFactory = SAXParserFactory.newInstance();
	   saxparser = parserFactory.newSAXParser();
	   saxparser.parse(p_XMLIS, p_SAXHandler);
	} catch (ParserConfigurationException ex) {
	   throw new XmlSecureStoreException(ex); 
	} catch (SAXException ex) {
	   throw new XmlSecureStoreException(ex); 
	} catch (IOException ex) {
	   throw new XmlSecureStoreException(ex); 
	}
   }

   /**
    * @param p_XMLFile XML file path
    * @param p_XSDFileName the XML schema file name
    * @return the DOM Document of the XML file
    * @throws XmlSecureStoreException any error happens
    */
   public static Document load(String p_XMLFile, String p_XSDFileName) throws XmlSecureStoreException{
	FileInputStream xmlIS = null;
	try {
	   xmlIS = new FileInputStream(p_XMLFile);
	   return load(new InputSource(xmlIS), p_XSDFileName);
	} catch (IOException e) {
	   throw new XmlSecureStoreException(e); 
	}
   }

   /**
    * @param p_XMLFile
    * @param p_XSDFileName
    * @return
    * @throws XmlSecureStoreException
    * 
    */
   public static Document load(InputSource p_XMLFile, String p_XSDFileName) throws XmlSecureStoreException{
	FileInputStream xmlIS = null;
	try {
	   DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	   builderFactory.setNamespaceAware(true);

	   DocumentBuilder builder = builderFactory.newDocumentBuilder();
	   Document vDoc = builder.parse(p_XMLFile);

	   //	       SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	   //	       Schema vPIFSchema = schemaFactory.newSchema(XMLManager.class.getResource(paramXSDFileName));
	   //	       Validator vValidator = vPIFSchema.newValidator();
	   //	       MyErrHandler vErrHandler = new MyErrHandler();
	   //	       vValidator.setErrorHandler(vErrHandler);
	   //
	   //	       vValidator.validate(new DOMSource(vDoc));

	   //	       if(vErrHandler.errorExists()){
	   //	        throw new AppletCtrlException(vErrHandler.getErrorMessage()); 
	   //	       }

	   return vDoc;
	} catch (SAXException e) {
	   throw new XmlSecureStoreException(e); 
	} 
	catch (ParserConfigurationException e) {
	   throw new XmlSecureStoreException(e); 
	} catch (IOException e) {
	   throw new XmlSecureStoreException(e); 
	}finally{
	   if(xmlIS != null){
		try {
		   xmlIS.close();
		} catch (IOException ex) {
		   //do nothing
		}
	   }
	}
   }

   /**
    * @param p_document
    * @param p_destination
    * @param p_XSDFileName 
    * @throws AppletCtrlException 
    */
   public static void write(Document p_document,  String p_destination, String p_XSDFileName)throws XmlSecureStoreException{
	FileWriter xmlFW = null;


	//Write the DOM document to the file
	try {
	   xmlFW = new FileWriter(p_destination);
	   //Prepare the output file
	   Result result = new StreamResult(xmlFW);

	   write(p_document, result, p_XSDFileName);
	} catch (IOException ex) {
	   throw new XmlSecureStoreException(ex); 
	} finally{
	   if (xmlFW != null) {
		try {
		   xmlFW.close();
		} catch (IOException ex) {
		   // do nothing
		}
	   }
	}

   }
   
   public static void write(Document p_document,  Result p_destination, String p_XSDFileName)throws XmlSecureStoreException{
	Source source = new DOMSource(p_document);
	FileWriter xmlFW = null;


	//Write the DOM document to the file
	try {
	   //       SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	   //       Schema vPIFSchema = schemaFactory.newSchema(XMLManager.class.getResource(paramXSDFileName));
	   //       Validator vValidator = vPIFSchema.newValidator();
	   //       MyErrHandler vErrHandler = new MyErrHandler();
	   //       vValidator.setErrorHandler(vErrHandler);

	   //vValidator.validate(new DOMSource(paramDocument));

	   //       if(vErrHandler.errorExists()){
	   //        throw new XmlSecureStoreException(vErrHandler.getErrorMessage()); 
	   //       }

	   Transformer xformer = TransformerFactory.newInstance().newTransformer();
	   xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //$NON-NLS-1$ //$NON-NLS-2$
	   xformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
	   xformer.transform(source, p_destination);
	} catch (TransformerConfigurationException ex) {
	   throw new XmlSecureStoreException(ex); 
	} catch (TransformerFactoryConfigurationError ex) {
	   throw new XmlSecureStoreException(ex); 
	} catch (TransformerException ex) {
	   throw new XmlSecureStoreException(ex); 
	} finally{
	   if (xmlFW != null) {
		try {
		   xmlFW.close();
		} catch (IOException ex) {
		   // do nothing
		}
	   }
	}

   }

   /**
    * @param p_doc DOM Document
    * @param p_elemName element name
    * @param p_value element value
    * @return the element
    */
   public static final Element createTextElement(Document p_doc, String p_elemName, String p_value) {
	Element l_txtElem = p_doc.createElement(p_elemName);

	l_txtElem.appendChild(p_doc.createTextNode(p_value));

	return l_txtElem;
   }

   /**
    * @param p_element parent element
    * @param p_elemName child text element name
    * @return element value
    */
   public static String searchText(Element p_element, String p_elemName) {

	Element l_elem = XMLManager.searchChild(p_element, p_elemName);

	if(l_elem != null){
	   return getText(l_elem);
	}

	return null;
   }
}

class MyErrHandler implements ErrorHandler{
   private String errMessage = null;
   public void error(SAXParseException paramArg0) throws SAXException {
	errMessage = paramArg0.getLocalizedMessage();
   }

   public void fatalError(SAXParseException paramArg0) throws SAXException {
	errMessage = paramArg0.getLocalizedMessage();
   }

   public void warning(SAXParseException paramArg0) throws SAXException {
	//do nothing
   }

   /**
    * @return if any error happens, when validating this XML
    */
   public boolean errorExists(){
	return (errMessage != null);
   }

   /**
    * @return the error message if any error happens
    */
   public String getErrorMessage(){
	return errMessage;
   }
}
