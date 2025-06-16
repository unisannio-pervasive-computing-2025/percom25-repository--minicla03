package minicla03.coinquylife.ProfileAndSetting.DOMAIN.useCase;

import java.util.function.Consumer;

import minicla03.coinquylife.Auth.DOMAIN.model.User;

public interface IRetrieveUserByTokenUseCase
{
    void execute(String token, String houseId, Consumer<User> callback);
}
