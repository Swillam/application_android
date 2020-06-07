package fr.application.scanS.ui.explorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.application.scanS.R;
import fr.application.scanS.data.type.Manga;
import fr.application.scanS.utils.MangaListener;

public class ExplorerAdapter extends RecyclerView.Adapter<ExplorerViewHolder> {
    private final Context _context;
    private final ArrayList<Manga> _mangaList;
    private final MangaListener _mangaListener;


    public ExplorerAdapter(Context context, ArrayList<Manga> mangaList, MangaListener mangaListener) {
        this._context = context;
        this._mangaList = mangaList;
        this._mangaListener = mangaListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExplorerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(this._context).inflate(R.layout.content_cell_explorer, parent, false);
        return new ExplorerViewHolder(cell, _mangaListener, _context);
    }

    @Override
    public void onBindViewHolder(@NonNull ExplorerViewHolder holder, int position) {
        Manga m = this._mangaList.get(position);
        holder.layoutForMangaExplorer(m);
    }


    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (this._mangaList != null) {
            itemCount = this._mangaList.size();
        }
        return itemCount;
    }

    public void addItem(Manga manga) {
        this._mangaList.add(manga);
        notifyDataSetChanged();
    }
}
