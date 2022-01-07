package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {
    public StackPane dashboardFID;
    public Label lblRole;
    public Label lblUserName;
    public ProgressIndicator roomAvailability;
    public ProgressIndicator reservedRoom;
    public StackPane dashboard2FID;

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/LoginForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) dashboardFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public  void initialize(){

        lblUserName.setText(LoginFormController.user.getUserName());

        if (controller.LoginFormController.user.getRole() == "Administrator") {
            lblRole.setText("Administrator");
        } else {
            lblRole.setText("Receptionist");
        }
    }

    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        dashboard2FID.getChildren().clear();
        dashboard2FID.getChildren().add(load);

    }

    public void reportOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("ReportForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins Can Access Reports");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }


    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("EmployeeForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins Can Edit Employee");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    public void vehicleOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("VehicleForm");
    }

    public void mealsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("MealForm");
    }

    public void RoomsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("RoomForm");
    }

    public void CustomerOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("CustomerForm");
    }

    public void reservedOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ReserveDetailForm");
    }

    public void checkOutOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("CheckOutForm");
    }

    public void checkInOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("CheckInForm");
    }
}
