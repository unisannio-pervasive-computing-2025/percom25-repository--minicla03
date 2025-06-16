package minicla03.coinquylife.Expense.DOMAIN.useCase;

import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ExpenseAPI.ExpenseResult;

public interface IUpdateExpenseStatusUseCase {

    void execute(String expenseId, Consumer<ExpenseResult> callback);
}
