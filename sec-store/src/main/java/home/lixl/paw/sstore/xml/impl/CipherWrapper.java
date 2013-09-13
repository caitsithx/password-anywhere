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

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 *
 * 
 */
public class CipherWrapper {

   public SecretKey getKey(char[] p_key) throws InvalidKeyException,
   NoSuchAlgorithmException, InvalidKeySpecException {
	Charset utf8 = Charset.forName("UTF8");
	byte[] l_passBytes = utf8.encode(new String(p_key)).array();

	byte[] l_keyBytes = new byte[24];

	if (l_passBytes.length > l_keyBytes.length) {
	   System.arraycopy(l_passBytes, 0, l_keyBytes, 0, l_keyBytes.length);
	} else {
	   System.arraycopy(l_passBytes, 0, l_keyBytes, 0, l_passBytes.length);
	   if (l_passBytes.length < l_keyBytes.length) {
		Arrays.fill(l_keyBytes, l_passBytes.length, l_keyBytes.length, (byte) 0x0F);		
	   }
	}

	DESedeKeySpec keySpec = new DESedeKeySpec(l_keyBytes);
	SecretKeyFactory skf = SecretKeyFactory.getInstance("DESede");
	SecretKey key = skf.generateSecret(keySpec);

	return key;
   }
}
