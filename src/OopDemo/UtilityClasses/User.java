
package OopDemo.UtilityClasses;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList; // Newsfeed
import java.util.HashSet; // Following and followers 
import java.util.Observable; // Observer pattern
import java.util.Observer;

/***********************************************
 * User Class:
 *  Participant in Observer/Composite/Visitor Design pattern
 *     Observer: Observer and Subject
 *     Composite: Leaf
 *     Visitor: Visited.
 **********************************************/
/**
 * Users send out tweet messages, follow others, 
 * and participate in groups. 
 * 
 * @author misslame
 */
public class User extends Observable implements GeneralUser, Observer {
    
    private String userName; // Also represents the id. 
    private ZonedDateTime timeCreated; // when the user was created
    private ZonedDateTime timeUpdated;
    private HashSet<User> followers;
    private HashSet<User> following;
    private ArrayList<Tweet> newsFeed;
    private ArrayList<Tweet> personalMessages;

    /**
     * Instantiates a User with empty attributes and the passed in name as an ID.
     * @param name the will be ID of the User
     */
    public User(String name) {
        userName = name; 
        timeCreated = ZonedDateTime.now();
        timeUpdated = ZonedDateTime.now();
        followers = new HashSet<>();
        following = new HashSet<>();
        newsFeed = new ArrayList<>();
        personalMessages = new ArrayList<>();
    }
    
    /**
     * @return what users are being followed.
     */
    public HashSet<User> getFollowing(){
        return following;
    } 
     
    /**
     * @return this users' newsfeed.
     */
    public ArrayList<Tweet> getNewsFeed(){
        return newsFeed;
    }
    
    /**
     * 
     * @return the messages sent by this user
     */
    public ArrayList<Tweet> getMessages(){
        return personalMessages;
    }
    
    public ZonedDateTime getUpdated(){
        return timeUpdated;
    }
    
    /**
     * (Updates other users' followers list)
     * @param toFollow who this user will follow
     * @return nothing
     */
    public void follow(User toFollow){
        following.add(toFollow);
        toFollow.beFollowed(this);
        timeUpdated = ZonedDateTime.now();
    }
    
    /**
     * Adds a new follower to this user
     * @param newFollower who this user is being followed by
     * @return nothing
     */
    public void beFollowed(User newFollower){
        timeUpdated = ZonedDateTime.now();
        followers.add(newFollower);
    }
    
    /**
     * Adds the passed in parameter to this users' newsfeed
     * and personal messages. 
     * @param message what this user will send out 
     * @return nothing
     */
    public void sendOutTweet(String message){
        Tweet newTweet = new Tweet(message, this);
        newsFeed.add(newTweet); // Adds personal tweet to newsfeed. 
        personalMessages.add(newTweet);
        
        // Update all observers (followers)
        for(User follow : followers){
            follow.update(this, newTweet);
        }
        timeUpdated = ZonedDateTime.now();
    }
    
    @Override
    public String getFormattedCreationTime(){
        return timeCreated.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
    }
    
    @Override
    public String getFormattedUpdatedTime(){
        return timeUpdated.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"));
    }
   
    @Override
    public String toString(){
        return userName;
    }
    
    @Override // (Observer Pattern requirement)
    public void update(Observable o, Object arg) {
        newsFeed.add((Tweet)arg);
    }
    
    @Override // (Visitor Pattern requirement)
    public int count(Visitor visitor) {
        return visitor.visit(this);
    }
    
    @Override
    public boolean exists(String str){
        return userName.equals(str);
    }
    
    @Override // Used for hashset to ensure no duplicates. 
    public int hashCode(){
        return userName.hashCode();
    }
    
    @Override // Used for hashset to ensure no duplicates.
    public boolean equals(Object obj){
        return obj instanceof GeneralUser && ((GeneralUser)obj).toString().equals(this.userName);
    }

    
}