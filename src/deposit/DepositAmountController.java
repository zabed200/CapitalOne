package deposit;

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

public class DepositAmountController implements Initializable {


    @FXML
    private Label account_no;
    @FXML
    private Label balance;
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



    public void setData() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

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




    public void depositButton() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps = con.prepareStatement(sql);

            ps.setString(1,Controller.acc);
            ps.setString(2,pin_field.getText());
            rs = ps.executeQuery();

            if(rs.next()){
                int depositAmount = Integer.parseInt(amount_field.getText());
                int totalAmount = Integer.parseInt(balance.getText());


                    int remainingAmount = totalAmount + depositAmount;
                    String sql1 = "UPDATE userdata SET Balance='"+remainingAmount+"' WHERE AccountNo='"+Controller.acc+"'";
                    ps = con.prepareStatement(sql1);
                    ps.execute();

                    String sql2 = "INSERT INTO deposit(AccountNo, DepositAmount, RemainingAmount, Date, Time) VALUES (?,?,?,?,?)";
                    ps = con.prepareStatement(sql2);
                    ps.setString(1,Controller.acc);
                    ps.setString(2,String.valueOf(depositAmount));
                    ps.setString(3,String.valueOf(remainingAmount));
                    ps.setString(4,date);
                    ps.setString(5,time);

                    int i = ps.executeUpdate();
                    if(i>0){
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Success");
                        a.setHeaderText("Amount deposit successful");
                        a.setContentText("Congratulations! \nAmount "+depositAmount+" has been deposited successfully!" +
                                "\nCurrent Balance: "+remainingAmount);
                        amount_field.setText("");
                        pin_field.setText("");
                        balance.setText(String.valueOf(remainingAmount));

                        a.showAndWait();
                    }else {

                    }



            }else {
                System.out.println("error");
            }


        }catch (Exception ee){
            System.out.println(ee.getMessage());
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }
}
