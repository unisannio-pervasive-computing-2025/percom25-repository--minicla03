package minicla03.coinquylife.Shift.DOMAIN.model;


public enum TaskCategory
{
    CLEANING("Cleaning", 10, -5),
    MAINTENANCE("Maintenance", 15, -10),
    REPAIR("Repair", 20, -15);

    private final String taskName;
    private final int points;
    private final int penalityPoints;

    TaskCategory(String taskName, int points, int penalityPoints)
    {
        this.taskName = taskName;
        this.points = points;
        this.penalityPoints = penalityPoints;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getPoints() {
        return points;
    }

    public int getPenalityPoints() {
        return penalityPoints;
    }

    public static TaskCategory fromString(String value) {
        return TaskCategory.valueOf(value.toUpperCase());
    }

    public String toValue() {
        return this.name();
    }

    public static class TaskCategoryAdapter extends com.google.gson.TypeAdapter<TaskCategory> {
        @Override
        public void write(com.google.gson.stream.JsonWriter out, TaskCategory value) throws java.io.IOException {
            out.value(value.taskName);
        }

        @Override
        public TaskCategory read(com.google.gson.stream.JsonReader in) throws java.io.IOException {
            String value = in.nextString();
            for (TaskCategory category : TaskCategory.values()) {
                if (category.taskName.equalsIgnoreCase(value)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("Unknown TaskCategory: " + value);
        }
    }
}
