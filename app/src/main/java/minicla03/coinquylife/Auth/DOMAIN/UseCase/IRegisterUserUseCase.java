package minicla03.coinquylife.Auth.DOMAIN.UseCase;

import java.util.function.Consumer;

import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthResult;

public interface IRegisterUserUseCase
{
    void register(String username, String name, String password, String surname, String email , Consumer<AuthResult> callback);
}
