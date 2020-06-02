package fr.application.scanS.ui.explorer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fr.application.scanS.R;

public class ExplorerFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_explorer, container, false);
        getActivity().setTitle(R.string.title_explorer);
        return root;
    }
}
