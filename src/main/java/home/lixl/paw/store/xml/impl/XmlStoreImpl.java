// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.store.xml.impl;

import home.lixl.paw.store.ModifyGroupData;
import home.lixl.paw.store.Store;
import home.lixl.paw.store.domain.Group;
import home.lixl.paw.store.domain.SecureDocument;
import home.lixl.paw.store.xml.JaxbHandler;
import home.lixl.paw.store.xml.StorageKeyKeeper;
import home.lixl.paw.store.xml.StoreSourceProvider;
import home.lixl.paw.store.xml.XmlSecureStoreException;
import home.lixl.paw.store.xml.crypto.Decrypter;
import home.lixl.paw.store.xml.crypto.Encrypter;
import home.lixl.paw.store.xml.util.XMLManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.util.List;
import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 *
 */
public class XmlStoreImpl implements Store {

    private final StorageKeyKeeper storeKeyKeeper;
    private final StoreSourceProvider storeSourceProvider;

    /**
     * @param p_storeKeyKeeper
     * @param p_storeSourceProvider
     */
    public XmlStoreImpl(StorageKeyKeeper p_storeKeyKeeper, StoreSourceProvider p_storeSourceProvider) {
        this.storeKeyKeeper = p_storeKeyKeeper;
		this.storeSourceProvider = p_storeSourceProvider;
    }

    @Override public void createNew(SecureDocument p_data) throws XmlSecureStoreException {
        Encrypter l_encrypter = new Encrypter();
        Document l_decXmlDocument = null;
        try {
            l_decXmlDocument = JaxbHandler.writeJAXB(p_data);
            l_encrypter.encryptAllWithNewKey(storeKeyKeeper.getKey(), l_decXmlDocument);
        } catch (JAXBException ex) {
            throw new XmlSecureStoreException(ex);
        }

        XMLManager.write(l_decXmlDocument, storeSourceProvider.getOutput(), null);
    }

    @Override public Document open() throws XmlSecureStoreException {
        InputSource l_input = storeSourceProvider.getInput();
        return XMLManager.load(l_input, null);
    }

    public SecureDocument list() throws XmlSecureStoreException {
        Document l_document = open();
        char[] l_key = storeKeyKeeper.getKey();

        Decrypter l_decrypter = new Decrypter();
        try {
            l_decrypter.decryptAll(l_key, l_document);
            return JaxbHandler.loadJAXB(l_document);
        } catch (JAXBException ex) {
            throw new XmlSecureStoreException(ex);
        } catch (Exception ex) {
            throw new XmlSecureStoreException(ex);
        }
    }

    public void modify(SecureDocument p_changedStore) throws XmlSecureStoreException {
        Document l_document = open();
        char[] l_key = storeKeyKeeper.getKey();
        Decrypter l_decrypter = new Decrypter();
        //	Document l_decXmlDocument = l_decrypter.decrypt(l_key, l_document);

        List<Group> l_groups = p_changedStore.getGroups();

        walkAndModify(l_groups, l_document, l_document.getDocumentElement());


        XMLManager.write(l_document, storeSourceProvider.getOutput(), null);
    }

    /**
     * @param p_groups
     * @param p_document
     * @param p_element
     * @throws XmlSecureStoreException
     */
    private void walkAndModify(List<Group> p_groups, Document p_document, Element p_element) throws XmlSecureStoreException {
        Element l_elem = null;
        Node l_childGroupElem = p_document.getDocumentElement();

        //	for (Group l_groupType : p_groups) {
        //	   l_elem = (Element)l_childGroupElem;
        //
        //	   Element l_groupElement = XMLManager.selectChildByAttr(p_element, "group", "id", l_groupType.getId());
        //
        //	   if(l_groupType.getGroup() == null || l_groupType.getGroup().size() == 0) {
        //
        //	   } else {
        //
        //	   }
        //	}
    }

    /**
     * @param p_groupIds
     * @param p_value
     * @param p_document
     * @throws XPathExpressionException
     */
    private void walkAndModify(String[] p_groupIds, ModifyGroupData p_value,
            Document p_document) throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath l_groupPath = xPathFactory.newXPath();

        Element l_elem = null;
        Node l_childGroupElem = p_document.getDocumentElement();
        for (int i = 0; i < p_groupIds.length; i++) {
            l_elem = (Element) l_childGroupElem;
            String l_xPathString = String.format("//child[@id='%1s']", p_groupIds[i]);
            XPathExpression l_xpathExp = l_groupPath.compile(l_xPathString);
            l_childGroupElem = (Node) l_xpathExp.evaluate(l_elem, XPathConstants.NODE);
        }

        l_elem.removeChild(l_childGroupElem);
    }

    public void update() {

    }

    @Override
    public void close() {

    }
}
