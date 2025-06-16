package minicla03.coinquylife.Shift.DOMAIN.useCase;

import java.util.function.Consumer;

public interface IToRankUseCase
{
    void execute(String typeTask, String username, String houseId, String endTime,
                 Consumer<String> callback);
}
