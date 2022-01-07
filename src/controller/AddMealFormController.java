package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import model.Meal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddMealFormController {
    public AnchorPane addMealFID;
    public JFXTextField txtFoodId;
    public JFXTextField txtFoodType;
    public JFXTextField txtFoodQty;
    public JFXTextField txtFoodPrice;

    public void foodIdOnAction(ActionEvent actionEvent) {
    }

    public void clearMealOnAction(ActionEvent actionEvent) {
    }

    public void saveMealOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Meal meal = new Meal(
               txtFoodId.getText(), txtFoodType.getText(), Double.parseDouble(txtFoodPrice.getText()), Integer.parseInt(txtFoodQty.getText())
        );
        if(saveMeal(meal))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    public boolean saveMeal(Meal m) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Meal VALUES(?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,m.getId());
        stm.setObject(2,m.getType());
        stm.setObject(3,m.getPrice());
        stm.setObject(4,m.getQty());

        return stm.executeUpdate()>0;
    }
}
