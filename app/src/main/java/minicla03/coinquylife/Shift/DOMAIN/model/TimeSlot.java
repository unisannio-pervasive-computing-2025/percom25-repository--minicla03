package minicla03.coinquylife.Shift.DOMAIN.model;

public class TimeSlot
{
    private String idTimeSlot;
    private String start;
    private String end;

    public TimeSlot() {}

    public TimeSlot(String start, String end)
    {
        this.start = start;
        this.end = end;
    }

    public String getIdTimeSlot()
    {
        return this.idTimeSlot;
    }

    public void setIdTimeSlot(String idTimeSlot)
    {
        this.idTimeSlot = idTimeSlot;
    }

    public String getStart()
    {
        return start;
    }
    public void setStart(String start)
    {
        this.start = start;
    }

    public String getEnd()
    {
        return end;

    }
    public void setEnd(String end)
    {
        this.end = end;
    }


    @Override
    public String toString()
    {
        return "TimeSlot{" +
                "idTimeSlot='" + idTimeSlot + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}