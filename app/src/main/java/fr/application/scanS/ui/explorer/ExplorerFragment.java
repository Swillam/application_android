package fr.application.scanS.ui.explorer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.application.scanS.R;
import fr.application.scanS.data.type.Manga;
import fr.application.scanS.ui.chapitre.ChapterFragment;
import fr.application.scanS.utils.MangaListener;

public class ExplorerFragment extends Fragment implements MangaListener {

    private View v;
    private ArrayList<Manga> listManga;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_explorer, container, false);
        requireActivity().setTitle(R.string.title_explorer);
        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = v.findViewById(R.id.fragment_explorer_recycler_view);
    }

    @Override
    public void OnMangaListener(int position) {
        Fragment fragment = new ChapterFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("manga", listManga.get(position));
        fragment.setArguments(bundle);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)// transition between home and manga
                .replace(((ViewGroup) v.getParent()).getId(), fragment)
                .addToBackStack(null)
                .commit();
    }
}
