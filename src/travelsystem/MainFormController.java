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
import java.awt.event.MouseEvent;
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
import javax.swing.table.TableModel;

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
            
            while(rs.next()){
                oblist_schedule.add(new Schedule(rs.getString("bus_no"), rs.getString("bus_time"),
                        rs.getString("bus_destination"), rs.getString("bus_seat"),
                        rs.getString("bus_price")));
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
        ////////////////////////////table_schedule////////////////////////////
        
        ////////////////////////////table_destination////////////////////////////
         try {
            Connection con = DBConnector.getConnection();
            
            ResultSet rs = con.createStatement().executeQuery("select * from bus_details");
            
            while(rs.next()){
                oblist_destination.add(new Destination(rs.getString("bus_source"), rs.getString("bus_destination")));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        toColDestination.setCellValueFactory(new PropertyValueFactory<>("bus_source"));
        fromColDestination.setCellValueFactory(new PropertyValueFactory<>("bus_destination"));
        tblDestination.setItems(oblist_destination); 
        ////////////////////////////table_destination////////////////////////////
        
        
        //cbox
        nboundtravelToCbox.setItems(destination);
        nboundtravelFromCbox.setItems(source);
        busSourceCBFillData();
        busDestinationCBFillData();
        busPriceLabelFillData();
//        north bound
//        adding item to "to" combo box
//        nboundtravelToCbox.getItems().addAll(
//                "Coastal",
//                "Airport Rd",
//                "Heritage",
//                "Novaliches Bayan",
//                "Sapang Palay",
//                "Navotas",
//                "Grotto",
//                "Monumento",
//                "Malanday",
//                "NAIA 1,2,3,4",
//                "Ortigas/Robinson's Galeria",
//                "Makati: Landmark/Circuit",
//                "Talaba/St. Dominic",
//                "Niog/Panapaan",
//                "SM Bacoor/Rotonda",
//                "Imus BDO",
//                "Salawag",
//                "SM Pala-Pala",
//                "Manggahan",
//                "Indang",
//                "Tagaytay",
//                "Trece Martirez",
//                "Balayan",
//                "Calatagan",
//                "Nasugbu",
//                "Alfonso",
//                "Naic"
//        );
//
//        //adding item to "from" combo box
//        nboundtravelFromCbox.getItems().addAll(
//                "PITX Terminal"
//        );
//        nboundtravelFromCbox.getSelectionModel().selectFirst();

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
                "Student",
                "Senior Citizen",
                "PWD/Disable"
        );

        //southbound
        //adding item to "to" combo box
//        sboundtravelToCbox.getItems().addAll(
//                "Talaba/St. Dominic",
//                "Niog/Panapaan",
//                "SM Bacoor/Rotonda",
//                "Imus BDO",
//                "Salawag",
//                "SM Pala-Pala",
//                "Manggahan",
//                "Indang",
//                "Tagaytay",
//                "Trece Martirez",
//                "Balayan",
//                "Calatagan",
//                "Nasugbu",
//                "Alfonso",
//                "Naic"
//        );
//
//        //adding item to "from" combo box
//        sboundtravelFromCbox.getItems().addAll(
//                "PITX Terminal"
//        );
//        sboundtravelFromCbox.getSelectionModel().selectFirst();
        nbBusTypeCbox.getItems().addAll("Aircon Bus", "Ordinary Bus");

//        //SHOW DATE
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
    }

    public void busSourceCBFillData() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String databaseURL = "jdbc:mysql://localhost:3306/bussystem";
            Connection con = (Connection) DriverManager.getConnection(databaseURL, "root", "");

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
            Class.forName("com.mysql.jdbc.Driver");
            String databaseURL = "jdbc:mysql://localhost:3306/bussystem";
            Connection con = (Connection) DriverManager.getConnection(databaseURL, "root", "");

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
            Class.forName("com.mysql.jdbc.Driver");
            String databaseURL = "jdbc:mysql://localhost:3306/bussystem";
            Connection con = (Connection) DriverManager.getConnection(databaseURL, "root", "");

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
    void handlleNboundTravelToCbox(ActionEvent event) {

//        double price = 0;
//        String destinationSelected = nboundtravelToCbox.getValue();
//        boolean nbAirconBusSelected = nbBusTypeCbox.getValue().equals("Aircon Bus");
//        boolean nbOrdinaryBusSelected = nbBusTypeCbox.getValue().equals("Ordinary Bus");
//
//        //price
//        if (destinationSelected.equals("Coastal")) {
//            price = 25;
//        } else if (destinationSelected.equals("Airport Rd")) {
//            price = 25;
//        } else if (destinationSelected.equals("Heritage")) {
//            price = 25;
//        } else if (destinationSelected.equals("Novaliches Bayan")) {
//            if (nbAirconBusSelected) {
//                price = 70;
//            } else if (nbOrdinaryBusSelected) {
//                price = 60;
//            }
//        } else if (destinationSelected.equals("Sapang Palay")) {
//            if (nbAirconBusSelected) {
//                price = 115;
//            } else if (nbOrdinaryBusSelected) {
//                price = 95;
//            }
//        } else if (destinationSelected.equals("Navotas")) {
//            if (nbAirconBusSelected) {
//                price = 40;
//            } else if (nbOrdinaryBusSelected) {
//                price = 32;
//            } else {
//                JOptionPane.showMessageDialog(null, "Invalid");
//            }
//        } else if (destinationSelected.equals("Grotto")) {
//            if (nbAirconBusSelected) {
//                price = 115;
//            } else if (nbOrdinaryBusSelected) {
//                price = 95;
//            }
//        } else if (destinationSelected.equals("Monumento")) {
//            if (nbAirconBusSelected) {
//                price = 40;
//            } else if (nbOrdinaryBusSelected) {
//                price = 32;
//            }
//        } else if (destinationSelected.equals("Malanday")) {
//            if (nbAirconBusSelected) {
//                price = 115;
//            } else if (nbOrdinaryBusSelected) {
//                price = 95;
//            }
//        } else if (destinationSelected.equals("NAIA 1,2,3,4")) {
//            price = 300;
//        } else if (destinationSelected.equals("Ortigas/Robinson's Galeria")) {
//            price = 80;
//        } else if (destinationSelected.equals("Makati: Landmark/Circuit")) {
//            price = 80;
//        } //south
//        else if (destinationSelected.equals("Talaba/St. Dominic")) {
//            if (nbAirconBusSelected) {
//                price = 25;
//            } else if (nbOrdinaryBusSelected) {
//                price = 17;
//            }
//        } else if (destinationSelected.equals("Niog/Panapaan")) {
//            price = 25;
//        } else if (destinationSelected.equals("SM Bacoor/Rotonda")) {
//            price = 27;
//        } else if (destinationSelected.equals("Imus BDO")) {
//            price = 30;
//        } else if (destinationSelected.equals("Salawag")) {
//            price = 35;
//        } else if (destinationSelected.equals("SM Pala-Pala")) {
//            price = 40;
//        } else if (destinationSelected.equals("Manggahan")) {
//            price = 50;
//        } else if (destinationSelected.equals("Indang")) {
//            if (nbAirconBusSelected) {
//                price = 75;
//            } else if (nbOrdinaryBusSelected) {
//                price = 65;
//            }
//        } else if (destinationSelected.equals("Tagaytay")) {
//            if (nbAirconBusSelected) {
//                price = 95;
//            } else if (nbOrdinaryBusSelected) {
//                price = 85;
//            }
//        } else if (destinationSelected.equals("Trece Martirez")) {
//            if (nbAirconBusSelected) {
//                price = 55;
//            } else if (nbOrdinaryBusSelected) {
//                price = 50;
//            }
//        } else if (destinationSelected.equals("Balayan")) {
//            if (nbAirconBusSelected) {
//                price = 190;
//            } else if (nbOrdinaryBusSelected) {
//                price = 175;
//            }
//        } else if (destinationSelected.equals("Calatagan")) {
//            if (nbAirconBusSelected) {
//                price = 210;
//            } else if (nbOrdinaryBusSelected) {
//                price = 195;
//            }
//        } else if (destinationSelected.equals("Nasugbu")) {
//            if (nbAirconBusSelected) {
//                price = 175;
//            } else if (nbOrdinaryBusSelected) {
//                price = 165;
//            }
//        } else if (destinationSelected.equals("Alfonso")) {
//            if (nbAirconBusSelected) {
//                price = 104;
//            } else if (nbOrdinaryBusSelected) {
//                price = 90;
//            }
//        } else if (destinationSelected.equals("Naic")) {
//            if (nbAirconBusSelected) {
//                price = 56;
//            } else if (nbOrdinaryBusSelected) {
//                price = 52;
//            }
//        }
//
//        //display
//        NumberFormat formatter = new DecimalFormat("#0.00");
//        String priceStr = String.valueOf(formatter.format(price));
//        nboundPriceShow.setText(priceStr);
    }

    @FXML
    void handleSboundTravelToCbox(ActionEvent event) {
//        double price = 0;
//        String destinationSelected = sboundtravelToCbox.getValue();
//        boolean sbAirconBusSelected = sbBusTypeCbox.getValue().equals("Aircon Bus");
//        boolean sbOrdinaryBusSelected = sbBusTypeCbox.getValue().equals("Ordinary Bus");
//
//        //price
//        if (destinationSelected.equals("Talaba/St. Dominic")) {
//            if (sbAirconBusSelected) {
//                price = 25;
//            } else if (sbOrdinaryBusSelected) {
//                price = 17;
//            }
//        } else if (destinationSelected.equals("Niog/Panapaan")) {
//            price = 25;
//        } else if (destinationSelected.equals("SM Bacoor/Rotonda")) {
//            price = 27;
//        } else if (destinationSelected.equals("Imus BDO")) {
//            price = 30;
//        } else if (destinationSelected.equals("Salawag")) {
//            price = 35;
//        } else if (destinationSelected.equals("SM Pala-Pala")) {
//            price = 40;
//        } else if (destinationSelected.equals("Manggahan")) {
//            price = 50;
//        } else if (destinationSelected.equals("Indang")) {
//            if (sbAirconBusSelected) {
//                price = 75;
//            } else if (sbOrdinaryBusSelected) {
//                price = 65;
//            }
//        } else if (destinationSelected.equals("Tagaytay")) {
//            if (sbAirconBusSelected) {
//                price = 95;
//            } else if (sbOrdinaryBusSelected) {
//                price = 85;
//            }
//        } else if (destinationSelected.equals("Trece Martirez")) {
//            if (sbAirconBusSelected) {
//                price = 55;
//            } else if (sbOrdinaryBusSelected) {
//                price = 50;
//            }
//        } else if (destinationSelected.equals("Balayan")) {
//            if (sbAirconBusSelected) {
//                price = 190;
//            } else if (sbOrdinaryBusSelected) {
//                price = 175;
//            }
//        } else if (destinationSelected.equals("Calatagan")) {
//            if (sbAirconBusSelected) {
//                price = 210;
//            } else if (sbOrdinaryBusSelected) {
//                price = 195;
//            }
//        } else if (destinationSelected.equals("Nasugbu")) {
//            if (sbAirconBusSelected) {
//                price = 175;
//            } else if (sbOrdinaryBusSelected) {
//                price = 165;
//            }
//        } else if (destinationSelected.equals("Alfonso")) {
//            if (sbAirconBusSelected) {
//                price = 104;
//            } else if (sbOrdinaryBusSelected) {
//                price = 90;
//            }
//        } else if (destinationSelected.equals("Naic")) {
//            if (sbAirconBusSelected) {
//                price = 56;
//            } else if (sbOrdinaryBusSelected) {
//                price = 52;
//            }
//        }
//
//        //display
//        NumberFormat formatter = new DecimalFormat("#0.00");
//        String priceStr = String.valueOf(formatter.format(price));
//        sboundPriceShow.setText("Php. " + priceStr);

    }

    @FXML
    void handleDiscountCbox(ActionEvent event) {

    }

    @FXML
    void handleSeatLuggageCbox(ActionEvent event) {
        //SHOW DETAILS ABOUT THE PRICE OFD THE LUGGAGE
        double price = 0;
        String luggage = seatLuggageCbox.getValue();

        if (luggage.equalsIgnoreCase("Cartons")) {
            price = 80;
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

        //Text Area
        String nbTravelTo = nboundtravelToCbox.getValue();
        String sbTravelTo = sboundtravelToCbox.getValue();

        receiptTxtArea.setText(receiptTxtArea.getText() + "                E-PASS                ");
        receiptTxtArea.setText("-------------------------TRAVEL DETAILS------------------------\n\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "To: \t\t\t\t\t\t" + nbTravelTo + "\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "From: \t\t\t\t\t" + nboundtravelFromCbox.getValue() + "\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Bus Type: \t\t\t\t" + nbBusTypeCbox.getValue() + "\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Discount Type: \t\t\t" + discountCbox.getValue() + "\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Price: \t\t\t\t\t" + nboundPriceShow.getText() + "\n\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "---------------------------SEAT DETAILS-------------------------\n\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Seat Position: \t\t\t\t" + seatPositionCbox.getValue() + "\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Luggage Type: \t\t\t" + seatLuggageCbox.getValue() + "\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "Price: \t\t\t\t\tPhp. " + seatLuggageShow.getText() + "\n\n");
        receiptTxtArea.setText(receiptTxtArea.getText() + "\nTotal Price: ");

        //SEAT PREFERENCES GETTING DATAS
//        String seat = seatPositionCbox.getValue();
//        String standing = standingTypeCbox.getValue();
//        String luggage = seatLuggageCbox.getValue();
//        String luggagePrice = seatLuggageShow.getText();
//
//        try {
//            File file = new File("C:\\Users\\Gillian\\Documents\\NetBeansProjects\\TravelSystem\\src\\database\\seat_details.txt");
//            FileWriter fw = new FileWriter(file, true);
//            BufferedWriter bw = new BufferedWriter(fw);
//
//            bw.append(seat + ",");
//            bw.append(standing + ",");
//            bw.append(luggage + ",");
//            bw.append(luggagePrice + ",");
//            bw.newLine();
//
//            bw.close();
//            fw.close();
//
//            System.out.println("seat details added!");
//            JOptionPane.showMessageDialog(null, "Successfully Added!");
//
//        } catch (Exception e) {
//            System.out.println("error");
//        }
//        //TRAVEL DETAILS
//        //NORTHBOUND
//        double price = 0;
//        String destinationSelected = nboundtravelToCbox.getValue();
//        boolean nbAirconBusSelected = nbBusTypeCbox.getValue().equals("Aircon Bus");  
//        boolean nbOrdinaryBusSelected = nbBusTypeCbox.getValue().equals("Ordinary Bus");
//
//        //NORTHBOUND
//        //price
//        if(destinationSelected.equals("Coastal")){
//            price = 20;
//        }else if(destinationSelected.equals("Airport Rd")){
//            price = 20;
//        }else if(destinationSelected.equals("Heritage")){
//            price = 20;
//        }else if(destinationSelected.equals("Novaliches Bayan")){
//            if(nbAirconBusSelected){
//                price = 70;
//            }else if(nbOrdinaryBusSelected){
//                price= 60;
//            }else{
//                JOptionPane.showMessageDialog(null, "Invalid");
//            }          
//        }else if(destinationSelected.equals("Sapang Palay")){
//            if(nbAirconBusSelected){
//                price = 115;
//            }else if(nbOrdinaryBusSelected){
//                price = 95;
//            }else{
//                JOptionPane.showMessageDialog(null, "Invalid");
//            }
//        }else if(destinationSelected.equals("Navotas")){
//            if(nbAirconBusSelected){
//                price = 40;
//            }else if(nbOrdinaryBusSelected){
//                price = 32;
//            }else{
//                JOptionPane.showMessageDialog(null, "Invalid");
//            }
//        }else if(destinationSelected.equals("Grotto")){
//            if(nbAirconBusSelected){
//                price = 115;
//            }else if(nbOrdinaryBusSelected){
//                price = 95;
//            }else{
//                JOptionPane.showMessageDialog(null, "Invalid");
//            }
//        }else if(destinationSelected.equals("Monumento")){
//            if(nbAirconBusSelected){
//                price = 40;
//            }else if(nbOrdinaryBusSelected){
//                price = 32;
//            }else{
//                JOptionPane.showMessageDialog(null, "Invalid");
//            }
//        }else if(destinationSelected.equals("Malanday")){
//            if(nbAirconBusSelected){
//                price = 115;
//            }else if(nbOrdinaryBusSelected){
//                price = 95;
//            }else{
//                JOptionPane.showMessageDialog(null, "Invalid");
//            }
//        }else if(destinationSelected.equals("NAIA 1,2,3,4")){
//            price = 300;
//        }else if(destinationSelected.equals("Ortigas/Robinson's Galeria")){
//            price = 80;
//        }else if(destinationSelected.equals("Makati: Landmark/Circuit")){
//            price = 80;
//        }
//        
//        //NORTHBOUND
//        int ticketsNo = Integer.parseInt(nboundNoOfTicketsTxtField.getText());
//        double totalPrice = price * ticketsNo;
//        
//        //convert to String
//        String totalPriceStr = String.valueOf(totalPrice);
//        System.out.println("");
//        
//        //SOUTHBOUND
//        //DISCOUNT TYPE PROCESS
//        double price = 1;
//        double discount = price * .20;
//        String discountType = discountCbox.getValue();
//        
//        if(discountType.equalsIgnoreCase("Student")){
//            System.out.println(discount);
//        }else if(discountType.equalsIgnoreCase("Senior Citizen")){
//            System.out.println(discount);
//        }else if(discountType.equalsIgnoreCase("PWD/Disable")){
//            System.out.println(discount);
//        }
    }
    
    @FXML
    void tblRowClick(MouseEvent event) {
        //int row = tblBook.getRowFactory();
    }

    @FXML
    void handleNboundTravelFromCbox(ActionEvent event) {

    }

    @FXML
    void handleSignOutBtn(ActionEvent event) throws IOException {
        Parent changeToLogin = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene changeLoginScene = new Scene(changeToLogin);
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        mainStage.setScene(changeLoginScene);
        mainStage.show();
    }

}
