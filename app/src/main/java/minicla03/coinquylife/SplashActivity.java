package minicla03.coinquylife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import minicla03.coinquylife.Auth.PRESENTATION.UI.AuthActivity;

public class SplashActivity extends AppCompatActivity
{
    private static final long SPLASH_DELAY = 2500; // 2.5 secondi
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        checkNetworkAndProceed();
    }

    private void checkNetworkAndProceed()
    {
        if (isNetworkAvailable())
        {
            // Se rete disponibile, aspetta splash delay e poi vai avanti
            runnable = () -> {
                SharedPreferences prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE);
                boolean isLoggedIn = prefs.getBoolean("is_logged_in", false);
                boolean isLoggedWithHouse = prefs.getBoolean("is_logged_with_house", false);

                /*if (isLoggedWithHouse)
                {
                    startActivity(new Intent(SplashActivity.this, DashboardActivity.class));
                } else if (isLoggedIn) {
                    startActivity(new Intent(SplashActivity.this, CoinquyHouseSelectionActivity.class));
                } else {*/
                startActivity(new Intent(SplashActivity.this, AuthActivity.class));
                //}

                finish();
            };

            handler.postDelayed(runnable, SPLASH_DELAY);
        }
        else
        {
            // Se non c'è rete, mostra dialog e riprova dopo che l'utente conferma
            new AlertDialog.Builder(this)
                    .setTitle("Nessuna connessione")
                    .setMessage("Non è stata rilevata una connessione a internet. Riprova quando sei connesso.")
                    .setCancelable(false)
                    .setPositiveButton("Riprova", (dialog, which) -> {
                        dialog.dismiss();
                        checkNetworkAndProceed(); // ricontrolla la rete
                    })
                    .show();
        }
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
