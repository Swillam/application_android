package fr.application.scanS.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.application.scanS.R;
import fr.application.scanS.data.type.Chapitre;
import fr.application.scanS.data.type.Manga;
import fr.application.scanS.utils.AsyncTaskImg;
import fr.application.scanS.utils.MangaListener;

public class MangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView _titreManga;
    private final TextView _chapitreName;
    private final TextView _chapitreNb;
    private final ImageView _img;
    private final ProgressBar _loadimg;
    private final MangaListener mangaListener;

    MangaViewHolder(@NonNull View itemView, MangaListener mangaListener) {
        super(itemView);
        this._titreManga = itemView.findViewById(R.id.item_title);
        this._chapitreName = itemView.findViewById(R.id.item_chapter_name);
        this._chapitreNb = itemView.findViewById(R.id.item_chapter_nb);
        this._img = itemView.findViewById(R.id.item_image);
        this._loadimg = itemView.findViewById(R.id.load_image);
        this.mangaListener = mangaListener;

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
        mangaListener.OnMangaListener(getAdapterPosition());
    }


}
