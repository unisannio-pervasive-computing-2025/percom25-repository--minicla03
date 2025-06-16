package minicla03.coinquylife.DATALAYER.remote.DashAPI;

import java.util.List;
import java.util.Map;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.Rank.DOMAIN.model.Classifica;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.Call;

public interface IDashAPI
{
    @POST("Dashboard/rest/dash/retrieveClassifica")
    @Headers({"Content-Type: application/json",
            "Accept: application/json"})
    Call<Map<String, Classifica>> getClassifica(@Body CoiquyListDTO body);

    @GET("Dashboard/rest/dash/retrieveCoinquy")
    @Headers({"Content-Type: application/json",
            "Accept: application/json"})
    Call<List<User>> getCoinquy(@Query("houseId") String houseId);

    //@GET("rest/dash/retrieveTurni")
    //Call<ResponseBody> getTurni(@Query("houseId") String houseId);
}