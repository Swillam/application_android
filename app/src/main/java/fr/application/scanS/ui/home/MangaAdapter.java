package fr.application.scanS.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.application.scanS.R;
import fr.application.scanS.data.Type.Chapitre;
import fr.application.scanS.data.Type.Manga;

public class MangaAdapter extends RecyclerView.Adapter<MangaViewHolder>{

    private Context _context;
    private ArrayList<Manga> _mangaList;
    private OnMangaListener mangaListener;


    public MangaAdapter(Context context, ArrayList<Manga> mangaList,OnMangaListener mangaListener){
        this._context = context;
        this._mangaList = mangaList;
        this.mangaListener = mangaListener;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(this._context).inflate(R.layout.content_cell_home,parent,false);
        MangaViewHolder mangaViewHolder = new MangaViewHolder(cell,mangaListener);
        return mangaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder holder, int position) {
        Manga m = this._mangaList.get(position);
        Chapitre c = m.getChapitrelast();
        holder.layoutForManga(m.getName(),c.getChapitre_name(),c.getChapitre_nb(),this._context);
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (this._mangaList !=null){
            itemCount = this._mangaList.size();
        }
        return itemCount;
    }
    public interface OnMangaListener{
        void OnMangaListener(int position);
    }
}
