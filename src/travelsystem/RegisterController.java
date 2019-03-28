/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Gillian
 */
public class RegisterController implements Initializable {

    @FXML
    private JFXTextField nameTxtField;

    @FXML
    private JFXTextField emailTxtField;

    @FXML
    private JFXTextField usernameTxtField;

    @FXML
    private JFXPasswordField passwordTxtField;

    @FXML
    private JFXButton registerBtn;

    @FXML
    private JFXButton backBtn;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void handleRegisterBtn(ActionEvent event) {
        
        //gets data from the fields
        String name = nameTxtField.getText();
        String email = emailTxtField.getText();
        String username = usernameTxtField.getText();
        String password = passwordTxtField.getText();
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String databaseURL = "jdbc:mysql://localhost:3306/bussystem";
            Connection con = (Connection) DriverManager.getConnection(databaseURL, "root", "");
            String insertQuery = "insert into register_details values(null,'"+name+"', '"+email+"', '"+username+"', '"+password+"')";
            Statement stat = con.createStatement();
            int x = stat.executeUpdate(insertQuery);
            System.out.println(x);
            if(x == 1){
                JOptionPane.showMessageDialog(null, "Information added on table");
            }
            
                JOptionPane.showMessageDialog(null, "Successfully Added!");
                
            //transition to MainForm    
            Parent changeToMain = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
            Scene changeMainScene = new Scene(changeToMain);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            mainStage.setScene(changeMainScene);
            mainStage.show();
            
        }catch(Exception e){
            System.out.println(e);
        }
        

    }
    
    @FXML
    void handleBackBtn(ActionEvent event) throws IOException {
        Parent changeToLogin = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene changeLoginScene = new Scene(changeToLogin);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeLoginScene);
        mainStage.show();
    }
    
}
