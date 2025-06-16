package minicla03.coinquylife.Auth.DOMAIN.UseCase;

import java.util.Map;
import java.util.function.Consumer;

import minicla03.coinquylife.Auth.DOMAIN.model.User;

public interface IVerifyTokenUseCase
{
    void execute(String token, String houseId, Consumer<User> callback);
}
