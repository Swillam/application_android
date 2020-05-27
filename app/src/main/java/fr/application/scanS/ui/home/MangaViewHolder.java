package fr.application.scanS.ui.home;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.application.scanS.R;

public class MangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView _titreManga, _chapitreName, _chapitreNb;
    private final ImageView _img;
    private MangaAdapter.OnMangaListener onMangaListener;

    public MangaViewHolder(@NonNull View itemView, MangaAdapter.OnMangaListener mangaListener) {
        super(itemView);
        this._titreManga = (TextView) itemView.findViewById(R.id.item_title);
        this._chapitreName = (TextView) itemView.findViewById(R.id.item_chapter_name);
        this._chapitreNb = (TextView) itemView.findViewById(R.id.item_chapter_nb);
        this._img = (ImageView) itemView.findViewById(R.id.item_image);
        this.onMangaListener = mangaListener;

        itemView.setOnClickListener(this);
    }
    // a faire
    public void layoutForManga(String titre, String chapitre, int chapitreNb, Context context){
        /*      get MangaImg
        File img = new File(context.getCacheDir(), titre+".png");
        Uri uri = FileProvider.getUriForFile(context, "fr.application.scanS", img);
        this._img.setImageURI(uri);
         */
        this._titreManga.setText(titre);
        this._chapitreName.setText(chapitre);
        this._chapitreNb.setText(String.valueOf(chapitreNb));
    }

    @Override
    public void onClick(View v) {
        onMangaListener.OnMangaListener(getAdapterPosition());
    }
}
