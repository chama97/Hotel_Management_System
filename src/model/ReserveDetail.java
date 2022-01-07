package model;


import java.util.Date;

public class ReserveDetail {
    private String CId;
    private String RId;
    private Date checkInDate;
    private Date checkOutDate;
    private double roomFee;
    private double foodFee;
    private double VehicleFee;
    private double totalFee;

    public ReserveDetail() {
    }

    public ReserveDetail(String CId, String RId, Date checkInDate, Date checkOutDate, double roomFee, double foodFee, double vehicleFee, double totalFee) {
        this.CId = CId;
        this.RId = RId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomFee = roomFee;
        this.setFoodFee(foodFee);
        setVehicleFee(vehicleFee);
        this.setTotalFee(totalFee);
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getRId() {
        return RId;
    }

    public void setRId(String RId) {
        this.RId = RId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getRoomFee() {
        return roomFee;
    }

    public void setRoomFee(double roomFee) {
        this.roomFee = roomFee;
    }


    public double getFoodFee() {
        return foodFee;
    }

    public void setFoodFee(double foodFee) {
        this.foodFee = foodFee;
    }

    public double getVehicleFee() {
        return VehicleFee;
    }

    public void setVehicleFee(double vehicleFee) {
        VehicleFee = vehicleFee;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    @Override
    public String toString() {
        return "ReserveDetail{" +
                "CId='" + CId + '\'' +
                ", RId='" + RId + '\'' +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", roomFee=" + roomFee +
                ", foodFee=" + foodFee +
                ", VehicleFee=" + VehicleFee +
                ", totalFee=" + totalFee +
                '}';
    }
}
