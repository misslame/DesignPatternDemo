
package OopDemo.UtilityClasses;

/**
 *  Utility class for representing messages by 
 *  authors
 * 
 * @author misslame
 */
public class Tweet {
    
    private String message; // What the author said.
    private User author; // Who the author is.
    private boolean isPositive; // whether the message is positive or not
    
    Tweet(String msg, User whoSentIt){
        message = msg;
        author = whoSentIt;
        isPositive = (msg.contains("great") || msg.contains("good") || msg.contains("excellent"));
    }
    
    @Override // represented like: author: message
    public String toString(){
        return author.toString() + ": " + message;
    }
    
    /**
     * @return whether the tweet message is positive or not
     */
    public boolean isMessagePositive(){
        return isPositive;
    }
}
