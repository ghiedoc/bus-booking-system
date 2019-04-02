/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelsystem;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Gillian
 */
public class AdminControlPanelController implements Initializable {

    @FXML
    private JFXTextField busNumberField;

    @FXML
    private JFXTextField sourceField;

    @FXML
    private JFXTextField timeField;

    @FXML
    private JFXTextField seatNumberField;

    @FXML
    private JFXTextField destinationField;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private DatePicker dateDepart;

    @FXML
    private Button saveBtn;

    @FXML
    private JFXComboBox<String> typeBusCbox;

    @FXML
    private JFXTextField priceField;

    @FXML
    private Button signOutBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        typeBusCbox.getItems().addAll("Aircon Bus",
                "Ordinary Bus");
        typeBusCbox.getSelectionModel().selectFirst();
    }

    private void clearFieldValue() {
        busNumberField.setText("");
        seatNumberField.setText("");
        sourceField.setText("");
        destinationField.setText("");
        timeField.setText("");
        priceField.setText("");
    }

    @FXML
    void handleSaveBtn(ActionEvent event) {
        String bus_no = busNumberField.getText();
        String bus_seat = seatNumberField.getText();
        String bus_source = sourceField.getText();
        String bus_desti = destinationField.getText();
        String bus_time = timeField.getText();
        String bus_date = datePicker.getValue().toString();      
        String bus_price = priceField.getText();
        //combo box
        String bus_type = typeBusCbox.getValue();

        if (bus_no.equals("") || bus_seat.equals("") || bus_source.equals("") || bus_desti.equals("") || bus_time.equals("")
                || bus_type.equals("") || bus_price.equals("")) {
            JOptionPane.showMessageDialog(null, "There are empty fields!");
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String databaseURL = "jdbc:mysql://localhost:3306/bussystem";
                com.mysql.jdbc.Connection con = (com.mysql.jdbc.Connection) DriverManager.getConnection(databaseURL, "root", "");
                Statement stat = con.createStatement();
                String selectQuery = "SELECT * FROM bus_details WHERE bus_no = '" + bus_no + "'";

                ResultSet rs = stat.executeQuery(selectQuery);

                if (rs.next() == true) {

                    JOptionPane.showMessageDialog(null, "Already added that details!");
                    clearFieldValue();

                } else {
                    String insertQuery = "insert into bus_details values(null,'" + bus_no + "', '" + bus_seat + "', '" + bus_source + "',"
                            + " '" + bus_desti + "', '" + bus_time + "', '" + bus_date + "', '" + bus_type + "', '" + bus_price + "')";
                    stat.executeUpdate(insertQuery);
                    JOptionPane.showMessageDialog(null, "Bus Details Added!");
                    clearFieldValue();
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    @FXML
    void handleSignOutBtn(ActionEvent event) throws IOException {

        Parent Login = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene changeAdminScene = new Scene(Login);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeAdminScene);
        mainStage.show();

    }

    @FXML
    void handleResetBtn(ActionEvent event) {

    }

}
