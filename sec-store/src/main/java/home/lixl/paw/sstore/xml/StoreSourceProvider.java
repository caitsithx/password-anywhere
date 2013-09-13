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

import javax.xml.transform.Result;

import org.xml.sax.InputSource;

/**
 *
 * 
 */
public interface StoreSourceProvider {

   /**
    * @return
    * 
    */
   InputSource getInput();

   /**
    * @return
    * 
    */
   Result getOutput();

   /**
    * @param p_fileLocation
    * 
    */
   void newFileSource(String p_fileLocation);

}
