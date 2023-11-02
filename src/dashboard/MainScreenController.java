package dashboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import login.Controller;
import login.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private Pane dashboard_main;


    @FXML
    private Label name;
    @FXML
    private Label body;



    public void setInfo() {
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
//                System.out.println(rs.getString("Name"));
                name.setText(rs.getString("Name"));

            }else {
                System.out.println("error");
            }


        }catch (Exception ee){
            System.out.println(ee.getMessage());
        }
    }


    @FXML
    public void transferAmount(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/transfer/TransferAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().setAll(fxml);
    }
    @FXML
    public void transactionHistory(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/transaction_history/TransactionHistory.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().setAll(fxml);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        body.setText("Captital One is the consumer division of financial services multinational Capital One Inc.\n" +
                "Capital One was founded in 1812. The Company has 2,649 branches in 19 countries,\n" +
                "including 723 branches in the United States and 1,494 branches in Mexico operated \n" +
                "by its subsidiary Banamex. The U.S. branches are concentrated in six metropolitan \n" +
                "areas: New York City, Chicago, Los Angeles, San Francisco, Washington, D.C.,\n" +
                "and Miami. Aside from the U.S. and Mexico, most of the company's branches are \n" +
                "in Poland, Russia and the United Arab Emirates.\n" +
                "\n");

        setInfo();
    }
}
