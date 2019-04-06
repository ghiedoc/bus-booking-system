/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Gillian
 */
public class MainFormController implements Initializable {

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private JFXComboBox<String> seatLuggageCbox;


    @FXML
    private Label seatLuggageShow;

    @FXML
    private Label usernameShow;

    @FXML
    private Label homeShowDate;

    @FXML
    private Label homeShowTime;

    @FXML
    private JFXComboBox<String> discountCbox;

    @FXML
    private TextArea receiptTxtArea;

    @FXML
    private JFXButton signOutBtn;

    @FXML
    private JFXButton okBtn;


    @FXML
    private TableView<Schedule> tblSchedule;

    @FXML
    private TableColumn<Schedule, String> busnumColSchedule;

    @FXML
    private TableColumn<Schedule, String> timeColSchedule;

    @FXML
    private TableColumn<Schedule, String> destinationColSchedule;

    @FXML
    private TableColumn<Schedule, String> seatColSchedule;

    @FXML
    private TableColumn<Schedule, String> priceColSchedule;

    @FXML
    private TableView<Destination> tblDestination;

    @FXML
    private TableColumn<Destination, String> toColDestination;

    @FXML
    private TableColumn<Destination, String> fromColDestination;

    @FXML
    private TableView<Schedule> tblBook;

    @FXML
    private TableColumn<Schedule, String> departureColBook;

    @FXML
    private TableColumn<Schedule, String> destinationColBook;

    @FXML
    private TableColumn<Schedule, String> seatColBook;

    @FXML
    private TableColumn<Schedule, String> typeColBook;

    @FXML
    private TableColumn<Schedule, String> priceColBook;

    @FXML
    private JFXTextField busTimeFld;

    @FXML
    private JFXTextField busTypeFld;

    @FXML
    private JFXTextField busDestiFld;

    @FXML
    private JFXTextField busPriceFld;

    @FXML
    private Tab epassTab;

    @FXML
    private JFXTextField busNumberFld;

    @FXML
    private JFXTextField busDateFld;

    @FXML
    private JFXTextField availSeatFld;

    @FXML
    private JFXTextField reserveSeatFld;

    @FXML
    private JFXTextField nameReserveFld;

    //for table
    ObservableList<Schedule> oblist_schedule = FXCollections.observableArrayList();
    ObservableList<Destination> oblist_destination = FXCollections.observableArrayList();

