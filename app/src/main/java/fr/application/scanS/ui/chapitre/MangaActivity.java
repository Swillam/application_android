package fr.application.scanS.ui.chapitre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.application.scanS.R;
import fr.application.scanS.data.Type.Chapitre;
import fr.application.scanS.data.Type.Manga;

public class MangaActivity extends Fragment {

    private Manga manga;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            manga = (Manga) bundle.getSerializable("manga");
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.manga_activity, container, false);
        TextView titre = (TextView) root.findViewById(R.id.titre_manga_textview);
        titre.setText(manga.getName());
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_chapitre);
        ArrayList<Chapitre> listChapitre = manga.getChapitres();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ChapitreAdapter chapitreAdapter = new ChapitreAdapter(getContext(),listChapitre);
        recyclerView.setAdapter(chapitreAdapter);
    }
}
