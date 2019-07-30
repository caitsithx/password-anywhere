package home.lixl.paw.store.xml.crypto.impl;

import home.lixl.paw.store.xml.XmlSecureStoreException;
import home.lixl.paw.store.xml.crypto.ElementDecryption;
import org.apache.xml.security.encryption.XMLCipher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ElementDecryptionImpl implements ElementDecryption {
    private final XMLCipher xmlCipher;
    private final Document document;

    public ElementDecryptionImpl(XMLCipher xmlCipher, Document document) {
        this.xmlCipher = xmlCipher;
        this.document = document;
    }

    @Override public void decrypt(Element element) {
        try {
            xmlCipher.doFinal(document, element);
        } catch (Exception e) {
            throw new XmlSecureStoreException(e);
        }
    }
}
