/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Gillian
 */
public class AdminController implements Initializable {

    @FXML
    private JFXTextField unameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private Button loginBtn;
    @FXML
    private JFXButton backBtn;
    @FXML
    private Label lblU;
    @FXML
    private Label lblP;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * set the visibility of the red asterisk to false so that it will become
         * hidden on the start.
         */
        lblU.setVisible(false);
        lblP.setVisible(false);
    }

    /**
     * fetch the data from the database if it will match the admin username and password
     * according to the data inputted by the admin.
     * @param event 
     */
    @FXML
    void handleAdminBtn(ActionEvent event) {
        //Serve as a validator when the field are empty
        if (unameField.getText().equals("")) {
            lblU.setVisible(true);
        }
        if (passwordField.getText().equals("")) {
            lblP.setVisible(true);
        } else {
            try {
                Connection con = (Connection) DBConnector.getConnection();
                PreparedStatement ps;
                ps = con.prepareStatement("select * from admin_details where username = ? and password = ?");
                ps.setString(1, unameField.getText());
                ps.setString(2, passwordField.getText());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Parent changeToAcp = FXMLLoader.load(getClass().getResource("/view/AdminControlPanel.fxml"));
                    Scene changeAdminScene = new Scene(changeToAcp);
                    Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    mainStage.setScene(changeAdminScene);
                    mainStage.show();

                    JOptionPane.showMessageDialog(null, "Successfully logged in!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    void handleBackBtn(ActionEvent event) throws IOException {
        Parent changeToLogin = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene changeRegScene = new Scene(changeToLogin);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeRegScene);
        mainStage.show();
    }

}
