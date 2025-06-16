package minicla03.coinquylife.Expense.DOMAIN.useCase;

import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ExpenseAPI.ExpenseResult;

public interface ICreateExpenseUseCase
{
    void execute(String description, double amount, String houseId,
                 String createdBy, String category, List<String> participants,
                 Consumer<ExpenseResult> callback);
}
