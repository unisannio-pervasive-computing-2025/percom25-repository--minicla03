package minicla03.coinquylife.DATALAYER.remote.ExpenseAPI;

import java.util.List;
import minicla03.coinquylife.Expense.DOMAIN.model.Expense;

public class ExpenseResultList
{
    private ExpenseStatus status;
    private String message;
    private List<Expense> expenses;

    public ExpenseResultList(ExpenseStatus status, String message, List<Expense> expenses) {
        this.status = status;
        this.message = message;
        this.expenses = expenses;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}