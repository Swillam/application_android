package fr.application.scanS.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

import fr.application.scanS.R;
import fr.application.scanS.data.DAO.ChapitreDAO;
import fr.application.scanS.data.DAO.MangaDAO;
import fr.application.scanS.data.Type.Chapitre;
import fr.application.scanS.data.Type.Manga;
import fr.application.scanS.ui.MangaActivity;

public class HomeFragment extends Fragment implements MangaAdapter.OnMangaListener {
    private View v;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // creer le RecyclerView (fait ?)
        RecyclerView recyclerView = view.findViewById(R.id.fragment_home_recycler_view);
        ArrayList<Manga> listManga = makeMangaList();
        view.findViewById(R.id.noManga).setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MangaAdapter mangaAdapter = new MangaAdapter(getContext(),listManga,this);
        recyclerView.setAdapter(mangaAdapter);
        if (listManga.isEmpty()){
            Log.i("info","listManga est vide");
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
        }
        return listManga;
    }

    @Override
    public void OnMangaListener(int position) {
        MangaActivity fragment = new MangaActivity();
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(HomeFragment.this)
                .replace(((ViewGroup)v.getParent()).getId() , fragment, "findThisFragment")
                .addToBackStack(null)
                .commit();
        /*
        Bundle bundle = new Bundle();
        bundle.putString("image", "fileName");
        fragment.setArguments(bundle);

         */
    }
}
