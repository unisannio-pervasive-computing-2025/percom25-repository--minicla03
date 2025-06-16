package minicla03.coinquylife.Rank.DOMAIN.model;

import com.google.gson.annotations.SerializedName;

public class Classifica
{
    @SerializedName("ClassificaId")
    private String id;
    private String idCoinquy;
    private String houseId;
    private int totalPoint;

    public Classifica() { }

    public Classifica(String idCoinquy, String houseId, int point) {
        this.idCoinquy = idCoinquy;
        this.houseId = houseId;
        this.totalPoint = point;
    }

    public String getIdCoinquy() {
        return idCoinquy;
    }

    public void setIdCoinquy(String idCoinquy) {
        this.idCoinquy = idCoinquy;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(int totalPoint) {
        this.totalPoint = totalPoint;
    }

}
