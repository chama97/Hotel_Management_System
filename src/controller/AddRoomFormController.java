package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddRoomFormController {
    public AnchorPane addRoomFID;
    public JFXTextField txtRoomId;
    public JFXTextField txtRoomType;
    public JFXTextField txtRoomCapacity;
    public JFXTextField txtRoomPrice;

    public void RoomIdOnAction(ActionEvent actionEvent) {
    }

    public void clearRoomOnAction(ActionEvent actionEvent) {
    }

    public void saveRoomOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Room rooms = new Room(
                txtRoomId.getText(), txtRoomType.getText(), txtRoomCapacity.getText(), Double.parseDouble(txtRoomPrice.getText())
        );
        if(saveRoom(rooms))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    public boolean saveRoom(Room r) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Room VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,r.getRoomId());
        stm.setObject(2,r.getRoomType());
        stm.setObject(3,r.getRoomCapacity());
        stm.setObject(4,r.getRoomFee());
        return stm.executeUpdate()>0;
    }

}
