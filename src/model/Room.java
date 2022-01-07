package model;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomId;
    private String roomType;
    private String roomCapacity;
    private double roomFee;

    public Room() {
    }

    public Room(String roomId, String roomType, String roomCapacity, double roomFee) {
        this.setRoomId(roomId);
        this.setRoomType(roomType);
        this.setRoomCapacity(roomCapacity);
        this.setRoomFee(roomFee);
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


    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId + '\'' +
                ", roomType='" + roomType + '\'' +
                ", roomCapacity='" + roomCapacity + '\'' +
                ", roomFee=" + roomFee +
                '}';
    }

    public static Room Search_available_rooms(String RoomType, String RoomCapacity) throws SQLException, ClassNotFoundException {
        List<Room> AvailableRooms = getAvailableRooms();
        for (int i = 0; i < AvailableRooms.size(); i++) {
            if (AvailableRooms.get(i).getRoomType().equals(RoomType)
                    && AvailableRooms.get(i).getRoomCapacity().equals(RoomCapacity)) {
                return AvailableRooms.get(i);
            }
        }
        return null;
    }

    public static List<Room> getAvailableRooms() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Room");
        ResultSet rst = stm.executeQuery();
        ArrayList<Room> rooms = new ArrayList<>();
        while (rst.next()) {
            rooms.add(new Room(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)

            ));
        }
        return rooms;
    }

}
