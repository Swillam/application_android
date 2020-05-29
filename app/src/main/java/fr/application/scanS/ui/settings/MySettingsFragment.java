package fr.application.scanS.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import 	androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import fr.application.scanS.MainActivity;
import fr.application.scanS.R;

public class MySettingsFragment extends PreferenceFragmentCompat
{

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
        SwitchPreference preference = (SwitchPreference) findPreference("switchNightPref");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference _preference) {
                boolean test =  PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("switchNightPref", false);
                Intent i = new Intent(getContext(), MainActivity.class);
                getActivity().finish();
                startActivity(i);
                return true;
            }
        });
    }
}