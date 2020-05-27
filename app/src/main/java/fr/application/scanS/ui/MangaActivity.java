package fr.application.scanS.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import fr.application.scanS.R;
import fr.application.scanS.data.Type.Manga;

public class MangaActivity extends Fragment {

    private Manga manga;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.manga_activity, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get Intent
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.i("test","destroyed");

    }
}
