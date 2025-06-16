package minicla03.coinquylife.SelectionHouse.DOMAIN.UseCase;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseResult;
import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseStatus;
import minicla03.coinquylife.SelectionHouse.DOMAIN.Repository.ISelectHouseRepository;
import minicla03.coinquylife.SelectionHouse.DOMAIN.model.CoinquyHouse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewHouseUseCase implements INewHouseUseCase
{
    private final ISelectHouseRepository repository;

    public NewHouseUseCase(ISelectHouseRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void execute(String houseName, String address, Consumer<SelectHouseResult> callback)
    {
        CoinquyHouse coinquyHouse = new CoinquyHouse(houseName, address);
        Call<SelectHouseResult> remoteCall = repository.createHouseRemote(coinquyHouse);

        remoteCall.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<SelectHouseResult> call, @NonNull Response<SelectHouseResult> response) {
                if (response.isSuccessful() && response.body() != null)
                {
                    SelectHouseResult result = response.body();
                    result.setStatus(SelectHouseStatus.HOUSE_CREATED);
                    Log.d("NewHouseUseCase", "House created successfully: " + result.getCode());
                    callback.accept(result);
                } else {
                    SelectHouseResult errorResult = new SelectHouseResult();
                    errorResult.setStatus(SelectHouseStatus.ERROR);
                    errorResult.setCode("Errore: " + response.code());
                    callback.accept(errorResult);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SelectHouseResult> call, @NonNull Throwable t) {
                SelectHouseResult failureResult = new SelectHouseResult();
                failureResult.setStatus(SelectHouseStatus.ERROR);
                failureResult.setCode("Errore di rete: " + t.getMessage());
                callback.accept(failureResult);
            }
        });
    }

}

