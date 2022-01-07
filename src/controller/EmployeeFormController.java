package controller;

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
import model.Room;
import view.tm.RoomTM;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeFormController {
    public StackPane employeeFID;
    public TableView tblEmployee;
    public TextField txtDelete;
    public TableColumn colEId;
    public TableColumn colEName;
    public TableColumn colEAddress;
    public TableColumn colStatus;
    public TableColumn colEMail;
    public TableColumn colEContact;
    public TableColumn colESalary;


    public void initialize() {
        try {

            colEId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colEName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colEAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            colEContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            colEMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
            colESalary.setCellValueFactory(new PropertyValueFactory<>("salary"));


            setEmployeesToTable(getAllEmployees());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public ArrayList<Employee> getAllEmployees() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee");
        ResultSet rst = stm.executeQuery();
        ArrayList<Employee> emp = new ArrayList<>();
        while (rst.next()) {
            emp.add(new Employee(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5),
                    rst.getString(6),
                    rst.getDouble(7)

            ));
        }
        return emp;
    }

    private void setEmployeesToTable(ArrayList<Employee> emp) {
        ObservableList<Employee> obList = FXCollections.observableArrayList();
        emp.forEach(e-> {
            obList.add(
                    new Employee(e.getId(), e.getName(), e.getAddress(), e.getStatus(), e.getContact(), e.getMail(), e.getSalary()));

        });
        tblEmployee.setItems(obList);
    }



    public void UpdateEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddEmployeeForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins can edit Employee");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    public void deleteEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (deleteRoom(txtDelete.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public boolean deleteRoom(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Employee WHERE  id='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public void closeOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) employeeFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void addEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddEmployeeForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins Can Add Employee");
            alert.setTitle("Error");
            alert.showAndWait();
        }

    }



    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        employeeFID.getChildren().clear();
        employeeFID.getChildren().add(load);

    }

    public void reportOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("ReportForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins Can access Reports");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }


    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("EmployeeForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admin can access employee");
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

    public void refreshTableOnAction(MouseEvent mouseEvent) throws IOException {
        loadUi("EmployeeForm");
    }
}
