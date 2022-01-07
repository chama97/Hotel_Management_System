package controller;

import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import model.Employee;
import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddEmployeeFormController {
    public AnchorPane addEmployeeFID;
    public JFXTextField txtEId;
    public JFXTextField txtEName;
    public JFXTextField txtEAddress;
    public JFXTextField txtEStatus;
    public JFXTextField txtEMail;
    public JFXTextField txtEContact;
    public JFXTextField txtESalary;

    public void EmployeeIdOnAction(ActionEvent actionEvent) {
    }

    public void clearEmployeeOnAction(ActionEvent actionEvent) {
    }

    public void saveEmployeeOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        Employee emp = new Employee(
                txtEId.getText(), txtEName.getText(), txtEAddress.getText(),txtEStatus.getText(),Integer.parseInt(txtEContact.getText()),txtEMail.getText(), Double.parseDouble(txtESalary.getText())
        );
        if(saveEmployee(emp))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    public boolean saveEmployee(Employee e) throws SQLException, ClassNotFoundException {
        Connection con= DbConnection.getInstance().getConnection();
        String query="INSERT INTO Employee VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,e.getId());
        stm.setObject(2,e.getName());
        stm.setObject(3,e.getAddress());
        stm.setObject(4,e.getStatus());
        stm.setObject(5,e.getContact());
        stm.setObject(6,e.getMail());
        stm.setObject(7,e.getSalary());
        return stm.executeUpdate()>0;
    }

}
