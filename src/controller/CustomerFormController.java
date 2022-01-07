package controller;

import com.jfoenix.controls.JFXToggleButton;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Customer;
import model.Vehicle;
import view.tm.VehicleTM;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerFormController {
    public StackPane customerFID;
    public TableView<Customer> tblCustomer;
    public TableColumn colCId;
    public TableColumn colCName;
    public TableColumn colCAddress;
    public TableColumn colCCity;
    public TableColumn colCNationality;
    public TableColumn colCMail;
    public TableColumn colContact;


    public void initialize() {
        try {

            colCId.setCellValueFactory(new PropertyValueFactory<>("CId"));
            colCName.setCellValueFactory(new PropertyValueFactory<>("CName"));
            colCAddress.setCellValueFactory(new PropertyValueFactory<>("CAddress"));
            colCCity.setCellValueFactory(new PropertyValueFactory<>("CCity"));
            colCNationality.setCellValueFactory(new PropertyValueFactory<>("CNationality"));
            colCMail.setCellValueFactory(new PropertyValueFactory<>("CMail"));
            colContact.setCellValueFactory(new PropertyValueFactory<>("CContact"));

            setCustomerToTable(getAllCustomers());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public ArrayList<Customer> getAllCustomers()throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer");
        ResultSet rst = stm.executeQuery();
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            customers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getInt(7)

            ));
        }
        return customers;
    }

    private void setCustomerToTable(ArrayList<Customer> customers) {
        ObservableList<Customer> obList = FXCollections.observableArrayList();
        customers.forEach(e-> {
            obList.add(
                    new Customer(e.getCId(), e.getCName(), e.getCAddress(), e.getCNationality(), e.getCCity(), e.getCMail(), e.getCContact()));
        });
        tblCustomer.setItems(obList);
    }


    public void closeOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) customerFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        customerFID.getChildren().clear();
        customerFID.getChildren().add(load);

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

    public void customerSaveOnAction(ActionEvent actionEvent) {
    }
}
