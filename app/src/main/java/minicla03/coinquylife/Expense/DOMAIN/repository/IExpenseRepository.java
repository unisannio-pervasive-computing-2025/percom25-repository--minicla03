package minicla03.coinquylife.Expense.DOMAIN.repository;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ExpenseAPI.ExpenseResult;
import minicla03.coinquylife.Expense.DOMAIN.model.Debt;
import minicla03.coinquylife.Expense.DOMAIN.model.Expense;

public interface IExpenseRepository
{
    void createExpense(Expense expense, @NotNull Consumer<ExpenseResult> callback);

    void getAllExpenses(String houseId, Consumer<List<Expense>> callback);

    void calculateDebt(String houseId, Consumer<List<Debt>> callback);

    ExpenseResult updateExpenseStatus(String expenseId, @NotNull Consumer<ExpenseResult> callback);
}