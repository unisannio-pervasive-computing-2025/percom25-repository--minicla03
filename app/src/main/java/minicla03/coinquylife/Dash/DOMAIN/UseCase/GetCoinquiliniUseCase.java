package minicla03.coinquylife.Dash.DOMAIN.UseCase;

import java.io.IOException;
import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.RepositoryEntity.CoinquilinoRepository;
import minicla03.coinquylife.Dash.DOMAIN.Repository.ICoinquilinoRepository;

public class GetCoinquiliniUseCase implements IGetCoinquyUseCase
{
    private final ICoinquilinoRepository repository;

    public GetCoinquiliniUseCase(CoinquilinoRepository repository)
    {
        this.repository = repository;
    }

    public List<User> execute(String houseId) throws IOException
    {
        return repository.getCoinquilini(houseId);
    }
}
