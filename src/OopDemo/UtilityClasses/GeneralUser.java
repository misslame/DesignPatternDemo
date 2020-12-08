
package OopDemo.UtilityClasses;

import java.time.ZonedDateTime;

/***********************************************
 * User Interface:
 *  Participant in Composite/Visitor Design pattern
 *     Composite: Component
 *     Visitor: Visited
 * 
 * Known where implemented:
 *     - User
 *     - UserGroup
 **********************************************/
/**
 * @author misslame
 */
public interface GeneralUser {

    public ZonedDateTime getUpdated();
    public int count(Visitor visitor);
    public String getFormattedCreationTime();
    public String getFormattedUpdatedTime();
    public boolean exists(String str);

}

