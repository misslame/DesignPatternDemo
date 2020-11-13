
package OopDemo;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import OopDemo.UtilityClasses.GeneralUser;
import OopDemo.UtilityClasses.Root;
import OopDemo.UtilityClasses.Tweet;
import OopDemo.UtilityClasses.User;

/***********************************************
 * AdminView Controller Class:
 *  Controls the entire UI elements and functionality
 * 
 **********************************************/
public class UserViewController implements Initializable {
    
    private User user; // The user who owns the current view
    
    @FXML private Label currentUserName; // the user's name at the top of the UI. 
    
    @FXML private TextField toFollowField; // field of which the user types who they want to follow
    @FXML private TextField tweetMessageField; // field of which the user types a new status tweet
    @FXML private VBox followingView; // displays who follows the user
    @FXML private VBox newsFeedView; // displays the newsfeed of the user. 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // INITIALIZE NOTHING> All dependent on initData call pass in of User obj. 
    }    
    
    // Call for initializing ui elements with User object data. 
    public void initData(User u){
        user = u;
        currentUserName.setText(user.toString());
        updateFollowingView();
        updateNewsFeed();
    }
    
    @FXML // When a user presses enter after typing a user to follow 
    private void onEnterUser(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER)){
           followUser(ke); 
        }
    }
    
    
    @FXML // Event: When user presses "follow" button:
    private void followUser(Event event){
        if(toFollowField.getText().length() > 0 && !toFollowField.getText().equals(user.toString())){
            GeneralUser following = Root.getInstance().getUserFromTree(toFollowField.getText());
            if(following != null && following instanceof User){
                user.follow((User)following);
                updateFollowingView(); 
                toFollowField.clear(); // clears TextField with the contents of the ID to follow
            }else{
                // ALERT ON EMPTY CONTENTS GIVEN
                Alert a = new Alert(Alert.AlertType.NONE, "Couldn't find the user you specified! Try again?",ButtonType.OK); 

                // show the dialog 
                a.show(); 
            }
        }else{
            // ALERT ON EMPTY CONTENTS GIVEN
            Alert a = new Alert(Alert.AlertType.NONE, "Type a valid user's name you want to follow! (You can't follow yourself!)",ButtonType.OK); 

            // show the dialog 
            a.show(); 
        }
    }
    
    @FXML // When a user presses enter after typing a message for a tweet. 
    private void onEnterTweet(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER)){
           postTweet(ke);
        }
    }
    
    @FXML // Event: When user presses "Post" button:
    private void postTweet(Event event){
        if(tweetMessageField.getText().length() > 0){
            user.sendOutTweet(tweetMessageField.getText()); // Sends tweet
            updateNewsFeed();
            tweetMessageField.clear(); // clears TextField with the contents of the message said.
        }else{
            // ALERT ON EMPTY CONTENTS GIVEN
            Alert a = new Alert(Alert.AlertType.NONE, "If you want to say something you need to post something!",ButtonType.OK); 

            // show the dialog 
            a.show(); 
        }
    }
    
    // Changes the ui element to show who the user is following. 
    private void updateFollowingView(){
        HashSet<User> following = user.getFollowing();
        followingView.getChildren().clear();
        
        Iterator i = following.iterator();
        while(i.hasNext()){
            HBox box = new HBox();
            box.getChildren().add(new Label(i.next().toString())); // Add message. 
            box.setAlignment(Pos.CENTER); // Set right
            
            followingView.getChildren().add(box);
        }
    }
    
    @FXML // Event: Refresh button pressed
    // Changes the ui element to display user's newsfeed contents. 
    private void updateNewsFeed(){
        ArrayList<Tweet> newsFeed = user.getNewsFeed();
        newsFeedView.getChildren().clear();
        
        for(Tweet tweet : newsFeed){
            HBox box = new HBox();
            box.getChildren().add(new Label(tweet.toString()));
            box.setAlignment(Pos.CENTER_LEFT);
            
            newsFeedView.getChildren().add(box);
        }
    }
}
