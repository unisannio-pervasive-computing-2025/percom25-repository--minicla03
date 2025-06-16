package minicla03.coinquylife.DATALAYER.RepositoryEntity;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import minicla03.coinquylife.Auth.DOMAIN.Repository.IAuthRepository;
import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthRemoteDataSource;
import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthResult;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository implements IAuthRepository
{
    private final AuthRemoteDataSource remoteDataSource;

    public AuthRepository(Context context)
    {
        remoteDataSource = new AuthRemoteDataSource(context);
    }

    @Override
    public Call<AuthResult> getUserByEmailRemote(User user)
    {
        Log.d("AUTHREPO", "Sto per andare nel database remoto");
        return remoteDataSource.getUserByEmail(user);
    }

    @Override
    public Call<ResponseBody> registerRemote(User user)
    {
        return remoteDataSource.register(user);
    }

    @Override
    public User verifyToken(String token, String houseId, Consumer<User> callback)
    {
        Call<Map<String, String>> call = remoteDataSource.verifyToken(token);
        call.enqueue(new Callback<>()
        {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, @NonNull Response<Map<String, String>> response)
            {
                if (response.code() == 200 && response.body() != null)
                {
                    String username = response.body().get("username");

                    List<User> coiquys = null;
                    try
                    {
                        coiquys = getCoiquysByHouseId(houseId);
                    }
                    catch (IOException e)
                    {
                        callback.accept(null);
                    }
                    assert coiquys != null;
                    User matchedUser = coiquys.stream()
                            .filter(coiquy -> coiquy.getUsername().equals(username))
                            .findFirst()
                            .orElse(null);

                    callback.accept(matchedUser);
                }
                else if (response.code() == 401)
                {
                    callback.accept(null);
                }
                else
                {
                    callback.accept(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, @NonNull Throwable t)
            {
                callback.accept(null);
            }
        });
        return null;
    }

    @Override
    public List<User> getCoiquysByHouseId(String houseId) throws IOException
    {
        return remoteDataSource.getCoiquysByHouseId(houseId).execute()
                .body() != null ? remoteDataSource.getCoiquysByHouseId(houseId).execute().body()
                : Collections.emptyList();
    }

}
