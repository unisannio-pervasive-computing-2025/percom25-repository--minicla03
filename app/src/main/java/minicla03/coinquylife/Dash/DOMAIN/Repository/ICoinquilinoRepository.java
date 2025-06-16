package minicla03.coinquylife.Dash.DOMAIN.Repository;

import java.io.IOException;
import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;

public interface ICoinquilinoRepository
{
    List<User> getCoinquilini(String houseId) throws IOException;
}
