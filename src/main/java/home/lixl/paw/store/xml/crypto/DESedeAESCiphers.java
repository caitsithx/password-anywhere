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

import home.lixl.paw.store.xml.XmlSecureStoreException;
import home.lixl.paw.store.xml.crypto.impl.ElementDecryptionImpl;
import home.lixl.paw.store.xml.crypto.impl.ElementEncryptionImpl;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Document;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 *
 */
class DESedeAESCiphers implements XmlCiphers {

    private SecretKey GenerateDataEncryptionKey() throws NoSuchAlgorithmException {
        String jceAlgorithmName = "AES";
        KeyGenerator keyGenerator = KeyGenerator.getInstance(jceAlgorithmName);
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    /* (non-Javadoc)
     * @see home.lixl.paw.store.xml.impl.XmlCiphers#forEncryption(char[], org.w3c.dom.Document)
     */
    @Override
    public ElementEncryption forEncryption(char[] password, Document decXmlDocument) {

        try {
            /*
             * Get a key to be used for encrypting the element. Here we are generating
             * an AES key.
             */
            Key symmetricKey = GenerateDataEncryptionKey();

            /*
             * Get a key to be used for encrypting the symmetric key. Here we are
             * generating a DESede key.
             */
            Key kek = getKey(password);

            String algorithmUri = XMLCipher.TRIPLEDES_KeyWrap;
            final XMLCipher keyCipher = XMLCipher.getInstance(algorithmUri);
            keyCipher.init(XMLCipher.WRAP_MODE, kek);
            final EncryptedKey encryptedKey = keyCipher.encryptKey(decXmlDocument, symmetricKey);
            /*
             * Let us encrypt the contents of the document element.
             */
            algorithmUri = XMLCipher.AES_128;
            final XMLCipher xmlCipher = XMLCipher.getInstance(algorithmUri);
            xmlCipher.init(XMLCipher.ENCRYPT_MODE, symmetricKey);

            return new ElementEncryptionImpl(xmlCipher, encryptedKey, decXmlDocument);
        } catch (NoSuchAlgorithmException | XMLEncryptionException | InvalidKeyException | InvalidKeySpecException bsae) {
            throw new XmlSecureStoreException(bsae);
        }
    }

    /* (non-Javadoc)
     * @see home.lixl.paw.store.xml.impl.XmlCiphers#forDecryption(char[])
     */
    @Override
    public ElementDecryption forDecryption(char[] password, Document document) {
        try {
            Key kek = getKey(password);

            final XMLCipher xmlCipher = XMLCipher.getInstance();
            /*
             * The key to be used for decrypting xml data would be obtained
             * from the keyinfo of the EncrypteData using the kek.
             */
            xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
            xmlCipher.setKEK(kek);

            return new ElementDecryptionImpl(xmlCipher, document);
        } catch (NoSuchAlgorithmException | XMLEncryptionException | InvalidKeyException | InvalidKeySpecException bsae) {
            throw new XmlSecureStoreException(bsae);
        }
    }

    SecretKey getKey(char[] password) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException {
        Charset utf8 = StandardCharsets.UTF_8;
        byte[] passBytes = utf8.encode(new String(password)).array();

        byte[] keyBytes = new byte[24];

        if (passBytes.length > keyBytes.length) {
            System.arraycopy(passBytes, 0, keyBytes, 0, keyBytes.length);
        } else {
            System.arraycopy(passBytes, 0, keyBytes, 0, passBytes.length);
            if (passBytes.length < keyBytes.length) {
                Arrays.fill(keyBytes, passBytes.length, keyBytes.length, (byte) 0x0F);
            }
        }

        DESedeKeySpec keySpec = new DESedeKeySpec(keyBytes);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");

        return skf.generateSecret(keySpec);
    }
}
