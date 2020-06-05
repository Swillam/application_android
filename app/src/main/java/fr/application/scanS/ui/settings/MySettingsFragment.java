package fr.application.scanS.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import java.util.Objects;

import fr.application.scanS.MainActivity;
import fr.application.scanS.R;

public class MySettingsFragment extends PreferenceFragmentCompat
{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
        SwitchPreference preference = (SwitchPreference) findPreference("switchNightPref");
        Objects.requireNonNull(preference).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference _preference) {
                Intent i = new Intent(getContext(), MainActivity.class);
                requireActivity().finish();
                startActivity(i);
                return true;
            }
        });
    }


}