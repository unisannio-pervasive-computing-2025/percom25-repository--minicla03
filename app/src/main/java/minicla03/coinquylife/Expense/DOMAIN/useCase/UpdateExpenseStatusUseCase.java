package minicla03.coinquylife.Expense.DOMAIN.useCase;

import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ExpenseAPI.ExpenseResult;
import minicla03.coinquylife.Expense.DOMAIN.repository.IExpenseRepository;

public class UpdateExpenseStatusUseCase implements IUpdateExpenseStatusUseCase
{
    private final IExpenseRepository repository;

    public UpdateExpenseStatusUseCase(IExpenseRepository repository) {
        this.repository = repository;
    }

    public void execute(String expenseId, Consumer<ExpenseResult> callback)
    {
        callback.accept(repository.updateExpenseStatus(expenseId, callback));
    }
}
