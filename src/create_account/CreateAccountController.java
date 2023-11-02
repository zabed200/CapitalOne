package create_account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import login.Main;


public class CreateAccountController implements Initializable {

    private FileChooser fileChooser = new FileChooser();
    private File file;
    private FileInputStream fis;


    @FXML
    private ImageView profilepic;
    @FXML
    private TextField name;
    @FXML
    private TextField idcardno;
    @FXML
    private TextField mobileno;
    @FXML
    private TextField city;
    @FXML
    private TextField address;
    @FXML
    private TextField accountno;
    @FXML
    private TextField pin;
    @FXML
    private TextField balance;
    @FXML
    private TextField answer;
    @FXML
    private DatePicker dob;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private ComboBox<String> maritalstatus;
    @FXML
    private ComboBox<String> religion;
    @FXML
    private ComboBox<String> accounttype;
    @FXML
    private ComboBox<String> questions;

    ObservableList<String> list = FXCollections.observableArrayList("Male","Female");
    ObservableList<String> list1 = FXCollections.observableArrayList("Single","Married");
    ObservableList<String> list2 = FXCollections.observableArrayList("Islam","Christian","Others");
    ObservableList<String> list3 = FXCollections.observableArrayList("Savings","Current");
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
    public void setUpPic(MouseEvent e) {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png","*.jpg"));
        file = fileChooser.showOpenDialog(null);
        if(file != null) {
            Image img = new Image(file.toURI().toString(),150,150,true,true);
            profilepic.setImage(img);
            profilepic.setPreserveRatio(true);
        }
    }


//==============Validator=========================

    public boolean validateName(){
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(name.getText());
        if(m.find() && m.group().equals(name.getText())){
            return true;
        }else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Input Error");
            a.setContentText("Enter characters only in the name field. \nPlease Try Again!");
            a.showAndWait();
            return false;
        }
    }

    public boolean validateMobileNo(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(mobileno.getText());
        if(m.find() && m.group().equals(mobileno.getText())){
            return true;
        }else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Input Error");
            a.setContentText("Enter numbers only in the mobile no. field. \nPlease Try Again!");
            a.showAndWait();
            return false;
        }
    }

    public boolean validateICN(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(idcardno.getText());
        if(m.find() && m.group().equals(idcardno.getText())){
            return true;
        }else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Input Error");
            a.setContentText("Enter numbers only in the ID field. \nPlease Try Again!");
            a.showAndWait();
            return false;
        }
    }

    public boolean validateBalance(){
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(balance.getText());
        if(m.find() && m.group().equals(balance.getText())){
            return true;
        }else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Input Error");
            a.setContentText("Enter numbers only in the balance field. \nPlease Try Again!");
            a.showAndWait();
            return false;
        }
    }

    public void clearAllFields(){
        name.clear();
        idcardno.clear();
        mobileno.clear();
        gender.getSelectionModel().clearSelection();
        religion.getSelectionModel().clearSelection();
        maritalstatus.getSelectionModel().clearSelection();
        dob.getEditor().clear();
        city.clear();
        address.clear();
        pin.clear();
        accounttype.getSelectionModel().clearSelection();
        balance.clear();
        questions.getSelectionModel().clearSelection();
        answer.clear();
        Image img = new Image("/images/unnamed.jpg");
        profilepic.setImage(img);
        accountno.setText(String.valueOf(generateAccountNo()));
    }

    public int generateAccountNo() {
        Random rand = new Random();
        int num = rand.nextInt(899999)+100000;
        return num;
    }

//================================================


    public void newAccount(MouseEvent e) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Main.DATABASE_URI, Main.USERNAME, Main.PASSWORD);

            if(validateName() && validateMobileNo() && validateICN() && validateBalance()) {

                String sql = "INSERT INTO userdata (Name, ICN, MobileNo, Gender,MaritalStatus, Religion, DOB, City, Address, AccountNo,PIN, AccountType,Balance,SecurityQuestion, Answer, ProfilePic) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql);


                ps.setString(1, name.getText());
                ps.setString(2, idcardno.getText());
                ps.setString(3, mobileno.getText());
                ps.setString(4, gender.getValue());
                ps.setString(5, religion.getValue());
                ps.setString(6, maritalstatus.getValue());
                ps.setString(7, ((TextField) dob.getEditor()).getText());
                ps.setString(8, city.getText());
                ps.setString(9, address.getText());
                ps.setString(10, accountno.getText());
                ps.setString(11, pin.getText());
                ps.setString(12, accounttype.getValue());
                ps.setString(13, balance.getText());
                ps.setString(14, questions.getValue());
                ps.setString(15, answer.getText());

                fis = new FileInputStream(file);
                ps.setBinaryStream(16, (InputStream) fis, (int) file.length());

                int i = ps.executeUpdate();
                if (i > 0) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Account Created");
                    a.setHeaderText("Account Created Successfully");
                    a.setContentText("Your account has been created successfully. You Can now login to your account");
                    a.showAndWait();

                    clearAllFields();
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error....");
                    a.setHeaderText("Error in creating account");
                    a.setContentText("Your account is not created. There are some errors. Please try again later!");
                    a.showAndWait();
                }
            }

        }catch (Exception ee){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Error");
            a.setHeaderText("Error in creating account");
            a.setContentText(ee.getMessage()+"Your account is not created. There are some technical issues. Please try again later!");
            a.showAndWait();

        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gender.setItems(list);
        maritalstatus.setItems(list1);
        religion.setItems(list2);
        accounttype.setItems(list3);
        questions.setItems(list4);
        accountno.setText(String.valueOf(generateAccountNo()));
        accountno.setEditable(false);
    }

}
