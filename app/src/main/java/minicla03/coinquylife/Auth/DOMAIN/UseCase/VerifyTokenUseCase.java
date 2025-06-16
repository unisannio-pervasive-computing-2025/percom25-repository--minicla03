package minicla03.coinquylife.Auth.DOMAIN.UseCase;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

import minicla03.coinquylife.Auth.DOMAIN.Repository.IAuthRepository;
import minicla03.coinquylife.Auth.DOMAIN.model.User;

public class VerifyTokenUseCase implements IVerifyTokenUseCase
{
    private final IAuthRepository repository;

    public VerifyTokenUseCase(IAuthRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void execute(String token, String houseId, Consumer<User> callback)
    {
        try
        {
            repository.verifyToken(token, houseId, callback);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}