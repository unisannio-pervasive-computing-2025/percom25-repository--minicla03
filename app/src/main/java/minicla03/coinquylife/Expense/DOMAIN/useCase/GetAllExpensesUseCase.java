package minicla03.coinquylife.Expense.DOMAIN.useCase;

import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.Expense.DOMAIN.model.Expense;
import minicla03.coinquylife.Expense.DOMAIN.repository.IExpenseRepository;

public class GetAllExpensesUseCase implements IGetAllExpensesUseCase
{
    private final IExpenseRepository repository;

    public GetAllExpensesUseCase(IExpenseRepository repository)
    {
        this.repository = repository;
    }

    public void execute(String houseId, Consumer<List<Expense>> callback)
    {
        repository.getAllExpenses(houseId, callback);
    }
}
