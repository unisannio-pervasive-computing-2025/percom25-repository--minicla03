package minicla03.coinquylife.Auth.PRESENTATION.UI;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import minicla03.coinquylife.R;

public class AuthActivity extends AppCompatActivity
{
    private boolean showingLogin = true;
    private TextView switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_layout);

        //modalità schermo intero
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide(); // Nasconde l'ActionBar

        switchButton = findViewById(R.id.textViewRegister);
        if (savedInstanceState == null) {
            showFragmentWithText(new LoginFragment(), "Non hai un account? Registrati", true);
        }

        switchButton.setOnClickListener(v -> {
            if (showingLogin)
            {
                showFragmentWithText(new RegisterFragment(), "Hai già un account? Loggati", false);
            } else
            {
                showFragmentWithText(new LoginFragment(), "Non hai un account? Registrati", true);
            }
        });
    }

    void showFragmentWithText(Fragment fragment, String buttonText, boolean isLogin)
    {
        showingLogin = isLogin;

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.auth_fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();

        switchButton.setText(buttonText);
    }
}