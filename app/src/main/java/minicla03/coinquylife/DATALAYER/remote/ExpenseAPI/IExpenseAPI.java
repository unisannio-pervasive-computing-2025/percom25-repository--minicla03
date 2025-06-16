package minicla03.coinquylife.DATALAYER.remote.ExpenseAPI;

import java.util.Map;

import minicla03.coinquylife.Expense.DOMAIN.model.Expense;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IExpenseAPI
{
    @Headers("Content-Type: application/json")
    @POST("Expense/rest/expense/calculateDebt")
    Call<ExpenseDebtResult> calculateDebt(
        @Body Map<String,String> body
    );

    @Headers("Content-Type: application/json")
    @POST("Expense/rest/expense/createExpense")
    Call<ExpenseResult> createExpense(
        @Body Expense body
    );

    @GET("Expense/rest/expense/getAllExpenses")
    @Headers("Content-Type: application/json")
    Call<ExpenseResultList> getAllExpenses(
        @Query("houseId") String houseId
    );

    @POST("Expense/rest/expense/updateExpenseStatus")
    @Headers("Content-Type:application/json")
    Call<ExpenseResult> updateExpenseStatus(
        @Body Map<String, String> body
    );
}
