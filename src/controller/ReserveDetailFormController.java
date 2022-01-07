package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Customer;
import model.ReserveDetail;
import view.tm.CartTM;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReserveDetailFormController {
    public StackPane reserveFID;
    public TableView <ReserveDetail> tblReserve;
    public TextField txtCheckOut;
    public TableColumn colCId;
    public TableColumn colRoomId;
    public TableColumn colCheckInDate;
    public TableColumn colCheckOutDate;
    public TableColumn colRoomCharge;
    public TableColumn colMealCharge;
    public TableColumn colVehicleCharge;
    public TableColumn colTotal;


    public void initialize() {
        try {

            colCId.setCellValueFactory(new PropertyValueFactory<>("CId"));
            colRoomId.setCellValueFactory(new PropertyValueFactory<>("RId"));
            colCheckInDate.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
            colCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
            colRoomCharge.setCellValueFactory(new PropertyValueFactory<>("roomFee"));
            colMealCharge.setCellValueFactory(new PropertyValueFactory<>("foodFee"));
            colVehicleCharge.setCellValueFactory(new PropertyValueFactory<>("VehicleFee"));
            colTotal.setCellValueFactory(new PropertyValueFactory<>("totalFee"));


            setCustomerDetailToTable(getAllCustomerDetail());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public ArrayList<ReserveDetail> getAllCustomerDetail()throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `reserve Detail`");
        ResultSet rst = stm.executeQuery();
        ArrayList<ReserveDetail> reserveDetails = new ArrayList<>();
        while (rst.next()) {
            reserveDetails.add(new ReserveDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDate(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7),
                    rst.getDouble(8)


            ));
        }
        return reserveDetails;
    }

    ObservableList<ReserveDetail> observableList = FXCollections.observableArrayList();

    private void setCustomerDetailToTable(ArrayList<ReserveDetail> reserveDetails) {

        reserveDetails.forEach(e-> {
            observableList.add(
                    new ReserveDetail(e.getCId(), e.getRId(), e.getCheckInDate(), e.getCheckOutDate(), e.getRoomFee(), e.getFoodFee(), e.getVehicleFee(), e.getTotalFee()));
        });
        tblReserve.setItems(observableList);
    }

    public  ObservableList<ReserveDetail> getReserveList() {
        return observableList;
    }



    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        reserveFID.getChildren().clear();
        reserveFID.getChildren().add(load);

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

    public void closeOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) reserveFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void refreshTableOnAction(MouseEvent mouseEvent) {
    }
}
