package minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI;

public class SelectHouseResult
{
    private String code;
    private String message;
    private SelectHouseStatus status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SelectHouseStatus getStatus() {
        return status;
    }

    public void setStatus(SelectHouseStatus status) {
        this.status = status;
    }
}
