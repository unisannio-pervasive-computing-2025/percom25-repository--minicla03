package minicla03.coinquylife.DATALAYER.remote.ShiftAPI;

public class UnavailabilityRequest {
    private final String username;
    private final String houseId;
    private final String start;
    private final String end;

    public UnavailabilityRequest(String username, String houseId, String start, String end) {
        this.username = username;
        this.houseId = houseId;
        this.start = start;
        this.end = end;
    }

    public String getUsername() {
        return username;
    }

    public String getHouseId() {
        return houseId;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}