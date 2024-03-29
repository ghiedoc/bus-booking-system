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
import java.sql.PreparedStatement;
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
    private JFXTextField availSeatFld;
    @FXML
    private JFXTextField reserveSeatFld;
    @FXML
    private JFXTextField nameReserveFld;

    //for table
    //I created an encapsulated class where it will return all the data's needed to populate in the jtable 
    ObservableList<Schedule> oblist_schedule = FXCollections.observableArrayList();
    ObservableList<Destination> oblist_destination = FXCollections.observableArrayList();

    //for showing username
    user us = user.getINSTANCE();
    String username = us.getUsername();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usernameShow.setText(username);
        /**
         * In order to not change the reservation number field, it is by default
         * set into "1" and then set it not editable so the user cannot type any
         * number inside the text field.
         */
        reserveSeatFld.setText("1");
        reserveSeatFld.setEditable(false);

        /**
         * To connect in the database I call the Connection and the DBConnector
         * which is a class so that you can get connection on the database.
         */
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

        /**
         * This will set the data according to the cell column created on the
         * table.
         */
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
        /**
         * To connect in the database I call the Connection and the DBConnector
         * which is a class so that you can get connection on the database.
         */
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

        /**
         * This will set the data according to the cell column created on the
         * table.
         */
        toColDestination.setCellValueFactory(new PropertyValueFactory<>("bus_source"));
        fromColDestination.setCellValueFactory(new PropertyValueFactory<>("bus_destination"));
        tblDestination.setItems(oblist_destination);
        //*****************************************************************************************

        /**
         * This set the text fields to be not editable.
         */
        busNumberFld.setEditable(false);
        busTimeFld.setEditable(false);
        busTypeFld.setEditable(false);
        busDestiFld.setEditable(false);
        busPriceFld.setEditable(false);
        availSeatFld.setEditable(false);
        //**********************************

        /**
         * adding item to the luggage combo box
         */
        seatLuggageCbox.getItems().addAll(
                "None",
                "Cartons",
                "Bags",
                "Suitcase"
        );

        /**
         * adding item to the discount combo box.
         */
        discountCbox.getItems().addAll(
                "None",
                "Student",
                "Senior Citizen",
                "PWD/Disable"
        );

        //usernameShow.setText("");
        showDate();
        showTime();
        setCellValueFromTableToField();
    }

    /**
     * This will erase all the inputted data inside the field.
     */
    private void clearFieldValue() {
        busNumberFld.setText("");
        busDestiFld.setText("");
        busTypeFld.setText("");
        busTimeFld.setText("");
        availSeatFld.setText("");
        busPriceFld.setText("");
        nameReserveFld.setText("");
    }

    /**
     * This method will allow to show the date. Create Date class and formatted
     * it according on how you want it to show. Then set it to the homeShowDate
     * label.
     */
    void showDate() {
        //SHOW DATE
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("MM-dd-yyyy");
        homeShowDate.setText(s.format(d));

    }

    /**
     * This method will allow to show the time.
     */
    void showTime() {
        //SHOW TIME
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            homeShowTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

    }

    /**
     * Show the price when you click on the value inside the Luggage combo box.
     */
    @FXML
    void handleSeatLuggageCbox(ActionEvent event) {

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

        //display in format
        NumberFormat formatter = new DecimalFormat("#0.00");
        String priceStr = String.valueOf(formatter.format(price));
        seatLuggageShow.setText(priceStr);
    }

    @FXML
    void handleOkBtn(ActionEvent event) {

        if (busNumberFld.getText().equals("") || busTimeFld.getText().equals("")
                || busTypeFld.getText().equals("") || busDestiFld.getText().equals("")
                || availSeatFld.getText().equals("") || nameReserveFld.getText().equals("")) {

            JOptionPane.showMessageDialog(null, "There are still empty fields!");
            System.out.println("Missing Fields");

        } else if (seatLuggageCbox.getSelectionModel().getSelectedItem().isEmpty()
                || discountCbox.getSelectionModel().getSelectedItem().isEmpty()) {

            JOptionPane.showMessageDialog(null, "Luggage/Discount are empty!");
            System.out.println("luggage or discount combobox is empty");
        } else {
            /**
             * This handle the seat reservation process where in you will
             * subtract the reserve seat number into the available number of
             * seat in the bus.
             */
            String bus_no = busNumberFld.getText();
            String username = nameReserveFld.getText();
            int reserve_seat_num = Integer.parseInt(reserveSeatFld.getText());
            int avail_seat_num = Integer.parseInt(availSeatFld.getText());
            int remaining_seat = avail_seat_num - reserve_seat_num;
            ResultSet rs;

            try {
                // get the connection from the database
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
                        System.out.println("Bus seat updated");
                        JOptionPane.showMessageDialog(null, "You have successfully reserve a seat " + nameReserveFld.getText() + ".");
                    } else {
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
            double bus_price = 0;
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

            /**
             * This show all the data in the text area as a e-pass form
             */
            receiptTxtArea.setText("-----------------------------E-PASS-----------------------------\n\n");
            receiptTxtArea.setText("                                     METROUTE\n\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Bus #: \t\t\t\t\t\t" + busNumberFld.getText() + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Bus Type: \t\t\t\t\t" + busTypeFld.getText() + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Boarding Time: \t\t\t\t" + busTimeFld.getText() + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Boarding Date: \t\t\t\t" + homeShowDate.getText() + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "From: \t\t\t\t\t\tPITX" + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "To: \t\t\t\t\t\t\t" + busDestiFld.getText() + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Seat Reserve: \t\t\t\t\t" + reserveSeatFld.getText() + " reservation for " + nameReserveFld.getText() + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Additional Service: \t\t\t\t" + seatLuggageCbox.getValue() + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Discount Type: \t\t\t\t" + discountCbox.getValue() + "\n");

            receiptTxtArea.setText(receiptTxtArea.getText() + "---------------------------------------------------------------\n\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Price: \t\t\t\t\t" + "Php. " + busPriceFld.getText() + ".00" + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Discounted Price: \t\t\t" + "Php. " + total_discount_str + "\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Price: \t\t\t\t\tPhp. " + seatLuggageShow.getText() + "\n\n");
            receiptTxtArea.setText(receiptTxtArea.getText() + "Total Price with Discount:\t Php. " + overall_price_discounted);
            receiptTxtArea.setText(receiptTxtArea.getText() + "\nTotal Price without Discount:  Php. " + overall_price_woutdisc);

            refreshBookTabTable();
            clearFieldValue();
            tabPane.getSelectionModel().select(epassTab);
        }

    }

    /**
     * This will fetch the data from the table into the text fields.
     */
    private void setCellValueFromTableToField() {
        tblBook.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                Schedule sched = tblBook.getItems().get(tblBook.getSelectionModel().getSelectedIndex());
                busNumberFld.setText(sched.getBus_no());
                busTimeFld.setText(sched.getBus_time());
                busTypeFld.setText(sched.getBus_type());
                busDestiFld.setText(sched.getBus_destination());
                busPriceFld.setText(sched.getBus_price());
                availSeatFld.setText(sched.getBus_seat());
            }
        });
    }
    
    /**
     * This will refresh the book tab table so that if you reserve
     * a seat you can see the changes in the remaining seats.
     */
    public void refreshBookTabTable() {

        oblist_schedule.clear();

        try {
            String admin_query = "select * from bus_details";
            PreparedStatement ps;
            Connection con = DBConnector.getConnection();
            ps = con.prepareStatement(admin_query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                oblist_schedule.add(new Schedule(rs.getString("bus_no"), rs.getString("bus_time"),
                        rs.getString("bus_destination"), rs.getString("bus_seat"),
                        rs.getString("bus_price"), rs.getString("bus_type")));
            }
            tblBook.setItems(oblist_schedule);
            ps.close();
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This will only change the scene.
     * @param event
     * @throws IOException 
     */
    @FXML
    void handleSignOutBtn(ActionEvent event) throws IOException {
        Parent changeToLogin = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene changeLoginScene = new Scene(changeToLogin);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeLoginScene);
        mainStage.show();

        JOptionPane.showMessageDialog(null, "Bon Voyage!");
    }

    @FXML
    void handleDiscountCbox(ActionEvent event) {

    }

}
