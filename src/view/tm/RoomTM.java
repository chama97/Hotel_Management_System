package view.tm;

import com.jfoenix.controls.JFXToggleButton;

public class RoomTM {
    private String roomId;
    private String roomType;
    private String roomCapacity;
    private double roomFee;
    private JFXToggleButton btn;

    public RoomTM() {
    }

    public RoomTM(String roomId, String roomType, String roomCapacity, double roomFee, JFXToggleButton btn) {
        this.setRoomId(roomId);
        this.setRoomType(roomType);
        this.setRoomCapacity(roomCapacity);
        this.setRoomFee(roomFee);
        this.setBtn(btn);
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(String roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public double getRoomFee() {
        return roomFee;
    }

    public void setRoomFee(double roomFee) {
        this.roomFee = roomFee;
    }

    public JFXToggleButton getBtn() {
        return btn;
    }

    public void setBtn(JFXToggleButton btn) {
        this.btn = btn;
    }
}
