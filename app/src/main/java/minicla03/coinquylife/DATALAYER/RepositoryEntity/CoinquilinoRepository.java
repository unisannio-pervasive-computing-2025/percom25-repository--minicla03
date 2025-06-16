package minicla03.coinquylife.DATALAYER.RepositoryEntity;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.remote.DashAPI.DashRemoteDataSource;
import minicla03.coinquylife.Dash.DOMAIN.Repository.ICoinquilinoRepository;
import retrofit2.Call;

public class CoinquilinoRepository implements ICoinquilinoRepository
{
    private DashRemoteDataSource remoteDataSource;

    public CoinquilinoRepository(Context context)
    {
        remoteDataSource = new DashRemoteDataSource(context);
    }

    @Override
    public List<User> getCoinquilini(String houseId) throws IOException
    {
        //verifica se sono già negli SharedPreferences
        Call<List<User>> listCoinquy = remoteDataSource.getCoinquy(houseId);
        try {
        List<User> coinquilini = listCoinquy.execute().body();

        if (coinquilini != null) {
            for (User user : coinquilini) {
                Log.d("Coinquilino",  " Nome: " + user.getName());
            }
        } else {
            Log.d("Coinquilino", "La lista è nulla.");
        }

        return coinquilini;

    } catch (IOException e) {
        Log.e("Errore", "Errore durante la chiamata Retrofit", e);
        return null;
    }
        //return listCoinquy.execute().body();
    }

}