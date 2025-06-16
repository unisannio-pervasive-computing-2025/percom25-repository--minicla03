package minicla03.coinquylife.Expense.DOMAIN.useCase;

import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ExpenseAPI.ExpenseResult;
import minicla03.coinquylife.Expense.DOMAIN.model.CategoryExpense;
import minicla03.coinquylife.Expense.DOMAIN.model.Expense;
import minicla03.coinquylife.Expense.DOMAIN.repository.IExpenseRepository;
import minicla03.coinquylife.Shift.DOMAIN.model.TaskCategory;

public class CreateExpenseUseCase implements ICreateExpenseUseCase
{
    private final IExpenseRepository expenseRepository;

    public CreateExpenseUseCase(IExpenseRepository expenseRepository)
    {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public void execute(String description, double amount, String houseId, String createdBy,
                        String category, List<String> participants, Consumer<ExpenseResult> callback)
    {
        Log.d("CreateExpenseUseCase", ""+CategoryExpense.fromString(category));
        Expense newExpense = new Expense(description, amount, createdBy, CategoryExpense.fromString(category), houseId, participants);
        expenseRepository.createExpense(newExpense, callback);
    }
}
