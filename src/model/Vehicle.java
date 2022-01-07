package model;

import com.jfoenix.controls.JFXToggleButton;

public class Vehicle {
    private String id;
    private String type;
    private double rentFee;
    private boolean isAvailable;

    public Vehicle() {
    }

    public Vehicle(String id, String type, double rentFee, boolean isAvailable) {
        this.id = id;
        this.type = type;
        this.rentFee = rentFee;
        this.isAvailable = isAvailable;
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

    public boolean isAvailable(boolean  isAvailable) {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", rentFee=" + rentFee +
                ", isAvailable=" + isAvailable +
                '}';
    }

}
