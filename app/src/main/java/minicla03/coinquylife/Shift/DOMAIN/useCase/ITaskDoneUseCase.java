package minicla03.coinquylife.Shift.DOMAIN.useCase;

import java.util.function.Consumer;

public interface ITaskDoneUseCase {
    void execute(String id, Consumer<String> onSuccess, Consumer<String> onError);
}
