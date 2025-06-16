package minicla03.coinquylife.Auth.DOMAIN.UseCase;

import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthResult;

public interface ILoginUserUseCase
{
    void login(String email, String password, Consumer<AuthResult> callback);
}
