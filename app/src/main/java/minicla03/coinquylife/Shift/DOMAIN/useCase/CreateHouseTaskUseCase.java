package minicla03.coinquylife.Shift.DOMAIN.useCase;

import android.os.Build;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import minicla03.coinquylife.Shift.DOMAIN.model.HouseTask;
import minicla03.coinquylife.Shift.DOMAIN.model.TaskCategory;
import minicla03.coinquylife.Shift.DOMAIN.model.TimeSlot;
import minicla03.coinquylife.Shift.DOMAIN.repository.IHouseTaskRepository;

public class CreateHouseTaskUseCase implements ICreateTaskUseCase
{
    private IHouseTaskRepository repository;

    public CreateHouseTaskUseCase(IHouseTaskRepository repository)
    {
        this.repository = repository;
    }

    public void execute(String description, String taskCategory,
                        String start, String houseId, Consumer<String> callback) throws ParseException {

        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        java.util.Date startDateTime = inputFormatter.parse(start);

        String formattedStart = outputFormatter.format(startDateTime);

        TimeSlot timeSlot = new TimeSlot(formattedStart, null);
        Log.d("CreateHouseTaskUseCase", "category: " + taskCategory);
        Log.d("CreateHouseTaskUseCase", "categoryTASk: " + taskCategory.substring(3));
        HouseTask houseTask = new HouseTask(TaskCategory.fromString(taskCategory.substring(3)), description, timeSlot, houseId);
        repository.createTask(houseTask, callback);
    }
}
