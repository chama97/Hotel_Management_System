package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddVehicleFormController {
    public AnchorPane addVehicleFID;
    public JFXTextField txtVehicleId;
    public JFXTextField txtVehicleType;
    public JFXTextField txtVehicleCharge;

    public void vehicleIdOnAction(ActionEvent actionEvent) {
    }

    public void clearVehicleOnAction(ActionEvent actionEvent) {
    }

    public void saveVehicleOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

       Vehicle vehicle = new Vehicle(
               txtVehicleId.getText(), txtVehicleType.getText(),Double.parseDouble(txtVehicleCharge.getText()),false
        );
        if(saveVehicle(vehicle))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    public boolean saveVehicle(Vehicle v) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Vehicle VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,v.getId());
        stm.setObject(2,v.getType());
        stm.setObject(3,v.getRentFee());
        stm.setObject(4,v.isAvailable(false));
        return stm.executeUpdate()>0;
    }
}

