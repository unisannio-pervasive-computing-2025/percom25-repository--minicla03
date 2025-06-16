package minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI;

import android.content.Context;

import minicla03.coinquylife.CoinquyLife;
import minicla03.coinquylife.SelectionHouse.DOMAIN.model.CoinquyHouse;
import retrofit2.Call;

public class HouseSelectionRemoteDataSource
{
    private final IHouseSelectionAPI apiEndpoints;

    public HouseSelectionRemoteDataSource(Context context)
    {
        this.apiEndpoints = CoinquyLife.getApi().getApiHouseSelection(context);
    }

    public Call<SelectHouseResult> createHouse(CoinquyHouse house)
    {
        return apiEndpoints.createHouse(house);
    }

    public Call<SelectHouseResult> loginHouse(CoinquyHouse house) {
        return apiEndpoints.loginHouse(house);
    }

}