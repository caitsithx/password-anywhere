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

import home.lixl.paw.store.domain.Group;
import home.lixl.paw.store.domain.Pair;
import home.lixl.paw.store.domain.SecureDocument;
import home.lixl.paw.store.xml.JaxbHandler;
import org.junit.Test;
import org.w3c.dom.Document;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 */
public class TestJaxbHandler {

    @Test
    public void testLoad() throws Exception {
        digest(java.nio.charset.Charset.defaultCharset().encode("1234"));

        DocumentBuilder dbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document l_doc = dbuilder.parse(TestJaxbHandler.class.getResourceAsStream("/plainstore.xml"));
        SecureDocument l_store = JaxbHandler.loadJAXB(l_doc);
        System.out.println(l_store);

        Group group = new Group();
		Pair pair = new Pair();
		pair.setName("name");
		pair.setValue("value");
		group.setPairs(Collections.singletonList(pair));
		l_store.setGroups(Collections.singletonList(group));

		Group group1 = new Group();
		group1.setPairs(Collections.singletonList(pair));
		l_store.setGroups(Arrays.asList(group, group1));

        Document l_xmlNode = JaxbHandler.writeJAXB(l_store);

        DOMSource domSource = new DOMSource(l_xmlNode);
        StreamResult streamResult = new StreamResult(System.out);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.transform(domSource, streamResult);
    }

    private static byte[] digest(ByteBuffer p_key) throws NoSuchAlgorithmException, NoSuchAlgorithmException {
        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update(p_key);
        return digest.digest();
    }
}
