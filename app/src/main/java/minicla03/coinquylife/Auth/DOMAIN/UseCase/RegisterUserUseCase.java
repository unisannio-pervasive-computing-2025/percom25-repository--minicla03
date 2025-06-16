package minicla03.coinquylife.Auth.DOMAIN.UseCase;

import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minicla03.coinquylife.Auth.DOMAIN.Repository.IAuthRepository;
import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.CoinquyLife;
import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthResult;
import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthStatus;
import minicla03.coinquylife.TokenManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserUseCase implements IRegisterUserUseCase
{
    private final IAuthRepository repository;

    public RegisterUserUseCase(IAuthRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void register(String username, String name, String password, String surname, String email, Consumer<AuthResult> callback)
    {
        if (!isValidEmail(email))
        {
            callback.accept(new AuthResult(AuthStatus.INVALID_EMAIL, "Invalid email format"));
            return;
        }

        User user = new User(username, name, password, surname, email);
        Call<ResponseBody> remoteCall = repository.registerRemote(user);

        remoteCall.enqueue(new Callback<>()
        {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response)
            {
                Log.d("RegisterUserUseCase", "Response code: " + response.code());
                if (response.isSuccessful())
                {
                    callback.accept(new AuthResult(AuthStatus.SUCCESS, "Registered successfully!"));
                }
                else if (response.code() == 409)
                {
                    callback.accept(new AuthResult(AuthStatus.USER_ALREADY_EXISTS, "User already exists!"));
                }
                else if (response.code() == 401)
                {
                    callback.accept(new AuthResult(AuthStatus.INVALID_EMAIL, "Invalid email"));
                }
                else
                {
                    Log.e("RegisterUserUseCase", "Error during registration: " + response.code());
                    callback.accept(new AuthResult(AuthStatus.ERROR, "Error during registration"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                callback.accept(new AuthResult(AuthStatus.ERROR, "Error SERVER"));
            }
        });
    }

    private boolean isValidEmail(String email)
    {
        if (email == null) return false;

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
