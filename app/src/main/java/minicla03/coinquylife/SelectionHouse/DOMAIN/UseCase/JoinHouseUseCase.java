package minicla03.coinquylife.SelectionHouse.DOMAIN.UseCase;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseResult;
import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseStatus;
import minicla03.coinquylife.SelectionHouse.DOMAIN.Repository.ISelectHouseRepository;
import minicla03.coinquylife.SelectionHouse.DOMAIN.model.CoinquyHouse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinHouseUseCase implements IJoinHouseUseCase
{
    private final ISelectHouseRepository repository;

    public JoinHouseUseCase(ISelectHouseRepository repo)
    {
        this.repository = repo;
    }

    @Override
    public void execute(String houseCode, Consumer<SelectHouseResult> callback) {
        CoinquyHouse coinquyHouse = new CoinquyHouse(houseCode);
        Call<SelectHouseResult> call = repository.joinHouseRemote(coinquyHouse);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<SelectHouseResult> call, @NonNull Response<SelectHouseResult> response) {
                SelectHouseResult result = new SelectHouseResult();

                if (response.isSuccessful() && response.body() != null) {
                    result = response.body();
                    result.setStatus(SelectHouseStatus.LINKED_SUCCES);
                } else {
                    switch (response.code()) {
                        case 404:
                            result.setStatus(SelectHouseStatus.HOUSE_NOT_FOUND);
                            result.setMessage("Casa o utente non trovati");
                            break;
                        case 409:
                            result.setStatus(SelectHouseStatus.ALREADY_LINK);
                            result.setMessage("Utente già collegato ad una casa");
                            break;
                        default:
                            result.setStatus(SelectHouseStatus.ERROR);
                            result.setMessage("Errore server: " + response.code());
                            break;
                    }
                }
                callback.accept(result);
            }

            @Override
            public void onFailure(@NonNull Call<SelectHouseResult> call, @NonNull Throwable t) {
                SelectHouseResult error = new SelectHouseResult();
                error.setStatus(SelectHouseStatus.ERROR);
                error.setMessage("Errore di rete: " + t.getMessage());
                callback.accept(error);
            }
        });
    }

}
