package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane loginFID;
    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public JFXComboBox<String> cmbUserRole;
    public JFXButton loginButton;
    public Label lblCheckLogin;
    public static User user;


    public void initialize(){
        ObservableList<String> obList = FXCollections.observableArrayList(
                "Administrator",
                "Receptionist"
        );
        cmbUserRole.setItems(obList);
    }

    public void openDashboardOnAction(ActionEvent actionEvent) {

        user = new User(txtUserName.getText(), txtPassword.getText(), cmbUserRole.getSelectionModel().getSelectedItem());

        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        String role = cmbUserRole.getSelectionModel().getSelectedItem();

        if(userName.equals("") || password.equals("") || role.equals("")){
            lblCheckLogin.setText("Please Enter Your Data");
        }else{
            try {
                Connection con= DbConnection.getInstance().getConnection();
                PreparedStatement stm = con.prepareStatement("SELECT * FROM User WHERE userName=? and password=? and role=?");
                stm.setString(1, userName);
                stm.setString(2, password);
                stm.setString(3, role);
                ResultSet rst = stm.executeQuery();

                if(rst.next()) {
                    URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
                    Parent load = FXMLLoader.load(resource);
                    Stage window = (Stage) loginFID.getScene().getWindow();
                    window.setScene(new Scene(load));
                }else {
                    lblCheckLogin.setText("Wrong Username or Password or Role");
                }


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void UserAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }
}
