
package OopDemo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/***********************************************
 * Login Controller Class:
 *  Controls the entire UI elements and functionality
 *  of the initial login.
 * 
 *  ** This is for fun **
 * 
 **********************************************/
public class Login implements Initializable {

    @FXML
    private void startAdminScene(ActionEvent event) throws IOException {
        System.out.println("Started Admin Scene");
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
        
        Scene tableViewScene = new Scene(tableViewParent);
        
        // This will get the stage:
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // NOTHING TO INITIALIZE
    }    
    
}
