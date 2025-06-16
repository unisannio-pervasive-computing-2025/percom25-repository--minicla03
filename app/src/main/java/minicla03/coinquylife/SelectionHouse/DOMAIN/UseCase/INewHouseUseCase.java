package minicla03.coinquylife.SelectionHouse.DOMAIN.UseCase;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseResult;

public interface INewHouseUseCase
{
    void execute(String houseName, String address, Consumer<SelectHouseResult> callback);
}
