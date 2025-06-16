package minicla03.coinquylife.DATALAYER.remote.AuthAPI;

import java.util.Map;
import java.util.List;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IAuthApi
{
    @POST("verify-token")
    @Headers("Content-Type: application/json")
    Call<Map<String, String>> verifyToken(String token);

    @Headers("Content-Type: application/json")
    @POST("Auth/rest/auth/login")
    Call<AuthResult> login(@Body User body);

    @Headers("Content-Type: application/json")
    @POST("Auth/rest/auth/register")
    Call<ResponseBody> register(@Body User body);

    @Headers("Content-Type: application/json")
    @POST("/getUserByHouseId")
    Call<List<User>> getUserByHouseId(@Body Map<String, String> requestBody);

}