    //for combo box
    final ObservableList source = FXCollections.observableArrayList();
    final ObservableList destination = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ////////////////////////////table_schedule////////////////////////////
        try {
            Connection con = DBConnector.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from bus_details");

            while (rs.next()) {
                oblist_schedule.add(new Schedule(rs.getString("bus_no"), rs.getString("bus_time"),
                        rs.getString("bus_destination"), rs.getString("bus_seat"),
                        rs.getString("bus_price"), rs.getString("bus_type")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //table at home tab
        busnumColSchedule.setCellValueFactory(new PropertyValueFactory<>("bus_no"));
        timeColSchedule.setCellValueFactory(new PropertyValueFactory<>("bus_time"));
        destinationColSchedule.setCellValueFactory(new PropertyValueFactory<>("bus_destination"));
        seatColSchedule.setCellValueFactory(new PropertyValueFactory<>("bus_seat"));
        priceColSchedule.setCellValueFactory(new PropertyValueFactory<>("bus_price"));
        tblSchedule.setItems(oblist_schedule);

        //table at book tab
        departureColBook.setCellValueFactory(new PropertyValueFactory<>("bus_time"));
        destinationColBook.setCellValueFactory(new PropertyValueFactory<>("bus_destination"));
        seatColBook.setCellValueFactory(new PropertyValueFactory<>("bus_seat"));
        typeColBook.setCellValueFactory(new PropertyValueFactory<>("bus_type"));
        priceColBook.setCellValueFactory(new PropertyValueFactory<>("bus_price"));
        tblBook.setItems(oblist_schedule);
        //*****************************************************************************************

        ////////////////////////////table_destination////////////////////////////
        try {
            Connection con = DBConnector.getConnection();

            ResultSet rs = con.createStatement().executeQuery("select * from bus_details");

            while (rs.next()) {
                oblist_destination.add(new Destination(rs.getString("bus_source"), rs.getString("bus_destination")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

        toColDestination.setCellValueFactory(new PropertyValueFactory<>("bus_source"));
        fromColDestination.setCellValueFactory(new PropertyValueFactory<>("bus_destination"));
        tblDestination.setItems(oblist_destination);
        //*****************************************************************************************

        //choose destination text field part set to not editable so users don't modify it.
        setCellValueFromTableToField();
        busNumberFld.setEditable(false);
        busDateFld.setEditable(false);
        busTimeFld.setEditable(false);
        busTypeFld.setEditable(false);
        busDestiFld.setEditable(false);
        busPriceFld.setEditable(false);
        availSeatFld.setEditable(false);
        //**********************************

        //adding item to luggage combo box
        seatLuggageCbox.getItems().addAll(
                "None",
                "Cartons",
                "Bags",
                "Suitcase"
        );

        //discount cbox
        discountCbox.getItems().addAll(
                "None",
                "Student",
                "Senior Citizen",
                "PWD/Disable"
        );

        //usernameShow.setText("");
        showDate();
        showTime();
 
    }

    void showDate() {
        //SHOW DATE
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("MM-dd-yyyy");
        homeShowDate.setText(s.format(d));

    }

    void showTime() {
        //SHOW TIME
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
            homeShowTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    @FXML
    void handleSeatLuggageCbox(ActionEvent event) {
        //SHOW DETAILS ABOUT THE PRICE OFD THE LUGGAGE
        double price = 0;
        String luggage = seatLuggageCbox.getValue();

        if (luggage.equalsIgnoreCase("Cartons")) {
            price = 70;
            System.out.println(price);
        } else if (luggage.equalsIgnoreCase("Bags")) {
            price = 50;
            System.out.println(price);
        } else if (luggage.equalsIgnoreCase("Suitcase")) {
            price = 100;
            System.out.println(price);
        } else if (luggage.equalsIgnoreCase("None")) {
            price = 0;
        }

        //display
        NumberFormat formatter = new DecimalFormat("#0.00");
        String priceStr = String.valueOf(formatter.format(price));
        seatLuggageShow.setText(priceStr);
    }

    @FXML
    void handleOkBtn(ActionEvent event) {

        //SEAT RESERVATION PROCESS
        String bus_no = busNumberFld.getText();
        String username = nameReserveFld.getText();
        int reserve_seat_num = Integer.parseInt(reserveSeatFld.getText());
        int avail_seat_num = Integer.parseInt(availSeatFld.getText());
        int remaining_seat = avail_seat_num - reserve_seat_num;

        ResultSet rs;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bussystem", "root", "");
            Statement stat = con.createStatement();
            String select = "select * from bus_booking where bus_no ='" + bus_no + "'";
            rs = stat.executeQuery(select);

            String insert = "insert into bus_booking values('" + bus_no + "','" + reserveSeatFld.getText() + "','" + username + "')";
            int i = stat.executeUpdate(insert);

            if (i == 1) {
                if (remaining_seat != 0) {
                    String updateQuery = "update bus_details set bus_seat='" + remaining_seat + "' where bus_no='" + bus_no + "'";
                    stat.executeUpdate(updateQuery);
                    JOptionPane.showMessageDialog(null, "Bus seat updated");
                }else{
                    JOptionPane.showMessageDialog(null, "There are no more availble seat");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //DISCOUNT PRICE PROCESS
        String discount_type = discountCbox.getValue();
        String total_discount_str = "";
        double total_discount = 0;
        double bus_price = 0.00;
        double luggage_price = Double.parseDouble(seatLuggageShow.getText());
        

        if (discount_type.equalsIgnoreCase("Student") || discount_type.equalsIgnoreCase("Senior Citizen")
                || discount_type.equalsIgnoreCase("PWD/Disable")) {

            bus_price = Double.parseDouble(busPriceFld.getText());
            System.out.println("original price: " + bus_price);
            double discount_rate = bus_price * 0.20;
            total_discount = bus_price - discount_rate;
            System.out.println("total disc: " + total_discount);
            NumberFormat formatter = new DecimalFormat("#0.00");
            total_discount_str = String.valueOf(formatter.format(total_discount));

        } else {
            System.out.println("no discount");
            bus_price = Double.parseDouble(busPriceFld.getText());
            System.out.println("price: " + bus_price);
        }

        //OVERALL PRICE
        double overall_price_discounted = total_discount + luggage_price;
        double overall_price_woutdisc = bus_price + luggage_price;

        receiptTxtArea.setText("-----------------------------E-PASS-----------------------------\n\n");
        receiptTxtArea.setText("                                     METROUTE\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Bus #: \t\t\t\t\t\t" + busNumberFld.getText() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Bus Type: \t\t\t\t\t" + busTypeFld.getText() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Boarding Time: \t\t\t\t" + busTimeFld.getText() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "From: \t\t\t\t\t\tPITX" + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "To: \t\t\t\t\t\t\t" + busDestiFld.getText() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Additional Service: \t\t\t\t" + seatLuggageCbox.getValue() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Discount Type: \t\t\t\t" + discountCbox.getValue() + "\n");

        receiptTxtArea.setText(receiptTxtArea.getText() + "---------------------------------------------------------------\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Price: \t\t\t\t\t" + "Php. " + busPriceFld.getText() + ".00" + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Discounted Price: \t\t\t" + "Php. " + total_discount_str + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Price: \t\t\t\t\tPhp. " + seatLuggageShow.getText() + "\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Total Price with Discount:\t Php. " + overall_price_discounted);
        receiptTxtArea.setText(receiptTxtArea.getText() + "\nTotal Price without Discount:  Php. " + overall_price_woutdisc);

    }

    private void setCellValueFromTableToField() {
        tblBook.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                Schedule sched = tblBook.getItems().get(tblBook.getSelectionModel().getSelectedIndex());
                busNumberFld.setText(sched.getBus_no());
                busDateFld.setText(sched.getBus_date());
                busTimeFld.setText(sched.getBus_time());
                busTypeFld.setText(sched.getBus_type());
                busDestiFld.setText(sched.getBus_destination());
                busPriceFld.setText(sched.getBus_price());
                availSeatFld.setText(sched.getBus_seat());

            }

        });
    }

    @FXML
    void handleSignOutBtn(ActionEvent event) throws IOException {
        Parent changeToLogin = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene changeLoginScene = new Scene(changeToLogin);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeLoginScene);
        mainStage.show();
    }

    @FXML
    void handleDiscountCbox(ActionEvent event) {

    }

}
