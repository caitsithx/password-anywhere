// -------------------------------------------------------------------------
//
// Project name: secstore
//
// Platform : Java virtual machine
// Language : JAVA 6.0
//
// Original author: lixl
// -------------------------------------------------------------------------
package home.lixl.paw.store;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 
 */
public final class ModifyGroupParameters {

   public HashMap<String, ModifyGroupData> parameters = new HashMap<>();
   
   public final Map<String, ModifyGroupData> getParameters() {
	return this.parameters;
   }
}
