package minicla03.coinquylife.SelectionHouse.DOMAIN.model;

public class CoinquyHouse {

    private String houseId;
    private String houseName;
    private String houseAddress;

    public CoinquyHouse() { }

    public CoinquyHouse(String houseName, String houseAddress) {
        this.houseName = houseName;
        this.houseAddress = houseAddress;
    }

    public CoinquyHouse(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }
}
