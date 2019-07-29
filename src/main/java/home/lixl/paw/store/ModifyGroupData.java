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


import home.lixl.paw.store.domain.Group;

/**
 *
 * 
 */
public final class ModifyGroupData extends Group {

   /**
    * @return the action
    * 
    */
   public ModifyGroupAction getAction() {
      return this.action;
   }
   
   /**
    * @param p_action the action to set
    * 
    */
   public void setAction(ModifyGroupAction p_action) {
      this.action = p_action;
   }
   

   private ModifyGroupAction action = null;
   private Group group = null;
   
}
