package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
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
import javafx.stage.StageStyle;
import model.Employee;
import model.Vehicle;
import view.tm.RoomTM;
import view.tm.VehicleTM;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleFormController {
    public StackPane vehicleFID;
    public TableView<VehicleTM> tblVehicle;
    public TableColumn colId;
    public TableColumn colType;
    public TableColumn colCharge;
    public ComboBox cmbCId;
    public ComboBox cmbVehicleId;
    public TextField txtDays;
    public TextField txtDelete;
    public JFXButton btnAdd;
    public Label lblTotalCharge;
    public Label lblVehicleCharge;
    public TableColumn colAvailable;


    public void initialize() {
        try {

            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colCharge.setCellValueFactory(new PropertyValueFactory<>("rentFee"));
            colAvailable.setCellValueFactory(new PropertyValueFactory<>("btn"));

            setVehicleToTable(getAllVehicles());

            loadVehicleIds();
            loadCustomerIds();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        cmbVehicleId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setVehicleData((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }



    public ArrayList<Vehicle> getAllVehicles()throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle");
        ResultSet rst = stm.executeQuery();
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        while (rst.next()) {
            vehicles.add(new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getBoolean(4)

            ));
        }
        return vehicles;
    }

    private void setVehicleToTable(ArrayList<Vehicle> vehicles) {
        ObservableList<VehicleTM> obList = FXCollections.observableArrayList();
        vehicles.forEach(e-> {
            JFXToggleButton btn = new JFXToggleButton();
            obList.add(
                    new VehicleTM(e.getId(), e.getType(), e.getRentFee(), btn));

            btn.setText("True");

            btn.setOnMouseClicked((MouseEvent event) -> {
                if (btn.isSelected()) {
                    btn.setText("False");
                }else{
                    btn.setText("True");

                }
            });


        });
        tblVehicle.setItems(obList);
    }

    private void loadVehicleIds() throws SQLException, ClassNotFoundException {
        List<String> vehicleIds = getAllVehicleIds();
        cmbVehicleId.getItems().addAll(vehicleIds);
    }



    public List<String> getAllVehicleIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Vehicle").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = getAllCustomerIds();
        cmbCId.getItems().addAll(customerIds);
    }



    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Customer").executeQuery();
        List<String> ids= new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public Vehicle getVehicle(String VId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Vehicle WHERE id='" + VId + "'").
                executeQuery();
        if (rst.next()){
            return new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getBoolean(4)
            );
        }else{
            return null;
        }
    }


    private void setVehicleData(String id) throws SQLException, ClassNotFoundException {
        Vehicle v = getVehicle(id);
        if (v == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {

            lblVehicleCharge.setText(String.valueOf(v.getRentFee()));
        }
    }



    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        vehicleFID.getChildren().clear();
        vehicleFID.getChildren().add(load);

    }

    public void reportOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("ReportForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admin can access Reports");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }


    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("EmployeeForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only admin can access Employee");
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

    public void UpdateVehicleOnAction(ActionEvent actionEvent) throws IOException {

        if (controller.LoginFormController.user.getRole() == "Administrator") {

            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddVehicleForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admin can update vehicles");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    public void deleteVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (deleteVehicle(txtDelete.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public boolean deleteVehicle(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Vehicle WHERE  id='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void addVehicleOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddVehicleForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admin can add Vehicles");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    public void rentVehicleOnAction(ActionEvent actionEvent) {
    }

    public void closeOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) vehicleFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void refreshTableOnAction(MouseEvent mouseEvent) throws IOException {
        loadUi("VehicleForm");
    }

    public void totalChargeOnAction(ActionEvent actionEvent) {
        double total;
        total = Double.parseDouble(lblVehicleCharge.getText())*Double.parseDouble(txtDays.getText());
        lblTotalCharge.setText(String.valueOf("Rs: "+total));
    }
}
