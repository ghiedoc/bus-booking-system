/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gillian
 */
public class MainFormController implements Initializable {

    @FXML
    private JFXComboBox<String> seatPositionCbox;

    @FXML
    private JFXComboBox<String> seatLuggageCbox;

    @FXML
    private JFXComboBox<String> nboundtravelToCbox;

    @FXML
    private JFXComboBox<String> nboundtravelFromCbox;

    @FXML
    private JFXTextField nboundNoOfTicketsTxtField;

    @FXML
    private Label nboundPriceShow;

    @FXML
    private Label seatLuggageShow;

    @FXML
    private Label usernameShow;

    @FXML
    private Label homeShowDate;

    @FXML
    private Label homeShowTime;

    @FXML
    private JFXComboBox<String> sboundtravelToCbox;

    @FXML
    private JFXComboBox<String> sboundtravelFromCbox;

    @FXML
    private JFXTextField sboundNoOfTicketsTxtField;

    @FXML
    private Label sboundPriceShow;

    @FXML
    private JFXComboBox<String> nbBusTypeCbox;

    @FXML
    private JFXComboBox<String> sbBusTypeCbox;

    @FXML
    private JFXComboBox<String> discountCbox;

    @FXML
    private TextArea receiptTxtArea;

    @FXML
    private JFXTextArea showReceiptTxtArea;

    @FXML
    private JFXButton signOutBtn;

    @FXML
    private JFXButton okBtn;

    @FXML
    private TableView<Schedule> tblScheduleTab;

    @FXML
    private TableColumn<Schedule, String> busNumColTab;

    @FXML
    private TableColumn<Schedule, String> departureColTab;

    @FXML
    private TableColumn<Schedule, String> destinationColTab;

    @FXML
    private TableColumn<Schedule, String> seatsColTab;

    @FXML
    private TableColumn<Schedule, String> priceColTab;

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

        //table at schedule tab
        busNumColTab.setCellValueFactory(new PropertyValueFactory<>("bus_no"));
        departureColTab.setCellValueFactory(new PropertyValueFactory<>("bus_time"));
        destinationColTab.setCellValueFactory(new PropertyValueFactory<>("bus_destination"));
        seatsColTab.setCellValueFactory(new PropertyValueFactory<>("bus_seat"));
        priceColTab.setCellValueFactory(new PropertyValueFactory<>("bus_price"));
        tblScheduleTab.setItems(oblist_schedule);

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

        //choose destination text field part
        setCellValueFromTableToField();
        busTimeFld.setEditable(false);
        busTypeFld.setEditable(false);
        busDestiFld.setEditable(false);
        busPriceFld.setEditable(false);
        //**********************************

        //cbox
        nboundtravelToCbox.setItems(destination);
        nboundtravelFromCbox.setItems(source);
        busSourceCBFillData();
        busDestinationCBFillData();
        busPriceLabelFillData();

        //adding item to position combo box
        seatPositionCbox.getItems().addAll(
                "Beside Window",
                "Middle",
                "Aisle Area",
                "Standing Area"
        );

        //adding item to luggage combo box
        seatLuggageCbox.getItems().addAll(
                "Cartons",
                "Bags",
                "Suitcase"
        );
        ;

        //discount cbox
        discountCbox.getItems().addAll(
                "None",
                "Student",
                "Senior Citizen",
                "PWD/Disable"
        );
        discountCbox.getSelectionModel().selectFirst();

        //adding item to bus_type
        nbBusTypeCbox.getItems().addAll("Aircon Bus", "Ordinary Bus");

        //SHOW DATE
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("MM-dd-yyyy");
        homeShowDate.setText(s.format(d));

        //SHOW TIME
//        new Timer(0, new ActionListener() {
//
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                Date d = new Date();
//                SimpleDateFormat s = new SimpleDateFormat("hh:mm a");
//                homeShowTime.setText(s.format(d));
//            }
//        }).start();

        usernameShow.setText("");
    }

    public void busSourceCBFillData() {

        try {
            Connection con = DBConnector.getConnection();
            Statement stat = con.createStatement();
            String selectQuery = "select bus_source from bus_details";

            ResultSet rs = stat.executeQuery(selectQuery);
            while (rs.next()) {
                source.add(rs.getString("bus_source"));
            }
            stat.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void busDestinationCBFillData() {

        try {
            Connection con = DBConnector.getConnection();
            Statement stat = con.createStatement();
            String selectQuery = "select bus_destination from bus_details";

            ResultSet rs = stat.executeQuery(selectQuery);
            while (rs.next()) {
                destination.add(rs.getString("bus_destination"));
            }
            stat.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void busPriceLabelFillData() {

        try {
            Connection con = DBConnector.getConnection();
            Statement stat = con.createStatement();
            String selectQuery = "select bus_price from bus_details";

            ResultSet rs = stat.executeQuery(selectQuery);
            while (rs.next()) {
                nboundPriceShow.setText(rs.getString("bus_price"));

            }
            stat.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }
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
        }

        //display
        NumberFormat formatter = new DecimalFormat("#0.00");
        String priceStr = String.valueOf(formatter.format(price));
        seatLuggageShow.setText(priceStr);
    }

    @FXML
    void handleOkBtn(ActionEvent event) {

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
            
        }else{
            System.out.println("no discount");
            bus_price = Double.parseDouble(busPriceFld.getText());
            System.out.println("price: " + bus_price);
        }
        
        //OVERALL PRICE
        double overall_price_discounted = total_discount + luggage_price;
        double overall_price_woutdisc = bus_price + luggage_price;

        receiptTxtArea.setText(receiptTxtArea.getText() + "                E-PASS                ");
        receiptTxtArea.setText("-------------------------TRAVEL DETAILS------------------------\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "To: \t\t\t\t\t\t" + busDestiFld.getText() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Bus Type: \t\t\t\t" + busTypeFld.getText() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Discount Type: \t\t\t" + discountCbox.getValue() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Price: \t\t\t\t\t" + "Php. " + busPriceFld.getText() + ".00" + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Discounted Price: \t\t\t" + "Php. " + total_discount_str + "\n\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "---------------------------SEAT DETAILS-------------------------\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Seat Position: \t\t\t\t" + seatPositionCbox.getValue() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Luggage Type: \t\t\t" + seatLuggageCbox.getValue() + "\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Price: \t\t\t\t\tPhp. " + seatLuggageShow.getText() + "\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Total Price with Discount: Php. " + overall_price_discounted);
        receiptTxtArea.setText(receiptTxtArea.getText() + "\nTotal Price without Discount: Php. " + overall_price_woutdisc);

    }

    private void setCellValueFromTableToField() {
        tblBook.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                Schedule sched = tblBook.getItems().get(tblBook.getSelectionModel().getSelectedIndex());
                busTimeFld.setText(sched.getBus_time());
                busTypeFld.setText(sched.getBus_type());
                busDestiFld.setText(sched.getBus_destination());
                busPriceFld.setText(sched.getBus_price());
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
    void handleNboundTravelFromCbox(ActionEvent event) {

    }

    @FXML
    void handlleNboundTravelToCbox(ActionEvent event) {

    }

    @FXML
    void handleSboundTravelToCbox(ActionEvent event) {

    }

    @FXML
    void handleDiscountCbox(ActionEvent event) {

    }

}
