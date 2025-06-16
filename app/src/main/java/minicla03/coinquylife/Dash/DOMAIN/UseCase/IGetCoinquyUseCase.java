package minicla03.coinquylife.Dash.DOMAIN.UseCase;

import java.io.IOException;
import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;

public interface IGetCoinquyUseCase
{
    List<User> execute(String houseId) throws IOException;
}
