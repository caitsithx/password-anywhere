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

import home.lixl.paw.store.domain.SecureDocument;
import home.lixl.paw.store.xml.JaxbHandler;
import home.lixl.paw.store.xml.StorageKeyKeeper;
import home.lixl.paw.store.xml.StoreSourceProvider;
import home.lixl.paw.store.xml.impl.XmlStoreImpl;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 *
 */
public class TestXmlSecureStoreImpl {

    @Test
    public void testLoad() throws Exception {
        XmlStoreImpl l_secStore = new XmlStoreImpl(new StorageKeyKeeper() {

            public void putKey(char[] p_password) {
                //
            }

            public char[] getKey() {
                return "hello world".toCharArray();
            }
        },
                new StoreSourceProvider() {
                    public void newFileSource(String p_fileLocation) {
                        //
                    }

                    public Result getOutput() {
                        try {
                            return new StreamResult(new FileOutputStream("target/enc.xml"));
                        } catch (FileNotFoundException ex) {
                            // TODO Auto-generated catch block
                            ex.printStackTrace();
                            return null;
                        }
                    }

                    public InputSource getInput() {
                        return null;
                    }
                });

        DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document l_doc = dbuilder.parse(TestXmlSecureStoreImpl.class.getResourceAsStream("/plainstore.xml"));
        SecureDocument l_store = JaxbHandler.loadJAXB(l_doc);
        System.out.println(l_store);

        l_secStore.createNew(l_store);
    }

    @Test
    public void testLoad1() throws Exception {
        XmlStoreImpl l_secStore = new XmlStoreImpl(new StorageKeyKeeper() {

            public void putKey(char[] p_password) {
                //
            }

            public char[] getKey() {
                return "hello world".toCharArray();
            }
        },
                new StoreSourceProvider() {
                    public void newFileSource(String p_fileLocation) {
                        //
                    }

                    public Result getOutput() {
                        return null;
                    }

                    public InputSource getInput() {
                        return new InputSource(TestXmlSecureStoreImpl.class.getResourceAsStream("/enc.xml"));
                    }
                });

        //	DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        //	Document l_doc = dbuilder.parse();
        //	SecureStore l_store = JaxbHandler.loadJAXB(l_doc);
        //	System.out.println(l_store);

        SecureDocument l_sstore = l_secStore.list();
        System.out.println(l_sstore);
    }

}
