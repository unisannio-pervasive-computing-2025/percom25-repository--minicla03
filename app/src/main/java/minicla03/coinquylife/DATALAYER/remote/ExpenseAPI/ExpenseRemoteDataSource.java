package minicla03.coinquylife.DATALAYER.remote.ExpenseAPI;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import minicla03.coinquylife.CoinquyLife;
import minicla03.coinquylife.Expense.DOMAIN.model.Debt;
import minicla03.coinquylife.Expense.DOMAIN.model.Expense;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseRemoteDataSource {

    private IExpenseAPI expenseAPI;
    private final Gson gson = new Gson();

    public ExpenseRemoteDataSource(Context context) {
        expenseAPI = CoinquyLife.getApi().getExpenseAPI(context);
    }

    public void createExpense(Expense expense, Consumer<ExpenseResult> callback) {
        Call<ExpenseResult> call = expenseAPI.createExpense(expense);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ExpenseResult> call, Response<ExpenseResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.accept(response.body());
                } else {
                    int code = response.code();
                    String errorMsg;

                    if (code == 400) {
                        errorMsg = "Input non valido (400): " + getErrorBody(response);
                    } else if (code == 500) {
                        errorMsg = "Errore interno del server (500)";
                    } else {
                        errorMsg = "Errore HTTP " + code + ": " + getErrorBody(response);
                    }

                    callback.accept(new ExpenseResult(ExpenseStatus.ERROR, errorMsg, null));
                }
            }

            @Override
            public void onFailure(Call<ExpenseResult> call, Throwable t) {
                callback.accept(new ExpenseResult(ExpenseStatus.ERROR, "Errore di rete: " + t.getMessage(), null));
            }
        });
    }

    public void getAllExpenses(String houseId, Consumer<List<Expense>> callback) {
        Call<ExpenseResultList> call = expenseAPI.getAllExpenses(houseId);
        call.enqueue(new Callback<>()
        {
            @Override
            public void onResponse(Call<ExpenseResultList> call, Response<ExpenseResultList> response)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    ExpenseResultList result = response.body();
                    if (result.getStatus() == ExpenseStatus.SUCCESS && result.getExpenses() != null) {
                        callback.accept(result.getExpenses());
                    } else if (result.getStatus() == ExpenseStatus.NO_CONTENT) {
                        callback.accept(Collections.emptyList());
                    } else {
                        callback.accept(null);
                    }
                } else {
                    callback.accept(null);
                }
            }

            @Override
            public void onFailure(Call<ExpenseResultList> call, Throwable t) {
                t.printStackTrace();
                callback.accept(null);
            }
        });
    }

    public void calculateDebt(String houseId, Consumer<List<Debt>> callback) {
        Map<String, String> body = new HashMap<>();
        body.put("houseId", houseId);

        Call<ExpenseDebtResult> call = expenseAPI.calculateDebt(body);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ExpenseDebtResult> call, Response<ExpenseDebtResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ExpenseDebtResult result = response.body();
                    if (result.getStatus() == ExpenseStatus.SUCCESS) {
                        callback.accept(result.getDebts());
                    } else if (result.getStatus() == ExpenseStatus.NO_CONTENT) {
                        callback.accept(Collections.emptyList());
                    } else {
                        callback.accept(null);
                    }
                } else {
                    callback.accept(null);
                }
            }

            @Override
            public void onFailure(Call<ExpenseDebtResult> call, Throwable t) {
                t.printStackTrace();
                callback.accept(null);
            }
        });
    }

    public void updateExpenseStatus(String expenseId, Consumer<ExpenseResult> callback) {
        Map<String, String> body = Map.of("expenseId", expenseId);
        Call<ExpenseResult> call = expenseAPI.updateExpenseStatus(body);
        call.enqueue(new Callback<ExpenseResult>() {
            @Override
            public void onResponse(Call<ExpenseResult> call, Response<ExpenseResult> response) {
                if (response.isSuccessful()) {
                    callback.accept(response.body());
                } else {
                    callback.accept(new ExpenseResult(ExpenseStatus.ERROR, "Errore HTTP: " + response.code(), null));
                }
            }

            @Override
            public void onFailure(Call<ExpenseResult> call, Throwable t) {
                callback.accept(new ExpenseResult(ExpenseStatus.ERROR, "Errore di rete: " + t.getMessage(), null));
            }
        });
    }

    private String getErrorBody(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                return response.errorBody().string();
            } else {
                return "Nessun dettaglio fornito";
            }
        } catch (IOException e) {
            return "Errore nel parsing del messaggio di errore";
        }
    }
}
