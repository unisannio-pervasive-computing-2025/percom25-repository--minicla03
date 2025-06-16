package minicla03.coinquylife.ProfileAndSetting.DOMAIN.useCase;

import java.io.IOException;
import java.util.function.Consumer;

import minicla03.coinquylife.Auth.DOMAIN.Repository.IAuthRepository;
import minicla03.coinquylife.Auth.DOMAIN.model.User;

public class RetrieveUserByTokenUseCase implements IRetrieveUserByTokenUseCase
{
    private final IAuthRepository authRepository;
    
    public RetrieveUserByTokenUseCase(IAuthRepository repo)
    {
        this.authRepository = repo;
    }

    @Override
    public void execute(String token, String houseId, Consumer<User> callback)
    {
        try {
            authRepository.verifyToken(token, houseId, callback);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}