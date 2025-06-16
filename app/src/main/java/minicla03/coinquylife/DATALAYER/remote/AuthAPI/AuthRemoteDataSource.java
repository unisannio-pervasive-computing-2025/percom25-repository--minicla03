package minicla03.coinquylife.DATALAYER.remote.AuthAPI;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.CoinquyLife;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class AuthRemoteDataSource
{
    private final IAuthApi apiEndpoints;

    public AuthRemoteDataSource(Context context)
    {
        this.apiEndpoints = CoinquyLife.getApi().getApiAuth(context);
    }

    public Call<AuthResult> getUserByEmail(User user)
    {
        Log.d("AuthRemoteDataSource", "STO PER CHIAMARE L'ENDPOINT getUserByEmail");
        return apiEndpoints.login(user);
    }

    public Call<ResponseBody> register(User user)
    {
        return apiEndpoints.register(user);
    }

    public Call<Map<String, String>> verifyToken(String token)
    {
        Log.d("AuthRemoteDataSource", "STO PER CHIAMARE L'ENDPOINT verifyToken");
        return apiEndpoints.verifyToken(token);
    }

    public Call<List<User>> getCoiquysByHouseId(String houseId)
    {
        Log.d("AuthRemoteDataSource", "STO PER CHIAMARE L'ENDPOINT retrive");
        return apiEndpoints.getUserByHouseId(Map.of("houseId", houseId));
    }
}