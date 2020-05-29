package fr.application.scanS.ui.chapitre;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.Objects;

import fr.application.scanS.R;
import fr.application.scanS.data.DAO.ChapitreDAO;
import fr.application.scanS.data.DAO.MangaDAO;
import fr.application.scanS.data.Type.Manga;

public class ChapterFragment extends Fragment {

    private Manga manga;
    private Button followBt;
    private RecyclerView recyclerView;
    private ProgressDialog pd;

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
        View root = inflater.inflate(R.layout.fragment_chapter, container, false);
        TextView titre = root.findViewById(R.id.titre_manga_textview);
        titre.setText(manga.getName());
        ImageView img = root.findViewById(R.id.manga_image);
        followBt = root.findViewById(R.id.bt_follow);

        // if the manga is in the db show unfollow button
        MangaDAO mangaDAO = new MangaDAO(getContext());
        mangaDAO.open();
        if (mangaDAO.select(manga.getId(mangaDAO)) != null) {
            followBt.setBackgroundColor(getResources().getColor(R.color.unfollow));
            followBt.setText(getResources().getText(R.string.unfollow));
        }
        mangaDAO.close();

        // get img
        if(manga.getImg_addr() != null){
            Uri uri = Uri.parse(manga.getImg_addr());
            File imgFile = new File(Objects.requireNonNull(uri.getPath()));
            if (imgFile.exists()) { // if no cache failure
                img.setImageURI(Uri.parse(manga.getImg_addr()));
            }

            TextView ongoing = root.findViewById(R.id.onGoing);
            String ongoingS = manga.getIn_progress() == 1 ? getString(R.string.ongoing) : getString(R.string.finished);
            ongoing.setText(ongoingS);

            TextView description = root.findViewById(R.id.manga_description);
            description.setText(manga.getDescription());
        }

        followBt.setOnClickListener(new View.OnClickListener() { //follow button
            public void onClick(View v) {
                MangaDAO mangaDAO1 = new MangaDAO(getContext());
                mangaDAO1.open();

                // add the manga to the db with its chapter for followed situation
                if (followBt.getText().toString().equals(getResources().getString(R.string.follow))) {
                    mangaDAO1.add(manga);
                    AsyncTaskDB asyncTaskDB = new AsyncTaskDB(manga.getId(mangaDAO1));
                    asyncTaskDB.execute(new ChapitreDAO(getContext()));
                    followBt.setBackgroundColor(getResources().getColor(R.color.unfollow));
                    followBt.setText(getResources().getText(R.string.unfollow));
                } else { // delete on cascade for unfollow situation
                    manga.setNoRead();
                    Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
                    mangaDAO1.delete(manga.getId(mangaDAO1));
                    followBt.setBackgroundColor(getResources().getColor(R.color.follow));
                    followBt.setText(getResources().getText(R.string.follow));
                }
                mangaDAO1.close();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view_chapitre);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ChapitreAdapter chapitreAdapter = new ChapitreAdapter(getContext(), manga.getChapitres(), followBt);
        recyclerView.setAdapter(chapitreAdapter);
    }

    @SuppressLint("StaticFieldLeak")
    public class AsyncTaskDB extends AsyncTask<ChapitreDAO, Void, Void> {
        private int id_manga;

        AsyncTaskDB(int id_manga) {
            this.id_manga = id_manga;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(getContext());
            pd.setTitle(getResources().getString(R.string.BdLoad));
            pd.setMessage(getResources().getString(R.string.wait));
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }


        @Override
        protected Void doInBackground(ChapitreDAO... chapitreDAOS) {
            chapitreDAOS[0].open();
            chapitreDAOS[0].add(manga.getChapitres(), id_manga);
            chapitreDAOS[0].close();
            return null;
        }

        protected void onPostExecute(Void result) {
            if (pd != null) {
                pd.dismiss();
            }
        }
    }
}
