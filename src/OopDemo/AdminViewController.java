
package OopDemo;

// Other:
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

// Personal Classes
import OopDemo.UtilityClasses.Root;
import OopDemo.UtilityClasses.GeneralUser;
import OopDemo.UtilityClasses.User;
import OopDemo.UtilityClasses.UserGroup;

//UI:
/**************************/
    // controller class
import java.net.URL;
import javafx.fxml.Initializable;
/**************************/
    // New scenes
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
/**************************/
    // input events. 
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
/**************************/
    // UI Objects
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
/**************************/


/***********************************************
 * AdminView Controller Class:
 *  Controls the entire UI elements and functionality
 * 
 **********************************************/
public class AdminViewController implements Initializable {

    private TreeView<GeneralUser> tree; // tree of users. 
    private GeneralUser selectedElement; // What user is selected in the tree. 
    
    @FXML private AnchorPane treePane; // Where tree is initialized
    @FXML private TextField userID; // text field for entering a User ID
    @FXML private TextField groupID; // text field for entering a UserGroup ID
    @FXML private TextField infoField; // text field for expressing info to the user
    
    @Override // On init -
    public void initialize(URL url, ResourceBundle rb) {
        initializeTreeView();
        
        // Tree View event handler:
        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event)->{
            handleMouseClicked(event);
        };
        
        tree.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);
    }
    
    // Initialize it with tree view items and place in to Anchor Pane. 
    private void initializeTreeView(){
        tree = Root.getInstance();
        tree.setMaxHeight(384);
        tree.prefHeight(384);
        tree.setMaxWidth(188);
        tree.prefWidth(188);
        treePane.getChildren().add(tree);
        
        TreeItem<GeneralUser> rootTreeItem = new TreeItem<>(new UserGroup("Root"));
        rootTreeItem.setExpanded(true);
        
        tree.setRoot(rootTreeItem);

    }
    
    // When tree is clicked on-
    private void handleMouseClicked(MouseEvent event){
        Node node = event.getPickResult().getIntersectedNode();
        if(node instanceof Text || (node  instanceof TreeCell && ((TreeCell) node).getText() != null)){
            selectedElement = tree.getSelectionModel().getSelectedItem().getValue();
            
            //debug (should never be null)
            System.out.println("selected value is : " + selectedElement.toString());
        }
    }
    
    @FXML // When a user presses enter after typing in a user ID
    private void onEnterUser(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER)){
           onAddUser(ke); 
        }
    }
    
    @FXML // event: When add user button is pressed. 
    private void onAddUser(Event event){
        if(selectedElement != null && selectedElement instanceof UserGroup){
            if(userID.getText().length() > 0){
                GeneralUser newUser = new User(userID.getText());
                
                TreeItem<GeneralUser> treeitem = (TreeItem)tree.getSelectionModel().getSelectedItem();
                TreeItem<GeneralUser> newTreeItem = new TreeItem<>(newUser);
                ((UserGroup)treeitem.getValue()).addMember(newUser);
                treeitem.getChildren().add(newTreeItem);
                
                tree.refresh(); // Reloads the tree to display new contents. 
                userID.clear(); // Clears the TextField where the ID was typed
            }else{
                // ALERT ON NO USERNAME ENTERED.
                Alert a = new Alert(Alert.AlertType.NONE, "Please enter a name for the User you are adding.",ButtonType.OK); 

                // show the dialog 
                a.show(); 
            }
        }else{
            // ALERT ON NO SELECTED ELEMENT OR NOT A SELECTED GROUP
            Alert a = new Alert(Alert.AlertType.NONE, "You haven't selected where you will add this user!",ButtonType.OK); 

            // show the dialog 
            a.show(); 
        }
        
    }
    
    
    @FXML // When a user presses enter after typing in a group ID 
    private void onEnterGroup(KeyEvent ke){
        if(ke.getCode().equals(KeyCode.ENTER)){
           onAddGroup(ke); 
        }
    }
    
    @FXML // event: When add user button is pressed. 
    private void onAddGroup(Event event){
        if(selectedElement != null && selectedElement instanceof UserGroup){
            if(groupID.getText().length() > 0){
                GeneralUser newUser = new UserGroup(groupID.getText());
                
                TreeItem<GeneralUser> treeitem = (TreeItem)tree.getSelectionModel().getSelectedItem();
                TreeItem<GeneralUser> newTreeItem = new TreeItem<>(newUser);
                ((UserGroup)treeitem.getValue()).addMember(newUser);
                treeitem.getChildren().add(newTreeItem);
                
                tree.refresh(); // Reloads the tree to display new contents.
                groupID.clear(); // Clears the TextField where the ID was typed
            }else{
                // ALERT ON NO USERNAME ENTERED.
                Alert a = new Alert(Alert.AlertType.NONE, "Please enter a name for the group you are adding.",ButtonType.OK); 

                // show the dialog 
                a.show(); 
            }
        }else{
            // ALERT ON NO SELECTED ELEMENT OR NOT A SELECTED GROUP
            Alert a = new Alert(Alert.AlertType.NONE, "You haven't selected where you will add this group!",ButtonType.OK); 

            // show the dialog 
            a.show(); 
        }
    }
    

    /* THIS DOES NOT CLOSE THE CURRENT WINDOW */
    @FXML // When opening the mini view of user information
    private void openUserView(Event event){
        if(selectedElement instanceof User){
            try {
                System.out.println("Started User " + selectedElement.toString() + " view");
                FXMLLoader loader = new FXMLLoader( getClass().getResource("UserView.fxml")); // Loads the specified fxml file. 
                
                Scene userViewScene = new Scene(loader.load());
                
                Stage window = new Stage();
                window.initModality(Modality.WINDOW_MODAL);
                window.setResizable(false);
                window.setScene(userViewScene);
                
                UserViewController controller = loader.getController();
                controller.initData((User)selectedElement); // sends selected user to new user view for initialization. 
                
                window.show();
                
            } catch (IOException ex) {
                Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }else{
            // ALERT ON NOT A SELECTED USER
            Alert a = new Alert(Alert.AlertType.NONE, "Select a valid user!",ButtonType.OK); 

            // show the dialog 
            a.show(); 
        }
    }
    
    @FXML // Event: Outputting the amount of users there are
    private void onCountUserPressed(Event event){
        infoField.clear(); // Clears the TextField where previous information was sent.
        infoField.setText("Number of Users: " + (Root.getInstance().countUsers()));
    }
    
    @FXML // Event: Outputting the amount of groups there are
    private void onCountGroupPressed(Event event){
        infoField.clear(); // Clears the TextField where previous information was sent.
        infoField.setText("Number of Groups: " + (Root.getInstance().countGroups()));
    }
    
    @FXML // Event: Outputting the amount of messages the users have 
    private void onCountMessagesPressed(Event event){
        infoField.clear(); // Clears the TextField where previous information was sent.
        infoField.setText("Number of Messages: " + (Root.getInstance().countMessages()));
    }
    
    @FXML // Event: Outputting the amount of positive messages the users have
    private void onCountPositiveMessagesPressed(Event event){
        infoField.clear(); // Clears the TextField where previous information was sent.
        infoField.setText("Number of Positive Messages: " + (Root.getInstance().countPositiveMessages()));
    }
    
}
