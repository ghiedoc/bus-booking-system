/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Gillian
 */
public class AdminControlPanelController implements Initializable {

    @FXML
    private Label showDate;
    @FXML
    private Label showTime;
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
    private Button saveBtn;
    @FXML
    private JFXComboBox<String> typeBusCbox;
    @FXML
    private JFXTextField priceField;
    @FXML
    private JFXTextField adminFld;
    @FXML
    private JFXButton newAdminBtn;
    @FXML
    private JFXPasswordField passwordFld;
    @FXML
    private TableView<AdminList> tblListAdmins;
    @FXML
    private TableColumn<AdminList, String> admInNameCol;
    @FXML
    private TableColumn<AdminList, String> adminUserCol;
    @FXML
    private Button signOutBtn;
    @FXML
    private JFXTextField nameFld;
    @FXML
    private Button deleteBtn;

    //for table
    ObservableList<AdminList> admin_list = FXCollections.observableArrayList();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sourceField.setText("PITX");
        sourceField.setEditable(false);

        typeBusCbox.getItems().addAll("Aircon Bus", "Ordinary Bus");
        typeBusCbox.getSelectionModel().selectFirst();

        datePicker.setValue(LocalDate.now());
        showDate();
        showTime();

        //admin_list table
        try {
            Connection con = DBConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from admin_details");

            while (rs.next()) {
                admin_list.add(new AdminList(rs.getString("name"), rs.getString("username")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //table at admin_list
        admInNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        adminUserCol.setCellValueFactory(new PropertyValueFactory<>("admin"));
        tblListAdmins.setItems(admin_list);

        //number validator
        NumberValidator numVali = new NumberValidator();
        priceField.getValidators().add(numVali);

        numVali.setMessage("Only numbers are allowed");

        priceField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    priceField.validate();
                }
            }
        });

        setCellValueFromTableToField();
    }

    void showDate() {
        //SHOW DATE
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("MM-dd-yyyy");
        showDate.setText(s.format(d));

    }

    void showTime() {
        //SHOW TIME
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            showTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

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
                //String selectQuery = "SELECT * FROM bus_details WHERE bus_no = '" + bus_no + "'";
                //ResultSet rs = stat.executeQuery(selectQuery);

                String insertQuery = "insert into bus_details values(null,'" + bus_no + "', '" + bus_seat + "', '" + bus_source + "',"
                        + " '" + bus_desti + "', '" + bus_time + "', '" + bus_date + "', '" + bus_type + "', '" + bus_price + "')";
                stat.executeUpdate(insertQuery);
                JOptionPane.showMessageDialog(null, "Bus Details Added!");
                clearFieldValue();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    void handleAddAdminBtn(ActionEvent event) {
        String name = nameFld.getText();
        String admin = adminFld.getText();
        String password = passwordFld.getText();

        if (name.equals("") || admin.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Missing Fields!");
        } else {
            try {
                Connection con = DBConnector.getConnection();
                String insertQuery = "insert into admin_details values(null,'" + name + "','" + admin + "', '" + password + "')";
                Statement stat = con.createStatement();
                int x = stat.executeUpdate(insertQuery);
                System.out.println(x);
                if (x == 1) {
                    JOptionPane.showMessageDialog(null, "Successfully Added!");
                }
               refreshTable();
               adminClearField();

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

    private void setCellValueFromTableToField() {
        tblListAdmins.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                AdminList al = tblListAdmins.getItems().get(tblListAdmins.getSelectionModel().getSelectedIndex());
                nameFld.setText(al.getName());
                adminFld.setText(al.getAdmin());
            }

        });
    }

    public void deleteAdmin() {
        String name = nameFld.getText();

        try {
            String sql = "delete from `admin_details` where name = ?";

            Connection con = DBConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Removed Successfully!");
            refreshTable();
            

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void refreshTable() {    
        
        admin_list.clear();
        try{
            String query = "select * from admin_details";
            PreparedStatement ps;
            Connection con = DBConnector.getConnection();
            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                admin_list.add(new AdminList(rs.getString("name"), rs.getString("username")));
            }
            tblListAdmins.setItems(admin_list);
            ps.close();
            rs.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void adminClearField(){
        nameFld.setText("");
        adminFld.setText("");
        passwordFld.setText("");
    }

}

//                String selectQuery = "SELECT * FROM bus_details WHERE bus_no = '" + bus_no + "'";
//
//                ResultSet rs = stat.executeQuery(selectQuery);
//
//                if (rs.next() == true) {
//
//                    JOptionPane.showMessageDialog(null, "Already added that details!");
//                    clearFieldValue();
//
//                } else {
//                    String insertQuery = "insert into bus_details values(null,'" + bus_no + "', '" + bus_seat + "', '" + bus_source + "',"
//                            + " '" + bus_desti + "', '" + bus_time + "', '" + bus_date + "', '" + bus_type + "', '" + bus_price + "')";
//                    stat.executeUpdate(insertQuery);
//                    JOptionPane.showMessageDialog(null, "Bus Details Added!");
//                    clearFieldValue();
//                }
