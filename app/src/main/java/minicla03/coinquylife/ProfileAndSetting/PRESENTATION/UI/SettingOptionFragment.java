package minicla03.coinquylife.ProfileAndSetting.PRESENTATION.UI;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;

import minicla03.coinquylife.R;

public class SettingOptionFragment extends PreferenceFragmentCompat {

    private SharedPreferences sharedPreferences;
    private static final String PREF_KEY = "notifications_enabled";
    private static final String SUMMARY_KEY = "summary";
    private String sharedPrefFile = "minicla03.coinquylife.settings";

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        sharedPreferences = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        CheckBoxPreference checkbox = findPreference(PREF_KEY);

        if (checkbox != null) {
            // Imposta il summary in base al valore salvato
            boolean currentValue = sharedPreferences.getBoolean(PREF_KEY, true);
            checkbox.setSummary(currentValue
                    ? getString(R.string.option_on)
                    : getString(R.string.option_off));

            // Ascolta i cambiamenti
            checkbox.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean enabled = (Boolean) newValue;

                // Aggiorna summary
                checkbox.setSummary(enabled
                        ? getString(R.string.option_on)
                        : getString(R.string.option_off));

                // Salva anche un valore di supporto se vuoi
                sharedPreferences.edit()
                        .putBoolean(PREF_KEY, enabled)
                        .putString(SUMMARY_KEY, enabled
                                ? getString(R.string.option_on)
                                : getString(R.string.option_off))
                        .apply();

                return true;
            });
        }
    }
}