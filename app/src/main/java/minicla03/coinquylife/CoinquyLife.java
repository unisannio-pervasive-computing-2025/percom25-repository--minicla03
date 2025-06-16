package minicla03.coinquylife;

import android.app.Application;

import minicla03.coinquylife.DATALAYER.remote.ApiClient;

public class CoinquyLife extends Application
{
    private static ApiClient api;
    private static TokenManager tokenManager;

    @Override
    public void onCreate()
    {
        super.onCreate();
        api = ApiClient.getInstance();
        tokenManager= TokenManager.getInstance(this);
    }

    public static ApiClient getApi() {return api;}

    public static TokenManager getTokenManager() {return tokenManager;}
}
