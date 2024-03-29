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
import javafx.scene.control.TextField;
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
    @FXML
    private TableView<Schedule> tblBusDetails;
    @FXML
    private TableColumn<Schedule, String> busNumCol;
    @FXML
    private TableColumn<Schedule, String> destiCol;
    @FXML
    private TableColumn<Schedule, String> sourceCol;
    @FXML
    private TableColumn<Schedule, String> timeCol;
    @FXML
    private TableColumn<Schedule, String> dateCol;
    @FXML
    private TableColumn<Schedule, String> typeCol;
    @FXML
    private TableColumn<Schedule, String> seatCol;
    @FXML
    private TableColumn<Schedule, String> priceCol;
    @FXML
    private Button busDelete;
    @FXML
    private TextField busNoFld;

    //for table
    ObservableList<AdminList> admin_list = FXCollections.observableArrayList();
    ObservableList<Schedule> bus_details = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sourceField.setText("PITX");
        sourceField.setEditable(false);
        busNoFld.setEditable(false);

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
            refreshTable();

        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //table at admin_list
        admInNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        adminUserCol.setCellValueFactory(new PropertyValueFactory<>("admin"));
        tblListAdmins.setItems(admin_list);

        //bus_list table
        try {
            Connection con = DBConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from bus_details");

            while (rs.next()) {
                bus_details.add(new Schedule(rs.getString("bus_no"), rs.getString("bus_destination"),
                        rs.getString("bus_source"), rs.getString("bus_time"), rs.getString("bus_date"),
                        rs.getString("bus_type"), rs.getString("bus_seat"), rs.getString("bus_price")));
            }
            //refreshBusTable();

        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //table for bus_details
        busNumCol.setCellValueFactory(new PropertyValueFactory<>("bus_no"));
        destiCol.setCellValueFactory(new PropertyValueFactory<>("bus_destination"));
        sourceCol.setCellValueFactory(new PropertyValueFactory<>("bus_source"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("bus_time"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("bus_date"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("bus_type"));
        seatCol.setCellValueFactory(new PropertyValueFactory<>("bus_seat"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("bus_price"));
        tblBusDetails.setItems(bus_details);

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

        //fetch data from table into field
        setCellValueFromTableToField();
        setCellValueFromTableToFieldBusNo();
    }
    
    /**
     * This method will allow to show the date.
     */
    void showDate() {
        //SHOW DATE
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("MM-dd-yyyy");
        showDate.setText(s.format(d));

    }
    
    /**
     * This method will allow to show the time.
     */
    void showTime() {
        //SHOW TIME
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            showTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    /**
     * This will erase all the inputted data inside the field.
     */
    private void clearFieldValue() {
        busNumberField.setText("");
        seatNumberField.setText("");
        destinationField.setText("");
        timeField.setText("");
        priceField.setText("");
    }
    
    /**
     * This will erase all the inputted data inside the field.
     */
    public void adminClearField() {
        nameFld.setText("");
        adminFld.setText("");
        passwordFld.setText("");
    }
    
    /**
     * This will refresh the table so that if you delete
     * or insert new admin it will refresh and you will
     * see the changes.
     */
    public void refreshTable() {

        admin_list.clear();

        try {
            String admin_query = "select * from admin_details";
            PreparedStatement ps;
            Connection con = DBConnector.getConnection();
            ps = con.prepareStatement(admin_query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                admin_list.add(new AdminList(rs.getString("name"), rs.getString("username")));
            }
            tblListAdmins.setItems(admin_list);
            ps.close();
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This will refresh the table on bus details.
     */
    public void refreshBusTable() {
    
        bus_details.clear();

        try {
            String bus_query = "select * from bus_details";
            PreparedStatement ps;
            Connection con = DBConnector.getConnection();
            ps = con.prepareStatement(bus_query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                bus_details.add(new Schedule(rs.getString("bus_no"), rs.getString("bus_destination"),
                        rs.getString("bus_source"), rs.getString("bus_time"), rs.getString("bus_date"),
                        rs.getString("bus_type"), rs.getString("bus_seat"), rs.getString("bus_price")));
            }
            tblBusDetails.setItems(bus_details);
            ps.close();
            rs.close();
            
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * This will allow to insert data to the database for bus details.
     * @param event 
     */
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
                Connection con = DBConnector.getConnection();
                Statement stat = con.createStatement();
                String insertQuery = "insert into bus_details values(null,'" + bus_no + "', '" + bus_desti + "', '" + bus_source + "',"
                        + " '" + bus_time + "', '" + bus_date + "', '" + bus_type + "', '" + bus_seat + "', '" + bus_price + "')";               
                stat.executeUpdate(insertQuery);
                JOptionPane.showMessageDialog(null, "Bus Details Added!");
                refreshBusTable();
                clearFieldValue();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    /**
     * This will allow to insert a new administrator in the database.
     * @param event 
     */
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

    /**
     * This will allow to fetch the data from the table
     * to the text fields. (administrator)
     */
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
    
    /**
     * This will allow to fetch the data from the table
     * to the text fields. (bus_details)
     */
    private void setCellValueFromTableToFieldBusNo() {
        tblBusDetails.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                Schedule sched = tblBusDetails.getItems().get(tblBusDetails.getSelectionModel().getSelectedIndex());
                busNoFld.setText(sched.getBus_no());
            }
        });
    }

    /**
     * This will allow you to delete an administrator account to the database.
     */
    @FXML
    public void deleteAdmin() {
        String name = nameFld.getText();

        try {
            String sql = "delete from `admin_details` where name = ?";

            Connection con = DBConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Removed Admin Successfully!");
            refreshTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    /**
     * This will allow you to delete bus details to the database.
     */
    @FXML
    public void deleteBusDetail() {
        String bus_no = busNoFld.getText();

        try {
            String sql = "delete from `bus_details` where bus_no = ?";

            Connection con = DBConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bus_no);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Removed bus details Successfully!");
            refreshBusTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    /**
     * This will allow to switch scene from AdminControllerPanel.java
     * into the Login.java Form
     * @param event
     * @throws IOException 
     */
    @FXML
    void handleSignOutBtn(ActionEvent event) throws IOException {

        Parent Login = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene changeAdminScene = new Scene(Login);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeAdminScene);
        mainStage.show();

    }

}
