package minicla03.coinquylife.Expense.PRESENTATION.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.RepositoryEntity.ExpenseRepository;
import minicla03.coinquylife.DATALAYER.remote.ExpenseAPI.ExpenseResult;
import minicla03.coinquylife.Expense.DOMAIN.model.Debt;
import minicla03.coinquylife.Expense.DOMAIN.model.Expense;
import minicla03.coinquylife.Expense.DOMAIN.repository.IExpenseRepository;
import minicla03.coinquylife.Expense.DOMAIN.useCase.CalculateDebtUseCase;
import minicla03.coinquylife.Expense.DOMAIN.useCase.CreateExpenseUseCase;
import minicla03.coinquylife.Expense.DOMAIN.useCase.GetAllExpensesUseCase;
import minicla03.coinquylife.Expense.DOMAIN.useCase.ICalculateDebtUseCase;
import minicla03.coinquylife.Expense.DOMAIN.useCase.ICreateExpenseUseCase;
import minicla03.coinquylife.Expense.DOMAIN.useCase.IGetAllExpensesUseCase;
import minicla03.coinquylife.Expense.DOMAIN.useCase.IUpdateExpenseStatusUseCase;
import minicla03.coinquylife.Expense.DOMAIN.useCase.UpdateExpenseStatusUseCase;


public class ExpenseViewModel extends AndroidViewModel
{
    private final ICreateExpenseUseCase createExpenseUseCase;
    private final IGetAllExpensesUseCase getAllExpensesUseCase;
    private final IUpdateExpenseStatusUseCase updateExpenseStatusUseCase;
    private final ICalculateDebtUseCase calculateDebtUseCase;

    private final MutableLiveData<ExpenseResult> expenseLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Expense>> expensesLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<String>> debtLiveData = new MutableLiveData<>();
    private final MutableLiveData<ExpenseResult> updateExpenseStatusLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<User>> listCoinquy = new MutableLiveData<>();
    private final MutableLiveData<String> houseId = new MutableLiveData<>();

    public ExpenseViewModel(@NotNull Application application)
    {
        super(application);
        IExpenseRepository expenseRepository = new ExpenseRepository(application);
        this.createExpenseUseCase = new CreateExpenseUseCase(expenseRepository);
        this.getAllExpensesUseCase = new GetAllExpensesUseCase(expenseRepository);
        this.updateExpenseStatusUseCase = new UpdateExpenseStatusUseCase(expenseRepository);
        this.calculateDebtUseCase = new CalculateDebtUseCase(expenseRepository);
    }

    public void createExpense(String description, double amount, String houseId,
                               String createdBy, String category, List<String> participants)
    {
        createExpenseUseCase.execute(description, amount, houseId, createdBy, category, participants, expenseLiveData::postValue);
    }

    public void getAllExpenses(String houseId)
    {
        getAllExpensesUseCase.execute(houseId, expensesLiveData::postValue);
    }

    public void updateExpenseStatus(String expenseId)
    {
        updateExpenseStatusUseCase.execute(expenseId, updateExpenseStatusLiveData::postValue);
    }

    public void calculateDebt(String houseId)
    {
        calculateDebtUseCase.execute(houseId, debtLiveData::postValue);
    }

    public LiveData<List<Expense>> getExpenses() {
        return expensesLiveData;
    }


    public LiveData<ExpenseResult> getExpenseLiveData() {
        return expenseLiveData;
    }

    public LiveData<List<String>> getDebtLiveData() {
        return debtLiveData;
    }

    public LiveData<ExpenseResult> getUpdateExpenseStatusLiveData()
    {
        return updateExpenseStatusLiveData;
    }

    public LiveData<List<User>> getListCoinquy() {
        return listCoinquy;
    }

    public void setListCoinquy(List<User> listCoinquy) {
        this.listCoinquy.setValue(listCoinquy);
    }

    public LiveData<String> getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId.setValue(houseId);
    }
}
