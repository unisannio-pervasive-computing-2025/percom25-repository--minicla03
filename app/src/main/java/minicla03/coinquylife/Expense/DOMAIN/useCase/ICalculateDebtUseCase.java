package minicla03.coinquylife.Expense.DOMAIN.useCase;

import java.util.List;
import java.util.function.Consumer;

public interface ICalculateDebtUseCase
{
    void execute(String houseId, Consumer<List<String>> callback);
}
