package minicla03.coinquylife.Shift.DOMAIN.model;

import java.util.List;

public class Roommate
{
    private String usernameRoommate;
    private String houseId;
    private List<TimeSlot> unavailableTimeSlots;

    public Roommate() {}

    public Roommate(String usernameRoommate, String houseId, List<TimeSlot> unavailableTimeSlots) {
        this.usernameRoommate = usernameRoommate;
        this.houseId = houseId;
        this.unavailableTimeSlots = unavailableTimeSlots;
    }

    public Roommate(String usernameRoommate, String houseId) {
        this.usernameRoommate = usernameRoommate;
        this.houseId = houseId;
    }

    public String getUsernameRoommate() { return this.usernameRoommate; }
    public void setUsernameRoommate(String idRoommate) { this.usernameRoommate = idRoommate; }

    public String getHouseId() { return this.houseId; }
    public void setHouseId(String houseId) { this.houseId = houseId; }

    public List<TimeSlot> getUnavailableTimeSlots() { return unavailableTimeSlots; }
    public void setUnavailableTimeSlots(List<TimeSlot> unavailableTimeSlots) { this.unavailableTimeSlots = unavailableTimeSlots; }

    @Override
    public String toString() {
        return "Roommate{" +
                "usernameRoommate='" + usernameRoommate + '\'' +
                ", houseId='" + houseId + '\'' +
                ", unavailableTimeSlots=" + unavailableTimeSlots +
                '}';
    }
}