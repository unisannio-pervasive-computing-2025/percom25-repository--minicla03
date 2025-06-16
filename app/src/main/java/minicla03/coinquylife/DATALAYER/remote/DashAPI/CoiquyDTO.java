package minicla03.coinquylife.DATALAYER.remote.DashAPI;

public class CoiquyDTO
{
    private String username;
    private String houseId;

    public CoiquyDTO() { }

    public CoiquyDTO(String username, String houseId) {
        this.username = username;
        this.houseId = houseId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getHouseId() {
        return houseId;
    }
    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    @Override
    public String toString() {
        return "CoiquyDTO{" +
                "username='" + username + '\'' +
                ", houseId='" + houseId + '\'' +
                '}';
    }
}
