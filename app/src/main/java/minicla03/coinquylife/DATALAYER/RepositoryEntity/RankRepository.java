package minicla03.coinquylife.DATALAYER.RepositoryEntity;

import android.app.Application;


import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.DashAPI.CoiquyListDTO;
import minicla03.coinquylife.DATALAYER.remote.DashAPI.DashRemoteDataSource;
import minicla03.coinquylife.Rank.DOMAIN.model.Classifica;
import minicla03.coinquylife.Rank.DOMAIN.repository.IRankRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankRepository implements IRankRepository
{
    private DashRemoteDataSource remoteDataSource;

    public RankRepository(Application application)
    {
        remoteDataSource = new DashRemoteDataSource(application);
    }

    public void getClassifica(CoiquyListDTO body, Consumer<Map<String, Classifica>> callback)
    {
        Call<Map<String, Classifica>> call = remoteDataSource.getClassifica(body);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Classifica>> call, @NonNull Response<Map<String, Classifica>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.accept(response.body());
                } else {
                    callback.accept(Collections.emptyMap());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Classifica>> call, @NonNull Throwable t) {
                callback.accept(Collections.emptyMap());
            }
        });
    }

}
