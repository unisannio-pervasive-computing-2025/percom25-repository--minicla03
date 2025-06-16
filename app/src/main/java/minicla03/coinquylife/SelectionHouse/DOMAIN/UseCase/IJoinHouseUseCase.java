package minicla03.coinquylife.SelectionHouse.DOMAIN.UseCase;

import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseResult;

public interface IJoinHouseUseCase
{
    void execute(String houseCode, Consumer<SelectHouseResult> callback);
}
