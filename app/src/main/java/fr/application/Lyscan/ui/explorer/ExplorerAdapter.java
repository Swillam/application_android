package fr.application.Lyscan.ui.explorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import fr.application.Lyscan.R;
import fr.application.Lyscan.data.type.Manga;
import fr.application.Lyscan.utils.API_url;
import fr.application.Lyscan.utils.MangaListener;

import static fr.application.Lyscan.utils.API_url.url;

public class ExplorerAdapter extends RecyclerView.Adapter<ExplorerAdapter.ExplorerViewHolder> {
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
        return new ExplorerViewHolder(cell);
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

    class ExplorerViewHolder extends RecyclerView.ViewHolder {

        private final ImageView _image;
        private final TextView _title;
        private final ProgressBar _loading;
        private final RequestQueue requestqueue;


        ExplorerViewHolder(@NonNull View itemView) {
            super(itemView);
            this._image = itemView.findViewById(R.id.item_image_explorer);
            this._title = itemView.findViewById(R.id.item_title_explorer);
            this._loading = itemView.findViewById(R.id.load_image_explorer);
            this.requestqueue = Volley.newRequestQueue(_context);


            // you go into manga details fragment when you click in the manga item
        }

        void layoutForMangaExplorer(Manga m) {
            this._title.setText(m.getName());
            ImageRequest request = new ImageRequest(url + "image.php?image=" + API_url.format(m.getName_raw()) + ".jpg",
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            _image.setImageBitmap(bitmap);
                            _loading.setVisibility(View.INVISIBLE);
                            // you go into manga details fragment when you click in the manga item
                            itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    _mangaListener.OnMangaListener(getAdapterPosition(), _image);

                                }
                            });
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            _loading.setVisibility(View.INVISIBLE);
                            // you go into manga details fragment when you click in the manga item
                            itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    _mangaListener.OnMangaListener(getAdapterPosition(), _image);

                                }
                            });

                        }
                    });
            requestqueue.add(request);
        }


    }

}
