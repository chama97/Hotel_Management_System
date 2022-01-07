package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Meal;
import model.Room;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateRoomController {
    public AnchorPane addRoomFID;
    public TextField txtRoomId;
    public TextField txtRoomType;
    public TextField txtRoomCapacity;
    public TextField txtRoomPrice;
    public JFXButton btnSave;

    public void roomIdOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String roomId = txtRoomId.getText();

        Room c1= getRoom(roomId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }
    }

    public void clearRoomOnAction(ActionEvent actionEvent) {
        txtRoomId.setText(null);
        txtRoomType.setText(null);
        txtRoomCapacity.setText(null);
        txtRoomPrice.setText(null);
    }

    public void updateRoomOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Room c1= new Room(
                txtRoomId.getText(),txtRoomType.getText(),
                txtRoomCapacity.getText(),
                Double.parseDouble(txtRoomPrice.getText())

        );


        if (updateItem(c1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();

    }

    public boolean updateItem(Room c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Room SET roomType=?, capacity=?,roomPrice=? WHERE id=?");
        stm.setObject(1,c.getRoomType());
        stm.setObject(2,c.getRoomCapacity());
        stm.setObject(3,c.getRoomFee());
        stm.setObject(4,c.getRoomId());
        return stm.executeUpdate()>0;
    }

    void setData(Room c){
        txtRoomId.setText(c.getRoomId());
        txtRoomType.setText(c.getRoomType());
        txtRoomCapacity.setText(c.getRoomCapacity());
        txtRoomPrice.setText(String.valueOf(c.getRoomFee()));
    }

    public Room getRoom(String RId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Room WHERE id='" + RId + "'").
                executeQuery();
        if (rst.next()) {
            return new Room(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)

            );
        } else {
            return null;
        }
    }
}
