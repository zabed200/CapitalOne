package change_pin;

import com.jfoenix.controls.JFXPasswordField;
import dashboard.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import login.Controller;
import login.Main;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ChangePinController implements Initializable {


    DashboardController dashboardController = new DashboardController();

    @FXML
    public Label account_no;

    @FXML
    public JFXPasswordField old_pin;
    @FXML
    public JFXPasswordField new_pin;
    @FXML
    public JFXPasswordField confirm_pin;


//=====================Set Data=============================

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
                account_no.setText(rs.getString("AccountNo"));
            }else {
                System.out.println("error");
            }
        }catch (Exception ee){
            System.out.println(ee.getMessage());
        }
    }


//========================Change PIN Button===========================

    public void changePinButton(MouseEvent mouseEvent) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps = con.prepareStatement(sql);

            ps.setString(1,Controller.acc);
            ps.setString(2,old_pin.getText());
            rs = ps.executeQuery();

            if(rs.next()){
                if(new_pin.getText().equals(confirm_pin.getText())) {
                    String sql1 = "UPDATE userdata SET PIN='" + new_pin.getText() + "' WHERE AccountNo='" + Controller.acc + "'";
                    ps = con.prepareStatement(sql1);
                    ps.execute();

                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Success");
                    a.setHeaderText("PIN Change Successful!");
                    a.setContentText("Congratulations!\nYour PIN has been changed successfully.\n" +
                            "You will now be redirected to the Login Screen.");
                    a.showAndWait();
                    old_pin.setText("");
                    new_pin.setText("");
                    confirm_pin.setText("");
                    dashboardController.logout(mouseEvent);
                }
            }else {
                System.out.println("error");
            }


        }catch (Exception ee){
            System.out.println(ee.getMessage());
        }
    }

//=================================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }
}
