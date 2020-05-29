package fr.application.scanS.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

import fr.application.scanS.R;
import fr.application.scanS.data.DAO.ChapitreDAO;
import fr.application.scanS.data.DAO.MangaDAO;
import fr.application.scanS.data.Type.Chapitre;
import fr.application.scanS.data.Type.Manga;
import fr.application.scanS.ui.chapitre.MangaActivity;

public class HomeFragment extends Fragment implements MangaAdapter.OnMangaListener {
    private View v;
    private ArrayList<Manga> listManga;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_home_recycler_view);
        listManga = makeMangaList();
        view.findViewById(R.id.noManga).setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MangaAdapter mangaAdapter = new MangaAdapter(getContext(),listManga,this);
        recyclerView.setAdapter(mangaAdapter);
        if (listManga.isEmpty()){
            view.findViewById(R.id.noManga).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        }

    }

    public ArrayList<Manga> makeMangaList(){
        MangaDAO mangaDAO = new MangaDAO(getContext());
        mangaDAO.open();
        ArrayList<Manga> listManga = mangaDAO.selectAll();
        ChapitreDAO chapitreDAO = new ChapitreDAO(getContext());
        chapitreDAO.open();
        Iterator<Manga> it = listManga.iterator();
        while (it.hasNext()){
            Manga m = it.next();
            int id = m.getId();
            ArrayList<Chapitre> chapitres = chapitreDAO.getChapitres(id);
            m.setChapitres(chapitres);
            if(m.getChapitrelast()==null){it.remove();} // si pas de nouveau chapitre à lire retirer le manga
        }
        return listManga;
    }

    @Override
    public void OnMangaListener(int position) {
        Fragment fragment = new MangaActivity();
        Bundle bundle = new Bundle();
        bundle.putSerializable("manga", listManga.get(position));
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)// transition entre home et manga
                .replace(((ViewGroup)v.getParent()).getId() , fragment)
                .addToBackStack(null)
                .commit();

    }
}
