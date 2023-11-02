package transaction_history;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import login.Controller;
import login.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class TransactionHistoryController implements Initializable {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


//=================Withdraw===================
    @FXML
    private TableView<History> withdraw_table;
    @FXML
    private TableColumn<History, String> withdraw_account_no;
    @FXML
    private TableColumn<History, String> withdraw_amount;
    @FXML
    private TableColumn<History, String> withdraw_remaining_amount;
    @FXML
    private TableColumn<History, String> withdraw_date;
    @FXML
    private TableColumn<History, String> withdraw_time;

//===============Deposit=====================
    @FXML
    private TableView<History> deposit_table;
    @FXML
    private TableColumn<History, String> deposit_account_no;
    @FXML
    private TableColumn<History, String> deposit_amount;
    @FXML
    private TableColumn<History, String> deposit_remaining_amount;
    @FXML
    private TableColumn<History, String> deposit_date;
    @FXML
    private TableColumn<History, String> deposit_time;

//===============Transfer=====================
    @FXML
    private TableView<History> transfer_table;
    @FXML
    private TableColumn<History, String> transfer_account_no;
    @FXML
    private TableColumn<History, String> transfer_amount;
    @FXML
    private TableColumn<History, String> transfer_sent_to;
    @FXML
    private TableColumn<History, String> transfer_date;
    @FXML
    private TableColumn<History, String> transfer_time;
//===============Transfer=====================
    @FXML
    private TableView<History> receive_table;
    @FXML
    private TableColumn<History, String> receive_account_no;
    @FXML
    private TableColumn<History, String> receive_amount;
    @FXML
    private TableColumn<History, String> receive_received_from;
    @FXML
    private TableColumn<History, String> receive_date;
    @FXML
    private TableColumn<History, String> receive_time;
//============================================


    public void withdraw(){
        withdraw_account_no.setCellValueFactory(new PropertyValueFactory<History, String>("name"));
        withdraw_amount.setCellValueFactory(new PropertyValueFactory<History, String>("amount"));
        withdraw_remaining_amount.setCellValueFactory(new PropertyValueFactory<History, String>("generic"));
        withdraw_date.setCellValueFactory(new PropertyValueFactory<History, String>("date"));
        withdraw_time.setCellValueFactory(new PropertyValueFactory<History, String>("time"));

        ObservableList<History> list = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM withdraw WHERE AccountNo=?";
            ps = con.prepareStatement(sql);

            ps.setString(1, Controller.acc);
            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new History(rs.getString("AccountNo"),rs.getString("WithdrawAmount"),rs.getString("RemainingAmount"),rs.getString("Date"),rs.getString("Time")));

            }

        }catch (Exception ee){
            System.out.println(ee.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Something Wrong");
            a.setContentText("Error while fetching data. Please Try Again!");
            a.showAndWait();
        }

        withdraw_table.setItems(list);

    }

    public void deposit(){
        deposit_account_no.setCellValueFactory(new PropertyValueFactory<History, String>("name"));
        deposit_amount.setCellValueFactory(new PropertyValueFactory<History, String>("amount"));
        deposit_remaining_amount.setCellValueFactory(new PropertyValueFactory<History, String>("generic"));
        deposit_date.setCellValueFactory(new PropertyValueFactory<History, String>("date"));
        deposit_time.setCellValueFactory(new PropertyValueFactory<History, String>("time"));

        ObservableList<History> list = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM deposit WHERE AccountNo=?";
            ps = con.prepareStatement(sql);

            ps.setString(1, Controller.acc);
            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new History(rs.getString("AccountNo"),rs.getString("DepositAmount"),rs.getString("RemainingAmount"),rs.getString("Date"),rs.getString("Time")));

            }

        }catch (Exception ee){
            System.out.println(ee.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Something Wrong");
            a.setContentText("Error while fetching data. Please Try Again!");
            a.showAndWait();
        }

        deposit_table.setItems(list);

    }

    public void transfer(){
        transfer_account_no.setCellValueFactory(new PropertyValueFactory<History, String>("name"));
        transfer_amount.setCellValueFactory(new PropertyValueFactory<History, String>("amount"));
        transfer_sent_to.setCellValueFactory(new PropertyValueFactory<History, String>("generic"));
        transfer_date.setCellValueFactory(new PropertyValueFactory<History, String>("date"));
        transfer_time.setCellValueFactory(new PropertyValueFactory<History, String>("time"));

        ObservableList<History> list = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM transfer WHERE AccountNo=?";
            ps = con.prepareStatement(sql);

            ps.setString(1, Controller.acc);
            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new History(rs.getString("AccountNo"),rs.getString("TransferAmount"),rs.getString("SendTo"),rs.getString("Date"),rs.getString("Time")));

            }

        }catch (Exception ee){
            System.out.println(ee.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Something Wrong");
            a.setContentText("Error while fetching data. Please Try Again!");
            a.showAndWait();
        }

        transfer_table.setItems(list);

    }

    public void receive(){
        receive_account_no.setCellValueFactory(new PropertyValueFactory<History, String>("name"));
        receive_amount.setCellValueFactory(new PropertyValueFactory<History, String>("amount"));
        receive_received_from.setCellValueFactory(new PropertyValueFactory<History, String>("generic"));
        receive_date.setCellValueFactory(new PropertyValueFactory<History, String>("date"));
        receive_time.setCellValueFactory(new PropertyValueFactory<History, String>("time"));

        ObservableList<History> list = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM transfer WHERE SendTo=?";
            ps = con.prepareStatement(sql);

            ps.setString(1, Controller.acc);
            rs = ps.executeQuery();

            while(rs.next()) {
                list.add(new History(rs.getString("SendTo"),rs.getString("TransferAmount"),rs.getString("AccountNo"),rs.getString("Date"),rs.getString("Time")));

            }

        }catch (Exception ee){
            System.out.println(ee.getMessage());
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Something Wrong");
            a.setContentText("Error while fetching data. Please Try Again!");
            a.showAndWait();
        }

        receive_table.setItems(list);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        withdraw();
        deposit();
        transfer();
        receive();
    }
}
