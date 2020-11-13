
package OopDemo.UtilityClasses;

import java.util.Iterator;

/**
 * 
 * @author misslame
 */
interface Visitor {
    int visit(User user);
    int visit(UserGroup user);
}

/**
 * To visit Users and count their existence.
 * @author misslame
 */
class UserCountVisitor implements Visitor{

    /**
     * 
     * @param user who is being counted.
     * @return 1 signifying a single User.
     */
    @Override
    public int visit(User user) {
        return 1;
    }
    
    /**
     * 
     * @param user group to count through.
     * @return number of users in the group.
     */
    @Override
    public int visit(UserGroup user) {
        int sum = 0;
        
        Iterator<GeneralUser> i = user.getMembers().iterator();
        while(i.hasNext()){
            GeneralUser g = i.next();
            sum += g.count(this);
            
        }
        
        return sum; // number of users in the group
    }
    
}

/**
 * To visit Groups and count their existence.  
 * @author misslame
 */
class UserGroupCountVisitor implements Visitor{
    
    /**
     * 
     * @param user 
     * @return 0 signifying it is not a group.
     */
    @Override
    public int visit(User user) {
        return 0;
    }
    
    /**
     * 
     * @param user what group that will be counted. 
     * @return number of groups in a group + 1 for itself. 
     */
    @Override
    public int visit(UserGroup user) {
        int sum = 0;
        
        Iterator<GeneralUser> i = user.getMembers().iterator();
        while(i.hasNext()){
            GeneralUser g = i.next();
            sum += g.count(this);
            
        }
        
        return sum + 1; // number of groups in this group, plus this group
    }
    
}

/**
 * To visit Users and Groups to find how many messages have been sent.
 * @author misslame
 */
class UserMessagesVisitor implements Visitor{
    
    /**
     * 
     * @param user
     * @return the amount of messages the passed in user has sent.
     */
    @Override
    public int visit(User user) {
        return user.getMessages().size();
    }
    
    /**
     * 
     * @param user the group to count through
     * @return the amount of messages the passed in group's members have sent.
     */
    @Override
    public int visit(UserGroup user) {
        int sum = 0;
        
        Iterator<GeneralUser> i = user.getMembers().iterator();
        while(i.hasNext()){
            GeneralUser g = i.next();
            sum += g.count(this);
            
        }
        
        return sum; // number of messages
    }
    
}


/**
 * To visit Users and Groups to find how many *positive* messages have been sent.
 * @author misslame
 */
class UserPositiveMessagesVisitor implements Visitor{
    
    /**
     * 
     * @param user 
     * @return number of positive messages the passed in user has sent.
     */
    @Override
    public int visit(User user) {
        int sum = 0;
        
        for(Tweet tweet : user.getMessages()){
            sum += (tweet.isMessagePositive()? 1:0);
        }
        
        return sum; // number of positive messages from this user
    }
    
    /**
     * 
     * @param user group to count from.
     * @return the number of positive messages the users in the passed in group have sent.
     */
    @Override
    public int visit(UserGroup user) {
       int sum = 0;
        
        Iterator<GeneralUser> i = user.getMembers().iterator();
        while(i.hasNext()){
            GeneralUser g = i.next();
            sum += g.count(this);
            
        }
        
        return sum; // number of positive messages sent by users in this group. 
    }
    
}