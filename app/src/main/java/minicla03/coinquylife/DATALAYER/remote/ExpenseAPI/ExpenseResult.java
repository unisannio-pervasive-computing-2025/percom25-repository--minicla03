package minicla03.coinquylife.DATALAYER.remote.ExpenseAPI;

import minicla03.coinquylife.Expense.DOMAIN.model.Expense;

public class ExpenseResult {

    private ExpenseStatus status;
    private String message;
    private Expense expenses;

    public ExpenseResult(ExpenseStatus status, String message, Expense expenses) {
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

    public void setMessage(String message) {
        this.message = message;
    }

    public Expense  getExpenses() {
        return expenses;
    }

    public void setExpenses(Expense  expenses) {
        this.expenses = expenses;
    }
}