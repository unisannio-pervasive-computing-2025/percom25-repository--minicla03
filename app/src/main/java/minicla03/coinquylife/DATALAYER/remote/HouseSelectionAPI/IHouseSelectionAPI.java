package minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI;

import minicla03.coinquylife.SelectionHouse.DOMAIN.model.CoinquyHouse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IHouseSelectionAPI
{
    @Headers("Content-Type: application/json")
    @POST("/House/rest/house/create")
    Call<SelectHouseResult> createHouse(
            @Body CoinquyHouse body
    );

    @Headers("Content-Type: application/json")
    @POST("/House/rest/house/loginHouse")
    Call<SelectHouseResult> loginHouse(
            @Body CoinquyHouse body
    );
}

