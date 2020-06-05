package fr.application.scanS.ui.explorer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.application.scanS.R;
import fr.application.scanS.data.type.Manga;
import fr.application.scanS.utils.AsyncTaskImg;
import fr.application.scanS.utils.MangaListener;

public class ExplorerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView _image;
    private final TextView _title;
    private final MangaListener mangaListener;
    private final ProgressBar _loading;

    public ExplorerViewHolder(@NonNull View itemView, MangaListener mangaListener) {
        super(itemView);
        this._image = itemView.findViewById(R.id.item_image_explorer);
        this._title = itemView.findViewById(R.id.item_title_explorer);
        this._loading = itemView.findViewById(R.id.load_image_explorer);
        this.mangaListener = mangaListener;
    }

    void layoutForMangaExplorer(Manga m, Context context) {
        this._title.setText(m.getName());
        new AsyncTaskImg(context, this._image, this._loading);
    }

    @Override
    public void onClick(View v) {
        mangaListener.OnMangaListener(getAdapterPosition());
    }


}
