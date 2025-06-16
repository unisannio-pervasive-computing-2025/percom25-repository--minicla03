package minicla03.coinquylife.DATALAYER.RepositoryEntity;

import android.app.Application;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ExpenseAPI.ExpenseRemoteDataSource;
import minicla03.coinquylife.DATALAYER.remote.ExpenseAPI.ExpenseResult;
import minicla03.coinquylife.Expense.DOMAIN.model.Debt;
import minicla03.coinquylife.Expense.DOMAIN.model.Expense;
import minicla03.coinquylife.Expense.DOMAIN.repository.IExpenseRepository;

public class ExpenseRepository implements IExpenseRepository
{
    private final ExpenseRemoteDataSource remoteDataSource;

    public ExpenseRepository(@NotNull Application application)
    {
        this.remoteDataSource = new ExpenseRemoteDataSource(application);
    }

    @Override
    public void createExpense(Expense expense, @NotNull Consumer<ExpenseResult> callback)
    {
        remoteDataSource.createExpense(expense, callback);
    }

    @Override
    public void getAllExpenses(String houseId, @NotNull Consumer<List<Expense>> callback)
    {
        remoteDataSource.getAllExpenses(houseId, callback);
    }

    @Override
    public void calculateDebt(String houseId, Consumer<List<Debt>> callback)
    {
        remoteDataSource.calculateDebt(houseId, callback);
    }

    @Override
    public ExpenseResult updateExpenseStatus(String expenseId, @NotNull Consumer<ExpenseResult> callback){
        remoteDataSource.updateExpenseStatus(expenseId, callback);
        return null;
    }
}
