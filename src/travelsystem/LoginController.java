/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
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
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Gillian
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField usernameTxtField;

    @FXML
    private JFXPasswordField passwordTxtField;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXButton registerBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    void handleLoginBtn(ActionEvent event) throws IOException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String databaseURL = "jdbc:mysql://localhost:3306/bussystem";
            Connection con = (Connection) DriverManager.getConnection(databaseURL, "root", "");
            PreparedStatement ps;
            ps = con.prepareStatement("SELECT * FROM register_details WHERE username = ? AND password = ?");
            ps.setString(1, usernameTxtField.getText());
            ps.setString(2, passwordTxtField.getText());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Parent changeToReg = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                Scene changeRegScene = new Scene(changeToReg);
                Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                mainStage.setScene(changeRegScene);
                mainStage.show();

                JOptionPane.showMessageDialog(null, "Successfully logged in!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    void handleRegisterBtn(ActionEvent event) throws IOException {

        Parent changeToMain = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Scene changeMainScene = new Scene(changeToMain);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeMainScene);
        mainStage.show();
    }

}
