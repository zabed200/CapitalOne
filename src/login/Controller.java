package login;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller implements Initializable
{

    public static Stage stage = null;
    public static String acc;

    @FXML
    private Pane main_area;
    @FXML
    private TextField accountno;
    @FXML
    private PasswordField pin;





    @FXML
    private void closeApp(MouseEvent e) {
        System.exit(0);
    }

    @FXML
    private void createAccount(MouseEvent e) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/create_account/CreateAccount.fxml"));
        main_area.getChildren().removeAll();
        main_area.getChildren().addAll(fxml);
    }
    @FXML
    private void forgotPassword(MouseEvent e) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/forgot_pass/ForgotPassword.fxml"));
        main_area.getChildren().removeAll();
        main_area.getChildren().setAll(fxml);
    }



    public void loginAccount(MouseEvent e) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and PIN=?";
            ps = con.prepareStatement(sql);

            ps.setString(1,accountno.getText());
            ps.setString(2,pin.getText());
            acc = accountno.getText();
//            JOptionPane.showMessageDialog(null,acc);

            rs = ps.executeQuery();

            if(rs.next()){
                ((Node)e.getSource()).getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/dashboard/Dashboard.fxml"));
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                this.stage = stage;

//                Alert a = new Alert(Alert.AlertType.INFORMATION);
//                a.setTitle("Success");
//                a.setHeaderText("Login Successful");
//                a.setContentText("Your are logged in successfully. You will now be redirected to dashboard");
//                a.showAndWait();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error....");
                a.setHeaderText("Error login");
                a.setContentText("Your account username or password is wrong. Please try again!  "+accountno.getText()+"    "+pin.getText());
                a.showAndWait();
            }


        }catch (Exception ee){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Error");
            a.setHeaderText("Error while login");
            a.setContentText(ee.getMessage()+"Please try again later!");
            a.showAndWait();

        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
