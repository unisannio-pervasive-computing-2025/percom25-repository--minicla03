package minicla03.coinquylife.DATALAYER.remote.ShiftAPI;


public class PlanningRequest
{
    private String houseId;
    private String problemId;

    public PlanningRequest(String houseId, String problemId)
    {
        this.houseId = houseId;
        this.problemId = problemId;
    }

    public String getHouseId() { return houseId; }
    public void setHouseId(String houseId) { this.houseId = houseId; }

    public String getProblemId() { return problemId; }
    public void setProblemId(String problemId) { this.problemId = problemId; }
}

