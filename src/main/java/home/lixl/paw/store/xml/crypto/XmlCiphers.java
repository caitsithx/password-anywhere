// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.store.xml.crypto;

import org.w3c.dom.Document;

/**
 *
 * 
 */
public interface XmlCiphers {

    ElementEncryption forEncryption(char[] password,
            Document decXmlDocument);

    ElementDecryption forDecryption(char[] password, Document decXmlDocument);

}
