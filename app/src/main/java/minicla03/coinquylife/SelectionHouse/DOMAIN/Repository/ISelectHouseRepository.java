package minicla03.coinquylife.SelectionHouse.DOMAIN.Repository;

import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.SelectHouseResult;
import minicla03.coinquylife.SelectionHouse.DOMAIN.model.CoinquyHouse;
import retrofit2.Call;

public interface ISelectHouseRepository
{
    Call<SelectHouseResult> createHouseRemote(CoinquyHouse house);

    Call<SelectHouseResult> joinHouseRemote(CoinquyHouse house);
}
