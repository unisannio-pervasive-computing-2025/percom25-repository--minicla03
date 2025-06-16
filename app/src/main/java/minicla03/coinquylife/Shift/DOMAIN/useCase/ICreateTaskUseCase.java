package minicla03.coinquylife.Shift.DOMAIN.useCase;
import java.text.ParseException;
import java.util.function.Consumer;

public interface ICreateTaskUseCase
{
    void execute(String taskCategory, String description, String start, String houseId,
                 Consumer<String> callback) throws ParseException;
}
