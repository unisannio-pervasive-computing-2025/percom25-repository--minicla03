package minicla03.coinquylife.Auth.DOMAIN.UseCase;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import minicla03.coinquylife.Auth.DOMAIN.Repository.IAuthRepository;
import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.CoinquyLife;
import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthResult;
import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserUseCase implements ILoginUserUseCase
{
    private final IAuthRepository repository;

    public LoginUserUseCase(IAuthRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void login(String email, String password, Consumer<AuthResult> callback)
    {
        User user= new User(email,password);
        Log.d("USECASELOGIN", "STO PER ANDARE NEL REPOSITORY");
        Call<AuthResult> remoteCall = repository.getUserByEmailRemote(user);

        remoteCall.enqueue(new Callback<>()
        {
            @Override
            public void onResponse(@NonNull Call<AuthResult> call, @NonNull Response<AuthResult> response)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    var authResult = response.body();
                    String token = authResult.getToken();
                    CoinquyLife.getTokenManager().saveToken(token);
                    callback.accept(new AuthResult(AuthStatus.SUCCESS, token));

                }
                else
                {
                    callback.accept(new AuthResult(AuthStatus.ERROR, null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthResult> call, @NonNull Throwable t)
            {
                Log.e("LoginUserUseCase", "Login fallito", t);
                callback.accept(new AuthResult(AuthStatus.ERROR, null));
            }
        });
    }
}
