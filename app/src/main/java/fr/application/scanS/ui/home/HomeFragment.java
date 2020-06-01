package fr.application.scanS.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import fr.application.scanS.R;
import fr.application.scanS.data.DAO.ChapitreDAO;
import fr.application.scanS.data.DAO.MangaDAO;
import fr.application.scanS.data.Type.Manga;
import fr.application.scanS.ui.chapitre.ChapterFragment;

public class HomeFragment extends Fragment implements MangaAdapter.OnMangaListener {
    private View v;
    private ArrayList<Manga> listManga;
    private RecyclerView recyclerView;
    private TextView noMangaTv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = v.findViewById(R.id.fragment_home_recycler_view);
        noMangaTv = v.findViewById(R.id.noManga);
        noMangaTv.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        new AsyncTaskLoad().execute();
    }

    @Override
    public void OnMangaListener(int position) {
        Fragment fragment = new ChapterFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("manga", listManga.get(position));
        fragment.setArguments(bundle);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)// transition between home and manga
                .replace(((ViewGroup) v.getParent()).getId(), fragment)
                .addToBackStack(null)
                .commit();
    }


    // load recyclerView in background
    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskLoad extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getResources().getString(R.string.wait));
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            MangaDAO mangaDAO = new MangaDAO(getContext());
            mangaDAO.open();
            listManga = mangaDAO.selectAll();

            // add chapter list in manga
            ChapitreDAO chapitreDAO = new ChapitreDAO(getContext());
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
            if (!(listManga.isEmpty())) { // if we have a list create the recyclerView
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                MangaAdapter mangaAdapter = new MangaAdapter(getContext(), listManga, HomeFragment.this);
                recyclerView.setAdapter(mangaAdapter);
            } else { // else show a textView no message
                recyclerView.setVisibility(View.INVISIBLE);
                noMangaTv.setVisibility(View.VISIBLE);
            }

        }
    }
}
