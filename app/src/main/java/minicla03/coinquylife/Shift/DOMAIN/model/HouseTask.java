package minicla03.coinquylife.Shift.DOMAIN.model;

import com.google.gson.annotations.SerializedName;

public class HouseTask
{
    private String idTask;
    private TaskCategory task;
    private String houseId;
    private String description;
    private TimeSlot timeSlot;
    @SerializedName("done")
    private boolean isDone;

    public HouseTask() { }

    public HouseTask(TaskCategory task, String description, TimeSlot timeSlot, String houseId)
    {
        this.task = task;
        this.description = description;
        this.timeSlot = timeSlot;
        this.houseId = houseId;
        this.isDone = false;
    }

    public String getIdTask()
    {
        return this.idTask;
    }

    public void setIdTask(String idTask)
    {
        this.idTask = idTask;
    }

    public TaskCategory getTask()
    {
        return this.task;
    }

    public void setTask(TaskCategory task)
    {
        this.task = task;
    }

    public String getHouseId()
    {
        return this.houseId;
    }

    public void setHouseId(String houseId)
    {
        this.houseId = houseId;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public TimeSlot getTimeSlot()
    {
        return this.timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot)
    {
        this.timeSlot = timeSlot;
    }

    public boolean isDone() { return isDone; }
    public void setDone(boolean done) {isDone = done; }

    @Override
    public String toString()
    {
        return "HouseTask{" +
                "idTask='" + idTask + '\'' +
                ", task=" + task +
                ", houseId='" + houseId + '\'' +
                ", description='" + description + '\'' +
                ", timeSlot=" + timeSlot +
                ", isDone=" + isDone +
                '}';
    }
}