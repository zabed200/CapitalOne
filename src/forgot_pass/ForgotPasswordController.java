package forgot_pass;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import login.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {


    @FXML
    private TextField accountno;
    @FXML
    private ComboBox<String> questions;
    @FXML
    private TextField answer;

    ObservableList<String> list4 = FXCollections.observableArrayList(
            "What is your pet name?",
            "What is your childhood town?",
            "What is your nickname?");



    public void backToLogin(MouseEvent e) throws IOException {
        Main.stage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/login/sample.fxml")));

    }

    public void closeApp(MouseEvent e ){
        System.exit(0);
    }



    public void resetPassword(MouseEvent e) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);
            String sql = "SELECT * FROM userdata WHERE AccountNo=? and SecurityQuestion=? and Answer=?";
            ps = con.prepareStatement(sql);

            ps.setString(1,accountno.getText());
            ps.setString(2,questions.getValue());
            ps.setString(3,answer.getText());

            rs = ps.executeQuery();

            if(rs.next()){
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Password Recovery");
                a.setHeaderText("Below is your password");
                a.setContentText("Account No: "+rs.getString("AccountNo")+"\nPIN:   "+rs.getString("PIN"));
                a.showAndWait();
            }else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Error....");
                a.setHeaderText("Error Password Recovery");
                a.setContentText("Authentication Error. Check Credentials. Please try again later!  "+accountno.getText()+"    "+answer.getText());
                a.showAndWait();
            }


        }catch (Exception ee){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Error");
            a.setHeaderText("Error");
            a.setContentText(ee.getMessage()+" Please try again later!");
            a.showAndWait();

        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questions.setItems(list4);
    }
}
