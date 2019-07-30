package home.lixl.paw.store.xml.crypto.impl;

import home.lixl.paw.store.xml.XmlSecureStoreException;
import home.lixl.paw.store.xml.crypto.ElementEncryption;
import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.keys.KeyInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ElementEncryptionImpl implements ElementEncryption {
    private final XMLCipher xmlCipher;
    private final Document document;

    public ElementEncryptionImpl(XMLCipher xmlCipher, EncryptedKey key, Document document) {
        this.xmlCipher = xmlCipher;
        this.document = document;

        EncryptedData encryptedData = xmlCipher.getEncryptedData();
        KeyInfo keyInfo = new KeyInfo(document);
        try {
            keyInfo.add(key);
        } catch (XMLEncryptionException e) {
            throw new XmlSecureStoreException(e);
        }
        encryptedData.setKeyInfo(keyInfo);
    }

    @Override public void encrypt(Element element) {
        try {
            xmlCipher.doFinal(document, element, true);
        } catch (Exception e) {
            throw new XmlSecureStoreException(e);
        }
    }
}
