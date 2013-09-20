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

import home.lixl.paw.sstore.xml.plain.GroupType;

/**
 *
 * 
 */
public final class ModifyGroupData extends GroupType{

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
   
   /**
    * @return the group
    * 
    */
   public GroupType getGroup() {
      return this.group;
   }
   
   /**
    * @param p_group the group to set
    * 
    */
   public void setGroup(GroupType p_group) {
      this.group = p_group;
   }
   
   private ModifyGroupAction action = null;
   private GroupType group = null;
   
}
