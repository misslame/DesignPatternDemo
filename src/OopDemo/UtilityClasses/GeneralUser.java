
package OopDemo.UtilityClasses;

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

    public int count(Visitor visitor);

}

