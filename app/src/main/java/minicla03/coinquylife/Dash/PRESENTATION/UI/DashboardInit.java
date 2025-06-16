package minicla03.coinquylife.Dash.PRESENTATION.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import minicla03.coinquylife.Auth.DOMAIN.model.User;
import minicla03.coinquylife.DATALAYER.RepositoryEntity.CoinquilinoRepository;
import minicla03.coinquylife.Dash.DOMAIN.UseCase.GetCoinquiliniUseCase;
import minicla03.coinquylife.Dash.DOMAIN.UseCase.IGetCoinquyUseCase;
import minicla03.coinquylife.R;

public class DashboardInit extends AppCompatActivity
{
    private IGetCoinquyUseCase getCoinquiliniUseCase;
    private final Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        getCoinquiliniUseCase= new GetCoinquiliniUseCase(new CoinquilinoRepository(this));
        ((TextView) findViewById(R.id.tvLoadingMessage)).setText("Inizializzazione della casa...");

        loadDataFromNetworkOrDatabase();
    }

    private void loadDataFromNetworkOrDatabase()
    {
        new Thread(() ->
        {
            try
            {
                String houseCode  = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                        .getString("houseCode", null);

                List<User> listCoinquy=getCoinquiliniUseCase.execute(houseCode);
                String json = gson.toJson(listCoinquy);

                getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                    .edit()
                        .putString("coinquyList", json)
                        .apply();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            runOnUiThread(() -> {
                startActivity(new Intent(DashboardInit.this, DashboardActivity.class));
                finish();
            });
        }).start();
    }
}
