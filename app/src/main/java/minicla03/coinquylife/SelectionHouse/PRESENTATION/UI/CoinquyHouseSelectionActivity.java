package minicla03.coinquylife.SelectionHouse.PRESENTATION.UI;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import minicla03.coinquylife.R;
import minicla03.coinquylife.SelectionHouse.PRESENTATION.ViewModel.SelectHouseViewModel;

public class CoinquyHouseSelectionActivity extends AppCompatActivity
{
    private SelectHouseViewModel selectHouseViewModel;
    private boolean showingLogin = true;
    private TextView switchButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivity_coinquyhouse_selection);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        selectHouseViewModel = new ViewModelProvider(this).get(SelectHouseViewModel.class);

        switchButton=findViewById(R.id.textViewRegister);

        if (savedInstanceState == null) {
            showFragmentWithText(new JoinCoinquyHouseFragment(), "Registra nuova casa", true);
        }

        switchButton.setOnClickListener(v -> {
            if (showingLogin)
            {
                showFragmentWithText(new JoinCoinquyHouseFragment(), "Registra nuova casa", false);
            } else
            {
                showFragmentWithText(new NewCoinquyHouseIDFragment(), "Aderisci ad una casa", true);
            }
        });
    }

    private void showFragmentWithText(Fragment fragment, String buttonText, boolean isLogin)
    {
        showingLogin = isLogin;

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.new_coinquy_house_fragment_container, fragment, fragment.getClass().getSimpleName())
                .commit();

        switchButton.setText(buttonText);
    }
}