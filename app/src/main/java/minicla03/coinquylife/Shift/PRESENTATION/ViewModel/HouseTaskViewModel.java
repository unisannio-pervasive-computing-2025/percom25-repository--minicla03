package minicla03.coinquylife.Shift.PRESENTATION.ViewModel;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.time.LocalDateTime;

import minicla03.coinquylife.DATALAYER.RepositoryEntity.HouseTaskRepository;

import minicla03.coinquylife.Shift.DOMAIN.useCase.CreateHouseTaskUseCase;
import minicla03.coinquylife.Shift.DOMAIN.useCase.ICreateTaskUseCase;

public class HouseTaskViewModel extends AndroidViewModel
{
    private final ICreateTaskUseCase createTaskUseCase;
    private final MutableLiveData<Boolean> taskCreated = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public HouseTaskViewModel(@NonNull Application application)
    {
        super(application);
        HouseTaskRepository repository = new HouseTaskRepository(application);
        createTaskUseCase = new CreateHouseTaskUseCase(repository);
    }

    public MutableLiveData<Boolean> getTaskCreated()
    {
        return taskCreated;
    }

    public MutableLiveData<String> getError()
    {
        return error;
    }

    public void createTask(String description, String taskCategory, String dateStart, String houseId) throws ParseException {
        createTaskUseCase.execute(description, taskCategory, dateStart, houseId, result ->
        {
            if (result != null)
            {
                taskCreated.postValue(true);
            } else
            {
                error.postValue("Failed to create task");
            }
        });
    }
}
