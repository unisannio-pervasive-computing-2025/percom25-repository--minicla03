package minicla03.coinquylife.Auth.PRESENTATION.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minicla03.coinquylife.Auth.DOMAIN.Repository.IAuthRepository;
import minicla03.coinquylife.Auth.DOMAIN.UseCase.ILoginUserUseCase;
import minicla03.coinquylife.Auth.DOMAIN.UseCase.IRegisterUserUseCase;
import minicla03.coinquylife.Auth.DOMAIN.UseCase.LoginUserUseCase;
import minicla03.coinquylife.Auth.DOMAIN.UseCase.RegisterUserUseCase;
import minicla03.coinquylife.DATALAYER.RepositoryEntity.AuthRepository;
import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthResult;
import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthStatus;

public class AuthViewModel extends AndroidViewModel
{
    private final ILoginUserUseCase loginUseCase;
    private final IRegisterUserUseCase registerUseCase;

    private final MutableLiveData<AuthResult> loginResult = new MutableLiveData<>();
    private final MutableLiveData<AuthResult> registerResult = new MutableLiveData<>();

    public AuthViewModel(@NonNull Application application)
    {
        super(application);
        IAuthRepository repo = new AuthRepository(application);
        loginUseCase = new LoginUserUseCase(repo);
        registerUseCase = new RegisterUserUseCase(repo);
    }

    public LiveData<AuthResult> getLoginResult()
    {
        return loginResult;
    }

    public LiveData<AuthResult> getRegisterResult()
    {
        return registerResult;
    }

    public void login(String email, String password)
    {
        if (password == null)
        {
            loginResult.postValue(new AuthResult(AuthStatus.INVALID_PASSWORD, null));
            return;
        }
        Log.d("AUTHVIEWMODEL", "Login attempt with email: " + email);
        loginUseCase.login(email, password, loginResult::postValue);
    }

    public void register(String username, String name, String password, String surname, String email)
    {

        registerUseCase.register(username, name, password, surname, email, registerResult::postValue);
    }

}
