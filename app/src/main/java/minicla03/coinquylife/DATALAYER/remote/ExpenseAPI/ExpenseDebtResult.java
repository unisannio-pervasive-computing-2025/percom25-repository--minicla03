package minicla03.coinquylife.DATALAYER.remote.ExpenseAPI;

import java.util.List;

import minicla03.coinquylife.Expense.DOMAIN.model.Debt;

public class ExpenseDebtResult
{
    private ExpenseStatus status;
    private String message;
    private List<Debt> debts;

    public ExpenseDebtResult(ExpenseStatus status, String message, List<Debt> debts) {
        this.status = status;
        this.message = message;
        this.debts = debts;
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

    public List<Debt> getDebts() {
        return debts;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }
}