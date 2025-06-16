package minicla03.coinquylife.DATALAYER.RepositoryEntity;

import android.content.Context;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.UnavailabilityRemoteSource;
import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.UnavailabilityRequest;
import minicla03.coinquylife.Shift.DOMAIN.model.Roommate;
import minicla03.coinquylife.Shift.DOMAIN.repository.IUnavailabilityRepository;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnavailabilityRepository implements IUnavailabilityRepository
{
    private final UnavailabilityRemoteSource remote;

    public UnavailabilityRepository(Context context)
    {
        remote = new UnavailabilityRemoteSource(context);
    }

    public void addUnavailability(UnavailabilityRequest request, Consumer<String> callback)
    {
        remote.addUnavailability(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.accept(response.body().string());
                    } else {
                        String err = response.errorBody() != null ? response.errorBody().string() : "Errore sconosciuto";
                        callback.accept(err);
                    }
                } catch (IOException e) {
                    callback.accept("Errore lettura risposta: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.accept("Errore di rete: " + t.getMessage());
            }
        });
    }

    public void initializeUnavailability(String houseId, List<Roommate> roommates, Consumer<String> callback)
    {

        remote.initializeUnavailability(houseId, roommates).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.accept(response.body().string());
                    } else {
                        String msg = response.errorBody() != null ? response.errorBody().string() : "Errore sconosciuto";
                        callback.accept(msg);
                    }
                } catch (IOException e) {
                    callback.accept("Errore lettura risposta: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.accept("Errore di rete: " + t.getMessage());
            }
        });
    }

}
