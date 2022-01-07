package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.Meal;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateMealFormController {
    public AnchorPane updateMealFID;
    public TextField txtFoodId;
    public TextField txtFoodType;
    public TextField txtFoodQty;
    public TextField txtFoodPrice;
    public JFXButton btnSave;

    public void idSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String mealId = txtFoodId.getText();

        Meal c1= new PlaceCustomerMealController().getMeal(mealId);
        if (c1==null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set").show();
        } else {
            setData(c1);
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
    }

    public void clearMealOnAction(ActionEvent actionEvent) {
        txtFoodId.setText(null);
        txtFoodType.setText(null);
        txtFoodQty.setText(null);
        txtFoodPrice.setText(null);
    }

    public void updateMealOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Meal c1= new Meal(
                txtFoodId.getText(),txtFoodType.getText(),
                Double.parseDouble(txtFoodPrice.getText()),
                Integer.parseInt(txtFoodQty.getText())

        );


        if (updateItem(c1))
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        else
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
    }

    public boolean updateItem(Meal c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Meal SET name=?, mealPrice=?, qty=? WHERE id=?");
        stm.setObject(1,c.getType());
        stm.setObject(2,c.getPrice());
        stm.setObject(3,c.getQty());
        stm.setObject(4,c.getId());
        return stm.executeUpdate()>0;
    }

    void setData(Meal c){
        txtFoodId.setText(c.getId());
        txtFoodType.setText(c.getType());
        txtFoodQty.setText(String.valueOf(c.getQty()));
        txtFoodPrice.setText(String.valueOf(c.getPrice()));
    }
}
