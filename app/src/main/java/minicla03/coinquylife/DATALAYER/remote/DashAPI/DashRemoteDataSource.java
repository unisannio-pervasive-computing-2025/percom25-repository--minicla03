package minicla03.coinquylife.DATALAYER.remote.DashAPI;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Map;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.CoinquyLife;
import minicla03.coinquylife.Rank.DOMAIN.model.Classifica;
import retrofit2.Call;

public class DashRemoteDataSource
{
    private final IDashAPI apiEndpoints;

    public DashRemoteDataSource(Context context)
    {
        apiEndpoints = CoinquyLife.getApi().getApiDash(context);
    }

    public Call<List<User>> getCoinquy(String houseId)
    {
        Log.d("DashRemoteDataSource", "getCoinquy called with houseId: " + houseId);
        return apiEndpoints.getCoinquy(houseId);
    }

    public Call<Map<String, Classifica>> getClassifica(CoiquyListDTO body)
    {
        Log.d("DashRemoteDataSource", "getClassifica called");
        return apiEndpoints.getClassifica(body);
    }


}
