package fr.application.scanS.ui.chapitre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.application.scanS.R;
import fr.application.scanS.data.Type.Chapitre;

public class ChapitreAdapter extends RecyclerView.Adapter <ChapitreAdapter.ChapitreViewHolder>{

    private Context _context;
    private ArrayList<Chapitre> _listChapitre;

    public ChapitreAdapter(Context context, ArrayList<Chapitre> listChapitre) {
        this._context = context;
        this._listChapitre = listChapitre;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ChapitreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(this._context).inflate(R.layout.content_cell_chapter,
                parent , false);
        return new ChapitreViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapitreViewHolder holder, int position) {
        Chapitre c = this._listChapitre.get(position);
        holder.LayoutForChapitre(c.getChapitre_name(),String.valueOf(c.getChapitre_nb()),c.getIfRead());
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (this._listChapitre !=null){
            itemCount = this._listChapitre.size();
        }
        return itemCount;
    }
    public class ChapitreViewHolder extends RecyclerView.ViewHolder {
        private final TextView _tvTitre;
        private final TextView _tvNumero;
        private final CheckBox _read;

        public ChapitreViewHolder(View cell) {
            super(cell);
            this._tvTitre = cell.findViewById(R.id.item_chapter_name_manga);
            this._tvNumero = cell.findViewById(R.id.item_chapter_nb_manga);
            this._read = cell.findViewById(R.id.checkBox);
            this._read.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int adapterPosition = getAdapterPosition();
                    Chapitre c = _listChapitre.get(adapterPosition);
                    if (_read.isChecked()) {
                        c.setIfRead(1,_context);
                        _read.setChecked(true);
                    }
                    else {
                        c.setIfRead(0,_context);
                        _read.setChecked(false);
                    }
                }
            });

         }

    public void LayoutForChapitre(String titre,String numero,int read) {
        this._tvTitre.setText(titre);
        this._tvNumero.setText(numero);
        if(read==1){
            this._read.setChecked(true);
        }
        else{
            this._read.setChecked(false);
        }
    }
}
}
