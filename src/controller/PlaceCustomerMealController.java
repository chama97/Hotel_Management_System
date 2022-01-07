package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Meal;
import model.ReserveDetail;
import model.Vehicle;
import view.tm.CartTM;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceCustomerMealController {
    public AnchorPane placeMaelFID;
    public TableView tblPlaceMeal;
    public TableColumn colCId;
    public TableColumn colMId;
    public TableColumn colCharge;
    public TableColumn colQty;
    public TableColumn colTotalCharge;
    public ComboBox cmbCId;
    public ComboBox cmbMealId;
    public TextField txtQty;
    public Label lblPrice;
    public Label lblQtyHand;
    public Label lblTotal;
    public Label roomId;

    int cartSelectedRowForRemove = -1;

    public void initialize() {

        colMId.setCellValueFactory(new PropertyValueFactory<>("MId"));
        colCharge.setCellValueFactory(new PropertyValueFactory<>("charge"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCharge.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {

            loadVehicleIds();
            loadCustomerIds();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbMealId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setMealData((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        cmbCId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setVehicleData((String) newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadVehicleIds() throws SQLException, ClassNotFoundException {
        List<String> mealIds = getAllMealIds();
        cmbMealId.getItems().addAll(mealIds);
    }

    public List<String> getAllMealIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Meal").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = getAllCustomerIds();
        cmbCId.getItems().addAll(customerIds);
    }


    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `reserve Detail`").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public Meal getMeal(String MId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Meal WHERE id='" + MId + "'").
                executeQuery();
        if (rst.next()) {
            return new Meal(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            );
        } else {
            return null;
        }
    }


    private void setMealData(String id) throws SQLException, ClassNotFoundException {
        Meal m = getMeal(id);
        if (m == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {

            lblPrice.setText(String.valueOf(m.getPrice()));
            lblQtyHand.setText(String.valueOf(m.getQty()));
        }
    }

    ObservableList<CartTM> obList = FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent) {

        int qtyOnHand = Integer.parseInt(lblQtyHand.getText());
        double unitPrice = Double.parseDouble(lblPrice.getText());
        int qtyForCustomer = Integer.parseInt(txtQty.getText());
        double total = qtyForCustomer * unitPrice;

        if (qtyOnHand < qtyForCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        CartTM tm = new CartTM(
                String.valueOf(cmbMealId.getValue()),
                unitPrice,
                qtyForCustomer,
                total
        );

        int rowNumber = isExists(tm);

        if (rowNumber == -1) {
            obList.add(tm);
        } else {

            CartTM temp = obList.get(rowNumber);
            CartTM newTm = new CartTM(
                    temp.getMId(),
                    unitPrice,
                    temp.getQty() + qtyForCustomer,
                    total + temp.getTotal()
            );

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblPlaceMeal.setItems(obList);
        calculateCost();

    }

    private int isExists(CartTM tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getMId().equals(obList.get(i).getMId())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        double ttl = 0;
        for (CartTM tm : obList
        ) {
            ttl += tm.getTotal();
        }

        lblTotal.setText(String.valueOf(ttl));
    }


    public void clearOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblPlaceMeal.refresh();
        }
    }

   // ObservableList<ReserveDetail> observableList = FXCollections.observableArrayList();

    public void placeMealOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ReserveDetail rd = new ReserveDetail(
                String.valueOf(cmbCId.getValue()), roomId.getText(), null, null, 0, Double.parseDouble(lblTotal.getText()), 0, Double.parseDouble(lblTotal.getText())
        );

       /* int rowNumber= isExist(rd);
        if (rowNumber==-1){
          observableList.add(rd);
        }else{

            ReserveDetail temp = observableList.get(rowNumber);
            ReserveDetail newTm = new ReserveDetail(
                    temp.getCId(),
                    temp.getRId(),
                    temp.getCheckInDate(),
                    temp.getCheckOutDate(),
                    0+temp.getFoodFee(),
                    temp.getFoodFee()+Double.parseDouble(lblTotal.getText()),
                    0,
                    Double.parseDouble(lblTotal.getText())+temp.getTotalFee()
            );

            observableList.remove(rowNumber);
            observableList.add(newTm);
    }*/

        if (saveMealDetail(rd))

            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();

        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

   /* private  int isExist(ReserveDetail rd){
        for (int i = 0; i < observableList.size(); i++) {
            if (rd.getCId().equals(observableList.get(i).getCId())){
                return i;
            }
        }
        return -1;
    }*/

    public boolean saveMealDetail(ReserveDetail rd) throws SQLException, ClassNotFoundException {

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `reserve Detail` SET roomId=?, checkInDate=?, checkOutDate=?, roomCharge=? ,foodCharge=?, vehicleCharge=?, totalFee=? WHERE custId=?");
        stm.setObject(1, rd.getCId());
        stm.setObject(2, rd.getRId());
        stm.setObject(3, rd.getCheckInDate());
        stm.setObject(4, rd.getCheckOutDate());
        stm.setObject(5, rd.getRoomFee());
        stm.setObject(6, rd.getFoodFee());
        stm.setObject(7, rd.getVehicleFee());
        stm.setObject(8, rd.getTotalFee());

        return stm.executeUpdate() > 0;
    }

    public ReserveDetail getDetail(String RId) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM `reserve Detail` WHERE custId='" + RId + "'").
                executeQuery();
        if (rst.next()){
            return new ReserveDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDate(4),
                    rst.getDouble(5),
                    rst.getDouble(6),
                    rst.getDouble(7),
                    rst.getDouble(8)
            );
        }else{
            return null;
        }
    }


    private void setVehicleData(String id) throws SQLException, ClassNotFoundException {
        ReserveDetail v = getDetail(id);
        if (v == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {

            roomId.setText(String.valueOf(v.getRId()));
        }
    }


}
