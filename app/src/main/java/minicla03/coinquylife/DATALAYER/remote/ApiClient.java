package minicla03.coinquylife.DATALAYER.remote;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import minicla03.coinquylife.DATALAYER.remote.AuthAPI.IAuthApi;
import minicla03.coinquylife.DATALAYER.remote.DashAPI.IDashAPI;
import minicla03.coinquylife.DATALAYER.remote.ExpenseAPI.IExpenseAPI;
import minicla03.coinquylife.DATALAYER.remote.HouseSelectionAPI.IHouseSelectionAPI;
import minicla03.coinquylife.DATALAYER.remote.ShiftAPI.ShiftAPI;
import minicla03.coinquylife.TokenManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient
{
    private static ApiClient instance;
    private Retrofit retrofit;

    private ApiClient() { }

    public static synchronized ApiClient getInstance()
    {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    private OkHttpClient getClientWithAuth(Context context)
    {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    String token = TokenManager.getInstance(context).getToken();

                    Request request = chain.request().newBuilder()
                            .header("Authorization", token != null ? "Bearer " + token : "")
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(loggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        }


    private Retrofit getRetrofit(Context context)
    {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://172.31.6.2:8080/")
                    .client(getClientWithAuth(context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public IAuthApi getApiAuth(Context context)
    {
        return getRetrofit(context).create(IAuthApi.class);
    }

    public IHouseSelectionAPI getApiHouseSelection(Context context)
    {
        return getRetrofit(context).create(IHouseSelectionAPI.class);
    }

    public IDashAPI getApiDash(Context context)
    {
        return getRetrofit(context).create(IDashAPI.class);
    }

    public IExpenseAPI getExpenseAPI(Context context)
    {
        return getRetrofit(context).create(IExpenseAPI.class);
    }


    public ShiftAPI getApiShift(Context context)
    {
        return getRetrofit(context).create(ShiftAPI.class);
    }
}

