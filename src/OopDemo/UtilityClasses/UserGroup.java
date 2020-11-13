
package OopDemo.UtilityClasses;

import java.util.HashSet; // list of users in the group

/***********************************************
 * UserGroup Class:
 *  Participant in Composite Design pattern
 *     Composite: Composite
 *     Visitor: Visited
 **********************************************/
/**
 * Contains and manages groups and users.
 * @author misslame
 */
public class UserGroup implements GeneralUser{
    
    private String groupName; // Also represents the ID
    private HashSet<GeneralUser> members; // members that belong in the group.
    
    /**
     * Instantiates a UserGroup with empty attributes and the passed in name as an ID.
     * @param name the will be ID of the User
     */
    public UserGroup(String name){
        groupName = name;
        members = new HashSet<>();
    }
    
    /**
     * @return members in the group
     */
    public HashSet<GeneralUser> getMembers(){
        return members;
    }
    
    /**
     * @param gu GeneralUser that will be added as a memeber to the group
     * @return nothing
     */
    public void addMember(GeneralUser gu){
        members.add(gu);
    }
    
    @Override
    public String toString(){
        return groupName;
    }
    
    @Override // Visitor Pattern requirement
    public int count(Visitor visitor) {
        return visitor.visit(this);
    }
    
    @Override // Used for hashset to ensure no duplicates. 
    public int hashCode(){
        return groupName.hashCode();
    }
    
    @Override // Used for hashset to ensure no duplicates. 
    public boolean equals(Object obj){
        return obj instanceof GeneralUser && ((GeneralUser)obj).toString().equals(this.groupName);
    }
    
}
