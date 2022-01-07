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
import model.Room;
import view.tm.RoomTM;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomFormController {
    public StackPane roomFID;
    public TableView tblRoom;
    public TableColumn colRoomId;
    public TableColumn colRoomType;
    public TableColumn colRoomCapacity;
    public TableColumn colRoomCharge;
    public TableColumn colAvailable;
    public TextField txtDelete;

    public void initialize() {
        try {

            colRoomId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
            colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
            colRoomCapacity.setCellValueFactory(new PropertyValueFactory<>("roomCapacity"));
            colRoomCharge.setCellValueFactory(new PropertyValueFactory<>("roomFee"));
            colAvailable.setCellValueFactory(new PropertyValueFactory<>("btn"));


            setRoomsToTable(getAllRooms());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public ArrayList<Room> getAllRooms() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Room");
        ResultSet rst = stm.executeQuery();
        ArrayList<Room> rooms = new ArrayList<>();
        while (rst.next()) {
            rooms.add(new Room(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)

            ));
        }
        return rooms;
    }

    private void setRoomsToTable(ArrayList<Room> rooms) {
        ObservableList<RoomTM> obList = FXCollections.observableArrayList();
        rooms.forEach(e-> {
                    JFXToggleButton btn = new JFXToggleButton();
                    obList.add(
                            new RoomTM(e.getRoomId(), e.getRoomType(), e.getRoomCapacity(), e.getRoomFee(), btn));
            btn.setStyle(
                    " -fx-cursor: hand ;"
                            + "-fx-fill: #d600cc;"
            );

            btn.setText("True");
            btn.setOnMouseClicked((MouseEvent event) -> {
                if (btn.isSelected()) {
                     btn.setText("False");
                }else{
                    btn.setText("True");

                }
            });

        });
        tblRoom.setItems(obList);
    }








    void loadUi(String fileName) throws IOException {
        URL resource = getClass().getResource("../view/"+fileName+".fxml");
        Parent load = FXMLLoader.load(resource);
        roomFID.getChildren().clear();
        roomFID.getChildren().add(load);

    }

    public void reportOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("ReportForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins can access Reports");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }


    public void EmployeeOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            loadUi("EmployeeForm");

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins can access Employee");
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

    public void UpdateRoomOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddRoomForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins can modify rooms");
            alert.setTitle("Error");
            alert.showAndWait();
        }

    }

    public void deleteRoomOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (deleteRoom(txtDelete.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    public boolean deleteRoom(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Room WHERE  id='" + id + "'").executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public void closeOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashboardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) roomFID.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void addRoomOnAction(ActionEvent actionEvent) throws IOException {
        if (controller.LoginFormController.user.getRole() == "Administrator") {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/AddRoomForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.OK);
            alert.setHeaderText("Only Admins can add rooms");
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    public void refreshTableOnAction(MouseEvent mouseEvent) throws IOException {
        loadUi("RoomForm");
    }
}
