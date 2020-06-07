package fr.application.scanS.ui.explorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
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

import fr.application.scanS.R;
import fr.application.scanS.data.type.Manga;
import fr.application.scanS.utils.API_url;
import fr.application.scanS.utils.MangaListener;

import static fr.application.scanS.utils.API_url.url;

public class ExplorerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final ImageView _image;
    private final TextView _title;
    private final MangaListener mangaListener;
    private final ProgressBar _loading;
    private final RequestQueue requestqueue;


    ExplorerViewHolder(@NonNull View itemView, MangaListener mangaListener, Context context) {
        super(itemView);
        this._image = itemView.findViewById(R.id.item_image_explorer);
        this._title = itemView.findViewById(R.id.item_title_explorer);
        this._loading = itemView.findViewById(R.id.load_image_explorer);
        this.mangaListener = mangaListener;
        this.requestqueue = Volley.newRequestQueue(context);


        // you go into manga details fragment when you click in the manga item
        itemView.setOnClickListener(this);
    }

    void layoutForMangaExplorer(Manga m) {
        this._title.setText(m.getName());
        ImageRequest request = new ImageRequest(url + "image.php?image=" + API_url.format(m.getName_raw()) + ".jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        _image.setImageBitmap(bitmap);
                        _loading.setVisibility(View.INVISIBLE);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        _loading.setVisibility(View.INVISIBLE);
                    }
                });
        requestqueue.add(request);
    }

    @Override
    public void onClick(View v) {
        mangaListener.OnMangaListener(getAdapterPosition(), _image);
    }

}
