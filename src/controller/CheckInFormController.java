package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Customer;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import model.ReserveDetail;
import model.Room;
import util.Validation;
import view.tm.RoomTM;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class CheckInFormController {
    public StackPane checkInFID;
    public JFXTextField txtCId;
    public JFXTextField txtCName;
    public JFXTextField txtCAddress;
    public JFXTextField txtCNationality;
    public JFXTextField txtCCity;
    public JFXTextField txtCMail;
    public JFXTextField txtCContact;
    public JFXDatePicker checkInDate;
    public JFXDatePicker checkOutDate;
    public JFXTextField txtRoomId;
    public Label lblTotal;
    public JFXComboBox cmbRoomType;
    public JFXComboBox cmbRoomCapacity;
    public Label lblContact;
    public Label lblMail;
    public Label lblCity;
    public Label lblNationality;
    public Label lblAddress;
    public Label lblName;
    public Label lblId;
    public JFXButton btnSave;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(C00-)[0-9]{3,4}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{6,30}$");
    Pattern nationalityPattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern cityPattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern mailPattern = Pattern.compile("^[A-z ]{2,20}[@][A-z]{2,20}[.com]$");
    Pattern contactPattern = Pattern.compile("^[0][1-9]{3}[-]?[0-9]{7}$");

    private void storeValidations() {
        map.put(txtCId, idPattern);
        map.put(txtCName, namePattern);
        map.put(txtCAddress, addressPattern);
        map.put(txtCNationality, nationalityPattern);
        map.put(txtCCity, cityPattern);
        map.put(txtCMail, mailPattern);
        map.put(txtCContact, contactPattern);
    }



    public void customerSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Customer customer = new Customer(
                txtCId.getText(), txtCName.getText(), txtCAddress.getText(), txtCNationality.getText(), txtCCity.getText(), txtCMail.getText(), Integer.parseInt(txtCContact.getText())
        );

        ReserveDetail rd = new ReserveDetail(
                txtCId.getText(), txtRoomId.getText(), Date.valueOf(checkInDate.getValue()), Date.valueOf(checkOutDate.getValue()), Double.parseDouble(lblTotal.getText()), 0, 0, Double.parseDouble(lblTotal.getText())
        );

        if (saveCustomer(customer) && saveCustomerDetail(rd))

            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();

       // new RoomTM().getBtn().setText("false");

    }

    public boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Customer VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, c.getCId());
        stm.setObject(2, c.getCName());
        stm.setObject(3, c.getCAddress());
        stm.setObject(4, c.getCNationality());
        stm.setObject(5, c.getCCity());
        stm.setObject(6, c.getCMail());
        stm.setObject(7, c.getCContact());
        return stm.executeUpdate() > 0;
    }

    public boolean saveCustomerDetail(ReserveDetail r) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO `reserve Detail` VALUES(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, r.getCId());
        stm.setObject(2, r.getRId());
        stm.setObject(3, r.getCheckInDate());
        stm.setObject(4, r.getCheckOutDate());
        stm.setObject(5, r.getRoomFee());
        stm.setObject(6, r.getFoodFee());
        stm.setObject(7, r.getVehicleFee());
        stm.setObject(8, r.getTotalFee());

        return stm.executeUpdate() > 0;
    }


   

    public boolean isFieldsEmpty() {
        if ("".equals(txtCName.getText())
                || ("".equals(txtCAddress.getText())) || ("".equals(txtCCity.getText()))
                || ("".equals(txtCNationality.getText()))
                || ("".equals(txtCContact.getText())) || ("".equals(txtCMail.getText()))
                || ("".equals(txtCId.getText()))) {

            return true;

        } else {

            return false;
        }
    }


    public void searchRoomAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (!isFieldsEmpty()) {
            Room rooms = Room.Search_available_rooms(getRoomTypeValue(), getRoomCapacityValue());
            
            if (rooms == null) {
                txtRoomId.setText("no match !");
            } else {
                
                txtRoomId.setText(rooms.getRoomId());
                lblTotal.setText(String.valueOf(rooms.getRoomFee() * getNoOfDays()));
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill all fields !");
            alert.setHeaderText("Fill all fields !");
            alert.setTitle("Error !");
            alert.showAndWait();
        }
    }

    public long getNoOfDays() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date firstDate = Date.valueOf(checkInDate.getValue());
            Date secondDate = Date.valueOf(checkOutDate.getValue());

            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            return diff;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getRoomTypeValue() {
        String roomType;
        roomType = (String) cmbRoomType.getSelectionModel().getSelectedItem();
        return roomType;
    }

    public String getRoomCapacityValue() {
        String roomCapacity;
        roomCapacity = (String) cmbRoomCapacity.getSelectionModel().getSelectedItem();
        return roomCapacity;
    }

    public void initialize() {

        btnSave.setDisable(true);
        storeValidations();

        ObservableList<String> typeList = FXCollections.observableArrayList(
                "Normal",
                "Deluxe",
                "Superior"
        );
        cmbRoomType.setItems(typeList);

        ObservableList<String> capacityList = FXCollections.observableArrayList(
                "Single",
                "Double",
                "Triple",
                "Quad"
        );
        cmbRoomCapacity.setItems(capacityList);

    }




    public void closeOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) checkInFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        checkInFID.getChildren().clear();
        checkInFID.getChildren().add(load);

    }

    public void reportOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("ReportForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admin Can Access Reports");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }


    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("EmployeeForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admin Can Edit Employee");
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

    public void refreshOnAction(MouseEvent mouseEvent) {
    }

    public void Handle_NameField(KeyEvent keyEvent) {

            if ((txtCName.getText()).matches("[A-Za-z\\s]{2,}")) {
                lblName.setText("valid");
                lblName.setTextFill(Color.GREEN);
            } else {
                lblName.setText("Name must contain only letters");
                lblName.setTextFill(Color.RED);

            }
        }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        Object response = Validation.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof JFXTextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Added").showAndWait();
            }
        }
    }
}

