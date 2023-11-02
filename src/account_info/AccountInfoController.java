package account_info;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import login.Controller;
import login.Main;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AccountInfoController implements Initializable {


    @FXML
    public AnchorPane account_info;
    @FXML
    private Text account_no;
    @FXML
    private Text gender;
    @FXML
    private Text account_type;
    @FXML
    private Label balance;





    @FXML
    public void withdrawAmount(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/withdraw/WithdrawAmount.fxml"));
        account_info.getChildren().removeAll();
        account_info.getChildren().setAll(fxml);
    }
    @FXML
    public void depositAmount(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/deposit/DepositAmount.fxml"));
        account_info.getChildren().removeAll();
        account_info.getChildren().setAll(fxml);
    }


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
                gender.setText(rs.getString("Gender"));
                account_type.setText(rs.getString("AccountType"));



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
