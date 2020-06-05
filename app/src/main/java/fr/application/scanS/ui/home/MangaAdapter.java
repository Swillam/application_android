package fr.application.scanS.ui.home;

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


public class MangaAdapter extends RecyclerView.Adapter<MangaViewHolder> {

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
        return new MangaViewHolder(cell, mangaListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        Manga m = this._mangaList.get(position);
        holder.layoutForManga(m,this._context);
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (this._mangaList !=null){
            itemCount = this._mangaList.size();
        }
        return itemCount;
    }

}
