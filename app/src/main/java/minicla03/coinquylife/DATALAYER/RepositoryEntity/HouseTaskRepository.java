package minicla03.coinquylife.DATALAYER.RepositoryEntity;

import android.content.Context;

import java.io.IOException;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.HouseTaskRemoteSource;
import minicla03.coinquylife.Shift.DOMAIN.repository.IHouseTaskRepository;
import minicla03.coinquylife.Shift.DOMAIN.model.HouseTask;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseTaskRepository implements IHouseTaskRepository
{
    private HouseTaskRemoteSource remoteSource;

    public HouseTaskRepository(Context context)
    {
        remoteSource = new HouseTaskRemoteSource(context);
    }

    public void createTask(HouseTask task, Consumer<String> callback)
    {
        remoteSource.createTask(task).enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse (Call <ResponseBody> call, Response < ResponseBody > response)
            {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String message = response.body().string();
                        callback.accept(message);
                    } else {
                        String errorMsg = response.errorBody() != null
                                ? response.errorBody().string()
                                : "Errore sconosciuto";
                        callback.accept(errorMsg);
                    }
                } catch (IOException e) {
                    callback.accept("Errore nella lettura della risposta: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                callback.accept("Errore di rete: " + t.getMessage());
            }
        });
    }
}
