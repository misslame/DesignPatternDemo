
package OopDemo.UtilityClasses;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/***********************************************
 * Root Class (TreeView UI Element):
 *  Participant in Singleton Design pattern
 *     Singleton: Singleton
 **********************************************/
/**
 * Maintains the Root UserGroup and UI Tree element.
 * Ensures only one tree containing the GeneralUsers exists. 
 * 
 * @author misslame
 */
public class Root extends TreeView<GeneralUser>{
    static Root instance = null; // initially null until needed.
    
    private Root(){
        super(); // TreeView<GeneralUser>();
    }
    
    /**
     * calls constructor on case where instance is null.
     * @return instance of the Root class
     */
    static public Root getInstance(){
        if(instance == null){
            instance = new Root(); // Calls constructor once
        }
        
        return instance;
    } 
    
    /**
     * Performs a search to find a particular GeneralUser on the tree
     * @param userToFind ID of User/GeneralUser to search for.
     * @return the object reference to the found GeneralUser
     */
    public GeneralUser getUserFromTree(String userToFind){
        return getUserFromTree(instance.getRoot(), userToFind );
    }
    
    /**
     * search method for finding a GeneralUser on the tree. (Recursive) 
     * @param item Where to search.
     * @param userToFind What to search.
     * @return the object reference to the found GeneralUser
     */
    private GeneralUser getUserFromTree(TreeItem<GeneralUser> item, String userToFind ){
        
        if(item != null && item.getValue().toString().equals(userToFind)){
            return item.getValue();
        }
        
        for(TreeItem<GeneralUser> child : item.getChildren()){
            GeneralUser g = getUserFromTree(child, userToFind);
            if (g != null){ // If null continue search
                return g;
            }
        }
        
        return null;
    }
    
    /**
     * Implements a visitor to find all Users within tree. 
     * @return the amount of Users that exist at all levels within tree.
     */
    public int countUsers(){
        Visitor visitor = new UserCountVisitor();
        return instance.getRoot().getValue().count(visitor);
    }
    
    /**
     * Implements a visitor to find all UserGroups within tree. 
     * @return the amount of UserGroups that exist at all levels within tree.
     */
    public int countGroups(){
        Visitor visitor = new UserGroupCountVisitor();
        return instance.getRoot().getValue().count(visitor);
    }
    
    /**
     * Implements a visitor to find all messages sent by users within tree. 
     * @return the amount of messages sent by users at all levels within tree.
     */
    public int countMessages(){
        Visitor visitor = new UserMessagesVisitor();
        return instance.getRoot().getValue().count(visitor);
    }
    
    /**
     * Implements a visitor to find all messages that are positive sent by 
     * users within tree.
     * @return the amount of messages that are positive sent by users at all levels within tree.
     */
    public int countPositiveMessages(){
        Visitor visitor = new UserPositiveMessagesVisitor();
        return instance.getRoot().getValue().count(visitor);
    }
}
