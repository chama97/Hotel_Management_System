package view.tm;

import com.jfoenix.controls.JFXToggleButton;

public class VehicleTM {
    private String id;
    private String type;
    private double rentFee;
    private JFXToggleButton btn;

    public VehicleTM() {
    }

    public VehicleTM(String id, String type, double rentFee, JFXToggleButton btn) {
        this.setId(id);
        this.setType(type);
        this.setRentFee(rentFee);
        this.setBtn(btn);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRentFee() {
        return rentFee;
    }

    public void setRentFee(double rentFee) {
        this.rentFee = rentFee;
    }

    public JFXToggleButton getBtn() {
        return btn;
    }

    public void setBtn(JFXToggleButton btn) {
        this.btn = btn;
    }

}
