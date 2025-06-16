package minicla03.coinquylife.ProfileAndSetting.PRESENTATION.UI;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

import minicla03.coinquylife.R;

public class SettingActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.setting_layout);

        MaterialToolbar toolbar = findViewById(R.id.toolbarSettings);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settingFragmentContainer, new SettingOptionFragment())
                .commit();

    }
}
