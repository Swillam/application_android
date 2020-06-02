package fr.application.scanS.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fr.application.scanS.R;

public class SettingsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final Button button = root.findViewById(R.id.bt_setting);
        button.setText("Gestion du compte");
        getActivity().setTitle(R.string.title_setting);
        return root;
    }
}
