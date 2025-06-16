package minicla03.coinquylife.DATALAYER.RepositoryEntity;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.PlanningRequest;
import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.ShiftRemoteSource;
import minicla03.coinquylife.Expense.DOMAIN.model.Expense;
import minicla03.coinquylife.Shift.DOMAIN.model.CleaningAssignment;
import minicla03.coinquylife.Shift.DOMAIN.repository.IShiftRepository;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ShiftRepository implements IShiftRepository
{

    private final ShiftRemoteSource remoteSource;
    private final Gson gson = new Gson();

    public ShiftRepository(Context context)
    {
        remoteSource = new ShiftRemoteSource(context);
    }

    public void getPlanning(PlanningRequest request, Consumer<List<CleaningAssignment>> callback, Consumer<String> error)
    {
        remoteSource.getPlanning(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String json = response.body().string();
                        List<CleaningAssignment> assignments = gson.fromJson(json, new TypeToken<List<CleaningAssignment>>() {
                        }.getType());
                        callback.accept(assignments);
                    }
                    else if(response.code()==500)
                    {
                        error.accept(response.errorBody().string());
                    }
                    else {
                        error.accept(response.errorBody().string());
                    }
                } catch (IOException e) {
                    error.accept(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                error.accept(t.getMessage());
            }
        });
    }

    public void taskDone(String id, Consumer<String> onSuccess, Consumer<String> onError) {
        remoteSource.taskDone(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String message = response.body().string();
                        onSuccess.accept(message);
                    }
                    else
                    {
                        String errorMsg = response.errorBody() != null ? response.errorBody().string() : "Errore sconosciuto";
                        onError.accept(errorMsg);
                    }
                } catch (IOException e) {
                    onError.accept("Errore nella lettura della risposta: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onError.accept("Errore di rete: " + t.getMessage());
            }
        });
    }

    @Override
    public void toRank(String typeTask, String username, String houseId, String dateComplete, String endTime, Consumer<String> callback, Consumer<String> error)
    {
        remoteSource.toRank(typeTask, username, houseId, dateComplete, endTime).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String result = response.body().string();
                        Log.d("ShiftRepository", "Risposta dal server: " + result);
                        callback.accept(result);
                    } else {
                        error.accept(response.errorBody() != null ? response.errorBody().string() : "Errore sconosciuto");
                    }
                } catch (IOException e) {
                    error.accept("Errore nella lettura della risposta: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                error.accept("Errore di rete: " + t.getMessage());
            }
        });
    }

    public void retrieveAllShift(String houseId, Consumer<List<CleaningAssignment>> callback, Consumer<String> error) {
        remoteSource.retrieveAllShift(houseId).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response != null) {
                        assert response.body() != null;
                        String JsonResult = response.body().string();
                        List<CleaningAssignment> result = gson.fromJson(JsonResult, new TypeToken<List<CleaningAssignment>>() {
                        }.getType());
                        Log.d("ShiftRepository", "Risposta dal server: " + result);
                        callback.accept(result);
                    } else {
                        error.accept(response.errorBody() != null ? response.errorBody().string() : "Errore sconosciuto");
                    }
                } catch (Exception e) {
                    error.accept("Errore nel recupero degli shifts: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                error.accept("Errore di rete: " + t.getMessage());
            }
        });
    }
}
