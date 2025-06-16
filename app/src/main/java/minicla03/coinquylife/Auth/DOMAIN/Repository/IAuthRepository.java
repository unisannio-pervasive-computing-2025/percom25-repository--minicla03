package minicla03.coinquylife.Auth.DOMAIN.Repository;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.remote.AuthAPI.AuthResult;
import okhttp3.ResponseBody;
import retrofit2.Call;

public interface IAuthRepository
{
    Call<AuthResult> getUserByEmailRemote(User user);

    Call<ResponseBody> registerRemote(User user);

    User verifyToken(String token, String houseId, Consumer<User> callback) throws IOException;

    List<User> getCoiquysByHouseId(String houseId) throws IOException;
}
