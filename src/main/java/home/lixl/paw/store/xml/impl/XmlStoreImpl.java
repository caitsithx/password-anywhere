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
import home.lixl.paw.store.xml.crypto.EncryptionManager;
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
    private final EncryptionManager encryptionManager;

    /**
     * @param storeKeyKeeper
     * @param storeSourceProvider
     */
    public XmlStoreImpl(StorageKeyKeeper storeKeyKeeper, StoreSourceProvider storeSourceProvider) {
        this.storeKeyKeeper = storeKeyKeeper;
        this.storeSourceProvider = storeSourceProvider;

        encryptionManager = new EncryptionManager();
    }

    @Override public void createNew(SecureDocument data) throws XmlSecureStoreException {
        Document decXmlDocument = null;
        try {
            decXmlDocument = JaxbHandler.writeJAXB(data);
            encryptionManager.encryptAllWithNewKey(storeKeyKeeper.getKey(), decXmlDocument);
        } catch (JAXBException ex) {
            throw new XmlSecureStoreException(ex);
        }

        XMLManager.write(decXmlDocument, storeSourceProvider.getOutput(), null);
    }

    @Override public Document open() throws XmlSecureStoreException {
        InputSource input = storeSourceProvider.getInput();
        return XMLManager.load(input, null);
    }

    public SecureDocument list() throws XmlSecureStoreException {
        Document document = open();
        char[] key = storeKeyKeeper.getKey();

        try {
            encryptionManager.decryptAll(key, document);
            return JaxbHandler.loadJAXB(document);
        } catch (JAXBException ex) {
            throw new XmlSecureStoreException(ex);
        } catch (Exception ex) {
            throw new XmlSecureStoreException(ex);
        }
    }

    public void modify(SecureDocument changedStore) throws XmlSecureStoreException {
        Document document = open();
        char[] key = storeKeyKeeper.getKey();

        List<Group> groups = changedStore.getGroups();

        walkAndModify(groups, document, document.getDocumentElement());


        XMLManager.write(document, storeSourceProvider.getOutput(), null);
    }

    /**
     * @param groups
     * @param document
     * @param element
     * @throws XmlSecureStoreException
     */
    private void walkAndModify(List<Group> groups, Document document, Element element) throws XmlSecureStoreException {
        Element elem = null;
        Node childGroupElem = document.getDocumentElement();

        //	for (Group groupType : groups) {
        //	   elem = (Element)childGroupElem;
        //
        //	   Element groupElement = XMLManager.selectChildByAttr(element, "group", "id", groupType.getId());
        //
        //	   if(groupType.getGroup() == null || groupType.getGroup().size() == 0) {
        //
        //	   } else {
        //
        //	   }
        //	}
    }

    /**
     * @param groupIds
     * @param value
     * @param document
     * @throws XPathExpressionException
     */
    private void walkAndModify(String[] groupIds, ModifyGroupData value,
            Document document) throws XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath groupPath = xPathFactory.newXPath();

        Element elem = null;
        Node childGroupElem = document.getDocumentElement();
        for (int i = 0; i < groupIds.length; i++) {
            elem = (Element) childGroupElem;
            String xPathString = String.format("//child[@id='%1s']", groupIds[i]);
            XPathExpression xpathExp = groupPath.compile(xPathString);
            childGroupElem = (Node) xpathExp.evaluate(elem, XPathConstants.NODE);
        }

        elem.removeChild(childGroupElem);
    }

    public void update() {

    }

    @Override
    public void close() {

    }
}
