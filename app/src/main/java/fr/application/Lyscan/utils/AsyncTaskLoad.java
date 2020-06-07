package fr.application.Lyscan.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;

import fr.application.Lyscan.R;
import fr.application.Lyscan.data.database.ChapitreDAO;
import fr.application.Lyscan.data.database.MangaDAO;
import fr.application.Lyscan.data.type.Manga;
import fr.application.Lyscan.ui.home.HomeFragment;
import fr.application.Lyscan.ui.home.MangaAdapter;

// load recyclerView in background

public class AsyncTaskLoad extends AsyncTask<Void, Void, Void> {
    private final HomeFragment homeFragment; // the homeFragment is the mangaListener
    private ProgressDialog progressDialog;
    private ArrayList<Manga> listManga;


    public AsyncTaskLoad(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(homeFragment.getContext());
        progressDialog.setMessage(homeFragment.requireContext().getString(R.string.wait));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }


    @Override
    protected Void doInBackground(Void... v) {
        MangaDAO mangaDAO = new MangaDAO(homeFragment.getContext());
        mangaDAO.open();
        listManga = mangaDAO.selectAll();

        // add chapter list in manga
        ChapitreDAO chapitreDAO = new ChapitreDAO(homeFragment.getContext());
        chapitreDAO.open();
        Iterator<Manga> it = listManga.iterator();
        while (it.hasNext()) {
            Manga m = it.next();
            m.setChapitres(chapitreDAO.getMangaChapitres(m.getId(mangaDAO)));
            if (m.getChapitrelast() == null) {
                it.remove();
            } // if no news remove the manga
        }
        chapitreDAO.close();
        mangaDAO.close();

        return null;
    }

    protected void onPostExecute(Void result) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        RecyclerView recyclerView = homeFragment.requireView().findViewById(R.id.fragment_home_recycler_view);
        TextView noMangaTv = homeFragment.requireView().findViewById(R.id.noManga);
        if (!(listManga.isEmpty())) { // if we have a list create the recyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(homeFragment.getContext()));
            MangaAdapter mangaAdapter = new MangaAdapter(homeFragment.getContext(), listManga, homeFragment);
            recyclerView.setAdapter(mangaAdapter);
            homeFragment.setListManga(listManga); // set arraylist for the onMangalistener in HomeFragment
        } else { // else show a textView no message
            recyclerView.setVisibility(View.INVISIBLE);
            noMangaTv.setVisibility(View.VISIBLE);
        }

    }
}
