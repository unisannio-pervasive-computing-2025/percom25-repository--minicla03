package minicla03.coinquylife.Shift.DOMAIN.useCase;

import java.util.function.Consumer;

import minicla03.coinquylife.Shift.DOMAIN.repository.IShiftRepository;

public class TaskDoneUseCase implements ITaskDoneUseCase
{

    private final IShiftRepository repository;

    public TaskDoneUseCase(IShiftRepository repository) {
        this.repository = repository;
    }

    public void execute(String id, Consumer<String> onSuccess, Consumer<String> onError) {
        repository.taskDone(id, onSuccess, onError);
    }
}
