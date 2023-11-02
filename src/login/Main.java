package login;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;


import java.awt.*;


public class Main extends Application {

    public static Stage stage = null;
//    public static String DATABASE_URI = "jdbc:mysql://ether404.com:3306/ethercom_bank";
    public static String DATABASE_URI = "jdbc:mysql://localhost:3307/bank";
//    public static String USERNAME = "ethercom_nibir";
    public static String USERNAME = "root";
//    public static String PASSWORD = "nibir#404";
    public static String PASSWORD = "root";

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/design/design.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);


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


        this.stage = primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
