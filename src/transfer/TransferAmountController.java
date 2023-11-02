package transfer;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import login.Controller;
import login.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class TransferAmountController implements Initializable {


    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;


    @FXML
    private Label account_no;
    @FXML
    private Label balance;
    @FXML
    private JFXTextField receiver_account_no;
    @FXML
    private JFXTextField amount_field;
    @FXML
    private JFXPasswordField pin_field;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Date d = new Date();
    String date = dateFormat.format(d);


    LocalTime localTime = LocalTime.now();
    DateTimeFormatter dt = DateTimeFormatter.ofPattern("hh:mm:ss a");
    String time = localTime.format(dt);



//==========================Set Data=================================

    public void setData() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1,Controller.acc);
            rs = ps.executeQuery();
            if(rs.next()){
                balance.setText(rs.getString("Balance"));
                account_no.setText(rs.getString("AccountNo"));
            }else {
                System.out.println("error");
            }
        }catch (Exception ee){
            System.out.println(ee.getMessage());
        }
    }

//==========================Search Button============================

    public void checkButton() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM userdata WHERE AccountNo=?";
            ps = con.prepareStatement(sql);
            ps.setString(1,receiver_account_no.getText());
            rs = ps.executeQuery();

            if(rs.next()){
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Information");
                a.setHeaderText("Receiver Account Info");
                a.setContentText("Account No. :   "+receiver_account_no.getText()+"\nName :   "+
                        rs.getString("Name")+"\nMobile No.:   "+
                        rs.getString("MobileNo"));
                a.showAndWait();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Something Wrong");
                a.setContentText("Kindly check if Receiver Account No. is correct.");
                a.showAndWait();
            }

        }catch (Exception ee){
            System.out.println(ee.getMessage());
        }
    }

//==========================Transfer Button==========================

    public void transferButton() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps = con.prepareStatement(sql);

            ps.setString(1,Controller.acc);
            ps.setString(2,pin_field.getText());
            rs = ps.executeQuery();

            if(rs.next()){
                int transferAmount = Integer.parseInt(amount_field.getText());
                int totalAmount = Integer.parseInt(balance.getText());

                if(transferAmount > totalAmount) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error");
                    a.setHeaderText("Error in transfer");
                    a.setContentText("Your balance is insufficient!");
                    a.showAndWait();
                } else {
                    int remainingAmount = totalAmount - transferAmount;
                    String sql1 = "UPDATE userdata SET Balance='"+remainingAmount+"' WHERE AccountNo='"+Controller.acc+"'";
                    ps = con.prepareStatement(sql1);
                    ps.execute();

//==================receiver account setup and deposit
                    String sql11 = "SELECT * FROM userdata WHERE AccountNo=?";
                    ps = con.prepareStatement(sql11);
                    ps.setString(1,receiver_account_no.getText());
                    rs = ps.executeQuery();
                    if(rs.next()){
                        int previousAmount = Integer.parseInt(rs.getString("Balance"));
                        int receiverAmount = previousAmount + transferAmount;
                        String sql111 = "UPDATE userdata SET Balance='"+receiverAmount+"' WHERE AccountNo='"+receiver_account_no.getText()+"'";
                        ps = con.prepareStatement(sql111);
                        ps.execute();

                        String sql112 = "INSERT INTO transfer(AccountNo, TransferAmount, SendTo, Date, Time) VALUES (?,?,?,?,?)";
                        ps = con.prepareStatement(sql112);
                        ps.setString(1,Controller.acc);
                        ps.setString(2,String.valueOf(transferAmount));
                        ps.setString(3,String.valueOf(receiver_account_no.getText()));
                        ps.setString(4,date);
                        ps.setString(5,time);

                        int i = ps.executeUpdate();
                        if(i>0){
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Success");
                            a.setHeaderText("Amount Transfer successful");
                            a.setContentText("Congratulations! \nAmount "+transferAmount+" has been transferred\nto" +
                                    "Account No. "+receiver_account_no.getText()+" successfully!" +
                                    "\nCurrent Balance: "+remainingAmount);
                            amount_field.setText("");
                            pin_field.setText("");
                            receiver_account_no.setText("");

                            balance.setText(String.valueOf(remainingAmount));
                            a.showAndWait();
                        }else {

                        }
                    }
                }
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error");
                a.setHeaderText("Something Wrong");
                a.setContentText("Kindly check your password and Receiver Account No.");
                a.showAndWait();
            }

        }catch (Exception ee){
            System.out.println(ee.getMessage());
        }
    }

//===================================================================


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }
}
