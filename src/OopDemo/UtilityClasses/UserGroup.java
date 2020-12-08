
package OopDemo.UtilityClasses;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private ZonedDateTime timeCreated; // when the user group was created
    private ZonedDateTime timeUpdated;
    private HashSet<GeneralUser> members; // members that belong in the group.
    
    /**
     * Instantiates a UserGroup with empty attributes and the passed in name as an ID.
     * @param name the will be ID of the User
     */
    public UserGroup(String name){
        groupName = name;
        timeCreated = ZonedDateTime.now();
        timeUpdated = ZonedDateTime.now();
        members = new HashSet<>();
    }
    
    /**
     * @return members in the group
     */
    public HashSet<GeneralUser> getMembers(){
        return members;
    }
    
    public ZonedDateTime getUpdated(){
        return ZonedDateTime.of(2000, 1,1,12,00,00,00,ZoneId.systemDefault());
    }
    
    /**
     * @param gu GeneralUser that will be added as a member to the group
     * @return nothing
     */
    public void addMember(GeneralUser gu){
        members.add(gu);
        timeUpdated = ZonedDateTime.now();
    }
    
    @Override
    public String getFormattedCreationTime() {
        return timeCreated.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
    }
    
    @Override
    public String getFormattedUpdatedTime() {
        return timeUpdated.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
    }
    
    @Override
    public String toString(){
        return groupName;
    }
    
    @Override // Visitor Pattern requirement
    public int count(Visitor visitor) {
        return visitor.visit(this);
    }
    
    @Override
    public boolean exists(String str) {
        for( GeneralUser u : members ){
            if(u.exists(str)){
                return true;
            }
        }
        return false; 
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
