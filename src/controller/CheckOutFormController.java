package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CheckOutFormController {
    public StackPane checkOutFID;
    public TextField txtOutCId;


    public void clearOnAction(ActionEvent actionEvent) {
    }

    public void checkOutCIdOnAction(ActionEvent actionEvent) {
    }

    public void closeOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) checkOutFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }



    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        checkOutFID.getChildren().clear();
        checkOutFID.getChildren().add(load);

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
        //new DashboardFormController().vehicleOnAction(actionEvent);

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
