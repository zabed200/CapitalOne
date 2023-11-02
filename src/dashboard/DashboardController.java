package dashboard;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import login.Controller;
import javafx.scene.image.Image;
import login.Main;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Pane dashboard_main;

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Label name;
    @FXML
    private Circle profilepic;



    @FXML
    private FontAwesomeIconView ico;

    @FXML
    private void closeApp(MouseEvent e) {
        System.exit(0);
    }

    @FXML
    private void minimizeApp(MouseEvent e) {
        Stage stage = (Stage) ico.getScene().getWindow();
        stage.setIconified(true);
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
                name.setText(rs.getString("Name"));
                InputStream is = rs.getBinaryStream("ProfilePic");
                OutputStream os = new FileOutputStream(new File("pic.jpg"));
                byte[] content = new byte[1024];
                int size = 0;
                while((size = is.read(content)) != -1) {
                    os.write(content , 0 ,size);
                }
                os.close();
                is.close();
                Image img = new Image("file:pic.jpg",false);
                profilepic.setFill(new ImagePattern(img));


            }else {
                System.out.println("error");
            }


        }catch (Exception ee){
            System.out.println(ee.getMessage());
        }
    }

    @FXML
    public void Click(MouseEvent mouseEvent){
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();
    }
    @FXML
    public void drag(MouseEvent mouseEvent){
        Controller.stage.setX(mouseEvent.getScreenX() - xOffset);
        Controller.stage.setY(mouseEvent.getScreenY() - yOffset);
    }
    @FXML
    public void accountInfo(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/account_info/AccountInfo.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().setAll(fxml);
    }
    @FXML
    public void mainScreen() throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/dashboard/MainScreen.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().addAll(fxml);
    }
    @FXML
    public void withdrawAmount(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/withdraw/WithdrawAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().setAll(fxml);
    }
    @FXML
    public void depositAmount(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/deposit/DepositAmount.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().setAll(fxml);
    }
    @FXML
    public void changePin(MouseEvent mouseEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/change_pin/ChangePin.fxml"));
        dashboard_main.getChildren().removeAll();
        dashboard_main.getChildren().setAll(fxml);
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




//    Logout   =============================================================

    public void logout(MouseEvent mouseEvent) throws IOException {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/login/sample.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();


        //===========================Drag and Move Window=========================

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
        //============================================================================

    }

//==========================================================================





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
        try {
            mainScreen();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Main.stage.close();

//Initial dashboard screen


    }
}
