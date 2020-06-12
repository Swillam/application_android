package fr.application.lyscan.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.application.lyscan.R;
import fr.application.lyscan.data.type.Chapitre;
import fr.application.lyscan.data.type.Manga;
import fr.application.lyscan.utils.AsyncTaskImg;
import fr.application.lyscan.utils.MangaListener;


public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {

    private final Context _context;
    private final ArrayList<Manga> _mangaList;
    private final MangaListener mangaListener;


    public MangaAdapter(Context context, ArrayList<Manga> mangaList, MangaListener mangaListener) {
        this._context = context;
        this._mangaList = mangaList;
        this.mangaListener = mangaListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(this._context).inflate(R.layout.content_cell_home, parent, false);
        return new MangaViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        Manga m = this._mangaList.get(position);
        holder.layoutForManga(m, this._context);
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (this._mangaList != null) {
            itemCount = this._mangaList.size();
        }
        return itemCount;
    }

    class MangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView _titreManga;
        private final TextView _chapitreName;
        private final TextView _chapitreNb;
        private final ImageView _img;
        private final ProgressBar _loadimg;

        MangaViewHolder(@NonNull View itemView) {
            super(itemView);
            this._titreManga = itemView.findViewById(R.id.item_title);
            this._chapitreName = itemView.findViewById(R.id.item_chapter_name);
            this._chapitreNb = itemView.findViewById(R.id.item_chapter_nb);
            this._img = itemView.findViewById(R.id.item_image);
            this._loadimg = itemView.findViewById(R.id.load_image);

            // you go into manga details fragment when you click in the manga item
            itemView.setOnClickListener(this);
        }


        void layoutForManga(Manga m, Context context) {
            Chapitre c = m.getChapitrelast();
            this._titreManga.setText(m.getName());
            this._chapitreName.setText(c.getChapitre_name());
            this._chapitreNb.setText(String.valueOf(c.getChapitre_nb()));
            new AsyncTaskImg(context, _img, _loadimg).execute(m);
        }

        @Override
        public void onClick(View v) {
            mangaListener.OnMangaListener(getAdapterPosition(), _img);
        }

    }

}
