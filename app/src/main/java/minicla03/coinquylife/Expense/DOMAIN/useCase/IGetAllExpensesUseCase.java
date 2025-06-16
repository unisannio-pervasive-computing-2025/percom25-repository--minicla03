package minicla03.coinquylife.Expense.DOMAIN.useCase;

import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.Expense.DOMAIN.model.Expense;

public interface IGetAllExpensesUseCase
{
    void execute(String houseId, Consumer<List<Expense>> callback);
}
