package minicla03.coinquylife.Shift.DOMAIN.model;

import java.util.UUID;

public class CleaningAssignment
{
    private String id;
    private UUID problemId;
    private HouseTask task;
    private Roommate assignedRoommate;

    public CleaningAssignment() {}

    public CleaningAssignment(UUID problemId, String id, HouseTask task)
    {
        this.problemId = problemId;
        this.id = id;
        this.task = task;
    }

    public String getId() { return this.id; }
    public void setId(String id) { this.id = id; }

    public HouseTask getTask() { return task; }
    public void setTask(HouseTask task) { this.task = task; }

    public UUID getProblemId() { return problemId; }
    public void setProblemId(UUID problemId) { this.problemId = problemId; }

    public Roommate getAssignedRoommate() { return assignedRoommate; }
    public void setAssignedRoommate(Roommate assignedRoommate) { this.assignedRoommate = assignedRoommate; }
}