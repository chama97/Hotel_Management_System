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
import javafx.stage.StageStyle;
import model.Employee;
import model.Meal;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MealFormController {
    public StackPane mealFID;
    public TableView tblMeals;
    public TableColumn colFoodId;
    public TableColumn colFoodName;
    public TableColumn colFoodPrice;
    public TableColumn colFoodQty;
    public TextField txtDelete;



    public void initialize() {
        try {

            colFoodId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colFoodName.setCellValueFactory(new PropertyValueFactory<>("type"));
            colFoodPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colFoodQty.setCellValueFactory(new PropertyValueFactory<>("qty"));


            setMealsToTable(getAllMeals());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public ArrayList<Meal> getAllMeals() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Meal");
        ResultSet rst = stm.executeQuery();
        ArrayList<Meal> meals = new ArrayList<>();
        while (rst.next()) {
            meals.add(new Meal(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)


            ));
        }
        return meals;
    }

    private void setMealsToTable(ArrayList<Meal> meals) {
        ObservableList<Meal> obList = FXCollections.observableArrayList();
        meals.forEach(e-> {
            obList.add(
                    new Meal(e.getId(), e.getType(), e.getPrice(), e.getQty()));

        });
        tblMeals.setItems(obList);
    }



    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        mealFID.getChildren().clear();
        mealFID.getChildren().add(load);

    }

    public void reportOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("ReportForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins can Access Reports");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }


    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("EmployeeForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admin can access Employee");
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

    public void UpdateMealOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddMealForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins can update Meals");
            alert.setTitle("Error");
            alert.showAndWait();
        }

    }

    public void deleteMealOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        if (deleteMeal(txtDelete.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }

    }

    public boolean deleteMeal(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Meal WHERE  id='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public void addMealOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddMealForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins can add Meals");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    public void placeMealOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/placeCustomerMeal.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    public void closeOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) mealFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void refreshTableOnAction(MouseEvent mouseEvent) throws IOException {
        loadUi("MealForm");
    }
}
